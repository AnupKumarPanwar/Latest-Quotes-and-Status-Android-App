package com.quotes.lateststatus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuotesActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Texts> textsList=new ArrayList<>();
    TextAdapter textAdapter;

    String[] quotes_array;

    SwipeRefreshLayout swipeRefreshLayout;

    String type, code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);


        recyclerView=findViewById(R.id.recycler_view);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);

        textAdapter=new TextAdapter(textsList, QuotesActivity.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(textAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareTextData();
            }
        });


        type=getIntent().getExtras().getString("category");

        code=type.replaceAll(" ", "");
        code=code.replaceAll("'","");
        code=code.toLowerCase();

        prepareTextData();


    }

    private void prepareTextData() {

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
                        .url("https://quotes-data.herokuapp.com/notificaionData/"+code)
                        .build();

                Response response;
                response = client.newCall(request).execute();
                String jsonString = response.body().string();

                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                textsList.clear();

                Texts texts;

                for (int i = 0; i < jsonArray.length(); i++) {

                    texts = new Texts(jsonArray.getString(i));
                    textsList.add(texts);
                }




                request = new Request.Builder()
                        .url("https://quotes-data.herokuapp.com/notificaionData/"+code+"2")
                        .build();

                response = client.newCall(request).execute();
                jsonString = response.body().string();
                jsonObject = new JSONObject(jsonString);
                jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {

                    texts = new Texts(jsonArray.getString(i));
                    textsList.add(texts);
                }



                request = new Request.Builder()
                        .url("https://quotes-data.herokuapp.com/notificaionData/"+code+"3")
                        .build();

                response = client.newCall(request).execute();
                jsonString = response.body().string();
                jsonObject = new JSONObject(jsonString);
                jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {

                    texts = new Texts(jsonArray.getString(i));
                    textsList.add(texts);
                }





            } catch (Exception E) {
//                    Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_LONG).show();
            }

            return true;
        }

        protected void onPostExecute(Boolean result) {
            textAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }


    }
}
