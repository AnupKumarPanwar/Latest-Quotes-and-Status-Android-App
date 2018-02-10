package com.quotes.lateststatus;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Asus on 2/8/2018.
 */

public class TextAdapter extends RecyclerView.Adapter <TextAdapter.MyViewHolder>{

    List<Texts> textsList;
    Activity context;

    public TextAdapter(List<Texts> TL, Activity ctx)
    {
        textsList=TL;
        context=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageButton copy, share;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_holder);
            copy=(ImageButton)itemView.findViewById(R.id.copy);
            share=(ImageButton)itemView.findViewById(R.id.share);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_text, parent, false);
        return new TextAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Texts texts=textsList.get(position);

        holder.textView.setText(texts.quote);

        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Latest Quotes and Status", texts.quote+"\n\nhttps://play.google.com/store/apps/details?id=com.quotes.lateststatus");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context.getApplicationContext(),"Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Latest Quotes and Status");
                shareIntent.putExtra(Intent.EXTRA_TEXT, texts.quote+"\n\nhttps://play.google.com/store/apps/details?id=com.quotes.lateststatus");
                shareIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(shareIntent, "Share"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return textsList.size();
    }


}
