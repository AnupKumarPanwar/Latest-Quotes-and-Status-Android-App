package com.quotes.lateststatus;


import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    List<Videos> videosList = new ArrayList<>();
    AAH_CustomRecyclerView recyclerView;
    VideoAdapter videoAdapter;

    SwipeRefreshLayout swipeRefreshLayout;

    Handler handler;

    Runnable runnable;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        savedInstanceState = null;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


//        if (view==null)
//        {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
//        }

        recyclerView = (AAH_CustomRecyclerView) view.findViewById(R.id.recycler_view);

        videoAdapter = new VideoAdapter(videosList, Picasso.with(getContext()), getActivity());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setActivity(getActivity());
        recyclerView.setAdapter(videoAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareVideoData();
            }
        });


        runnable = new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.giphy.com/v1/gifs/trending?api_key=6jP7SrsIeETXfgbmKw5gD71Vos7Db3LG&limit=25&rating=G")
                        .build();


                try {
                    Response response;
                    response = client.newCall(request).execute();
                    String jsonString = response.body().string();

                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    videosList.clear();

                    Videos videos;

                    for (int i = 0; i < jsonArray.length(); i++) {

                        videos = new Videos(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("original").getString("mp4"), jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("original_still").getString("url"));
                        videosList.add(videos);
                    }

                    videoAdapter.notifyDataSetChanged();

                } catch (Exception E) {
//                    Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_LONG).show();
                }

                swipeRefreshLayout.setRefreshing(false);

            }
        };


        handler = new Handler();

        prepareVideoData();


        // Inflate the layout for this fragment
        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void prepareVideoData() {

        JSONAsyncTask getData = new JSONAsyncTask();
        getData.execute();

    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.giphy.com/v1/gifs/trending?api_key=6jP7SrsIeETXfgbmKw5gD71Vos7Db3LG&limit=100&rating=G")
                        .build();

                Response response;
                response = client.newCall(request).execute();
                String jsonString = response.body().string();

                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                videosList.clear();

                Videos videos;

                for (int i = 0; i < jsonArray.length(); i++) {

                    videos = new Videos(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("original").getString("mp4"), jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("original_still").getString("url"));
                    videosList.add(videos);
                }



            } catch (Exception E) {
//                    Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_LONG).show();
            }

            return true;
        }

        protected void onPostExecute(Boolean result) {
            videoAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }


    }
}

