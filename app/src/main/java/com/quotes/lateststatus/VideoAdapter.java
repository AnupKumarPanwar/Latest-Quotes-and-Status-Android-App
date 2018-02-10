package com.quotes.lateststatus;

/**
 * Created by Asus on 2/3/2018.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.List;

import okio.Buffer;

/**
 * Created by Asus on 2/3/2018.
 */

public class VideoAdapter extends AAH_VideosAdapter{

    List<Videos> videosList;
    Picasso picasso;
    Activity context;


    public ImageButton save, fav, share;
    public class MyViewHolder extends AAH_CustomViewHolder {

//        public VideoView videoView;

        public MyViewHolder(View view) {
            super(view);
            save=(ImageButton) view.findViewById(R.id.save);
//            fav=(ImageButton)view.findViewById(R.id.favourite);
            share=(ImageButton)view.findViewById(R.id.share);

//            videoView=(VideoView) view.findViewById(R.id.video_holder);

        }
    }


    public VideoAdapter(List<Videos> VL, Picasso p, Activity ctx)
    {
        videosList=VL;
        picasso=p;
        context=ctx;
    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_video, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AAH_CustomViewHolder holder, int position) {
        Videos videos=videosList.get(position);
//        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//            }
//        });
//        holder.videoView.setVideoURI(Uri.parse(videos.videoUrl));
//        holder.videoView.start();
        holder.setImageUrl(videos.imageUrl);
        holder.setVideoUrl(videos.videoUrl);

        picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String videoPath=downloadFile(holder.getVideoUrl(), "sample.mp4");

//                Toast.makeText(context, videoPath.toString(), Toast.LENGTH_LONG).show();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Latest Quotes and Status");
                shareIntent.putExtra(Intent.EXTRA_TEXT, holder.getVideoUrl());
                context.startActivity(Intent.createChooser(shareIntent, "Share"));

            }
        });




    }

    private String downloadFile(String dwnload_file_path, String fileName) {
        String filePath = null;

        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot,"downloaded_file.mp4");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            int totalSize = urlConnection.getContentLength();


            //create a buffer...
            byte[] buffer = new byte[10240];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                int downloadedSize = bufferLength;
                // update the progressbar //
            }
            //close the output stream when complete //
            filePath=SDCardRoot.getPath()+"/downloaded_file.mp4";
            fileOutput.close();



        } catch (final MalformedURLException e) {

            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        catch (final Exception e) {
        }

        return filePath;
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }
}
