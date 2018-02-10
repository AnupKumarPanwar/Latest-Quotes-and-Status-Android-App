package com.quotes.lateststatus;

/**
 * Created by Asus on 2/3/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quotes.lateststatus.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Asus on 2/3/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    List<Posts> postsList;
    Activity context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageButton save, fav, share;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            save=(ImageButton) view.findViewById(R.id.save);
//            fav=(ImageButton)view.findViewById(R.id.favourite);
            share=(ImageButton)view.findViewById(R.id.share);

            imageView=(ImageView) view.findViewById(R.id.image_holder);

        }
    }


    public PostAdapter(List<Posts> PL, Activity ctx)
    {
        postsList=PL;
        context=ctx;
    }

    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostAdapter.MyViewHolder holder, int position) {
        final Posts posts=postsList.get(position);
        Picasso.with(holder.imageView.getContext()).load(posts.imageUrl).into(holder.imageView);


        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri bmpUri = getLocalBitmapUri(holder.imageView);

//                Toast.makeText(context, bmpUri.toString(), Toast.LENGTH_LONG).show();


                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download the #1 app for *Latest Quotes and Status*.\nhttps://play.google.com/store/apps/details?id=com.quotes.lateststatus");
                shareIntent.setType("image/*");
                context.startActivity(Intent.createChooser(shareIntent, "Share"));

//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download the #1 app for Latest Quotes and Status.\nhttps://play.google.com/store/apps/details?id=com.quotes.lateststatus");
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(posts.imageUrl));
//                shareIntent.setType("image/*");
//                context.startActivity(Intent.createChooser(shareIntent, "Share"));


            }
        });




    }

    private Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // This way, you don't need to request external read/write permission.
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
}
