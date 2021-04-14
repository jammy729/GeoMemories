package com.example.geomemories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    // variables
    public ArrayList<String> list;
    // constructor
    public PostAdapter(ArrayList<String> list) {
        this.list = list;
    }


    //methods
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String[]  results = (list.get(position)).split(",");
        holder.nameTextView.setText(results[0]);
        holder.postTextView.setText(results[1]);


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int pos);
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public  TextView destTextView;
        //variables
        public LinearLayout postViewLayout;
        public TextView nameTextView, postTextView;
        public View mView;
        Context context;

        public MyViewHolder(View postView) {
            super(postView);
            postViewLayout = (LinearLayout) postView;

            nameTextView = postView.findViewById(R.id.descriptionView);
            postTextView = postView.findViewById(R.id.postView);



            context = postView.getContext();
            mView = postView;
        }
    }
}
