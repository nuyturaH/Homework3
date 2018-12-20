package com.har8yun.homeworks.homework3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.har8yun.homeworks.homework3.R;
import com.har8yun.homeworks.homework3.models.TodoItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ToDoItemRecyclerAdapter extends RecyclerView.Adapter<ToDoItemRecyclerAdapter.ToDoItemViewHolder> {

    private List<TodoItem> mData = new ArrayList<>();
    private static OnRvItemClickListener mOnRvItemClickListener;

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_todo_item, parent, false);       //view_todo_item is our xml file,,

        // so we give an example of View that we want to create every time,to inflater
        return new ToDoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemViewHolder holder, final int position)
    {

        SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd MMM yyyy, hh:mm");

        TodoItem todoItem = mData.get(position);

        holder.title.setText(todoItem.getTitle());
        holder.description.setText(todoItem.getDescription());
        holder.date.setText(simpleDateformat.format(todoItem.getDate()));
        holder.priority.setBackgroundColor(todoItem.getPriority());

    }

    public static class ToDoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView description;
        public TextView date;
        public TextView priority;

        public ToDoItemViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_view_todo_item);
            description = itemView.findViewById(R.id.description_view_todo_item);
            date = itemView.findViewById(R.id.date_view_todo_item);
            priority = itemView.findViewById(R.id.priority_view_todo_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRvItemClickListener.onItemClicked(getAdapterPosition());

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItems(Collection<TodoItem> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }


    public void updateItem(TodoItem item) {
        Log.e("mmmm","mtav updati mej");
        // Find item and update
        // Not the best solution, it is possible update an item by accepting position
        // from outside. For current data structure and 'client' implementation
        // this is best of worst.
//        for (int i = 0; i < mData.size(); i++) {
//            if (Objects.equals(item, mData.get(i))) {
//                mData.set(i, item);
//                notifyItemChanged(i);
//            }
//        }

        for (int i = 0; i < mData.size(); i++) {
            if (item.getId()== mData.get(i).getId()) {
                mData.set(i, item);
                notifyItemChanged(i);
            }
        }

    }




    public void setmOnRvItemClickListener(OnRvItemClickListener mOnRvItemClickListener) {
        this.mOnRvItemClickListener = mOnRvItemClickListener;
    }

    public interface OnRvItemClickListener{
        void onItemClicked(int pos);
    }
}