package com.quotes.lateststatus;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostFragment extends Fragment {

    List<Posts> postsList=new ArrayList<>();
    RecyclerView recyclerView;
    PostAdapter postAdapter;

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



//        if (view==null) {
            View view = inflater.inflate(R.layout.fragment_post, container, false);
//        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        postAdapter=new PostAdapter(postsList, getActivity());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postAdapter);




        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                preparePostData();
            }
        });





        runnable= new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://graph.facebook.com/v2.8/anupkumarpanwar2/posts?fields=full_picture&include_hidden=true&limit=20+&access_token=EAAImdkZBRFdUBAAZAjeBYC3JVZBnMlLlmoVS8eh0iwwyOqD1QxxxIdKnkZC857bHGi0SR2EPHE9IsJYU4StdTtBNZB7EILdxtwELRQ2KhPsQpvNResFnqBmBinqJeXD45sS9R1d1DVn44jitTAAuxGUoDUVZCZBAfePw55ZBx0GuCAZDZD")
                        .build();



                try {
                    Response response;
                    response = client.newCall(request).execute();
                    String jsonString=response.body().string();

                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");

                    postsList.clear();

                    Posts posts;

                    for (int i=0; i<jsonArray.length(); i++)
                    {

                        posts= new Posts(jsonArray.getJSONObject(i).getString("full_picture"));
                        postsList.add(posts);
                    }

                    postAdapter.notifyDataSetChanged();

                }
                catch (Exception E)
                {
//                    Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_LONG).show();
                }

                swipeRefreshLayout.setRefreshing(false);

            }
        };




        handler=new Handler();

        preparePostData();



        // Inflate the layout for this fragment
        return view;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void preparePostData() {

//        handler.postDelayed(runnable, 1);
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
        protected Boolean doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://graph.facebook.com/v2.8/anupkumarpanwar2/posts?fields=full_picture&include_hidden=true&limit=100+&access_token=EAAImdkZBRFdUBAAZAjeBYC3JVZBnMlLlmoVS8eh0iwwyOqD1QxxxIdKnkZC857bHGi0SR2EPHE9IsJYU4StdTtBNZB7EILdxtwELRQ2KhPsQpvNResFnqBmBinqJeXD45sS9R1d1DVn44jitTAAuxGUoDUVZCZBAfePw55ZBx0GuCAZDZD")
                    .build();



            try {
                Response response;
                response = client.newCall(request).execute();
                String jsonString=response.body().string();

                JSONObject jsonObject=new JSONObject(jsonString);
                JSONArray jsonArray=jsonObject.getJSONArray("data");

                postsList.clear();

                Posts posts;

                for (int i=0; i<jsonArray.length(); i++)
                {

                    posts= new Posts(jsonArray.getJSONObject(i).getString("full_picture"));
                    postsList.add(posts);
                }

            }
            catch (Exception E)
            {
//                    Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_LONG).show();
            }

            return true;
        }

        protected void onPostExecute(Boolean result) {
            postAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }


}
