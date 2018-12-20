package com.har8yun.homeworks.homework3.adapter.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.har8yun.homeworks.homework3.R;


public class ToDoItemViewHolder extends RecyclerView.ViewHolder{
    public CardView cv;
    public TextView title;
    public TextView description;
    public TextView date;

    public ToDoItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.input_title);
        description = itemView.findViewById(R.id.input_description);
        date = itemView.findViewById(R.id.date);
    }
}
