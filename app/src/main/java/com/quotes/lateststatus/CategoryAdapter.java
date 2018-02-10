package com.quotes.lateststatus;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Asus on 2/8/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter <CategoryAdapter.MyViewHolder>{

    List<Categories> categoriesList;
    Activity context;

    public CategoryAdapter(List<Categories> CL, Activity ctx)
    {
        categoriesList=CL;
        context=ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=(Button) itemView.findViewById(R.id.text_holder);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_category, parent, false);
        return new CategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Categories categories=categoriesList.get(position);

        holder.textView.setText(categories.category);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context.getApplicationContext(), QuotesActivity.class);
                intent.putExtra("category", categories.category);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


}
