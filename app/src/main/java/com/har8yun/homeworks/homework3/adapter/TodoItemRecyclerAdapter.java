package com.har8yun.homeworks.homework3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.har8yun.homeworks.homework3.R;
import com.har8yun.homeworks.homework3.fragments.TodoItemListFragment;
import com.har8yun.homeworks.homework3.models.TodoItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TodoItemRecyclerAdapter extends RecyclerView.Adapter<TodoItemRecyclerAdapter.ToDoItemViewHolder> {

    private List<TodoItem> mData = new ArrayList<>();
    private static OnRvItemClickListener mOnRvItemClickListener;

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_todo_item, parent, false);
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

        if (!holder.checkBox.isChecked()){
            holder.checkBox.setVisibility(View.INVISIBLE);
        }

        if (TodoItemListFragment.multiSelectMode){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ToDoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView description;
        public TextView date;
        public TextView priority;
        public CheckBox checkBox;

        public ToDoItemViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_view_todo_item);
            description = itemView.findViewById(R.id.description_view_todo_item);
            date = itemView.findViewById(R.id.date_view_todo_item);
            priority = itemView.findViewById(R.id.priority_view_todo_item);
            checkBox = itemView.findViewById(R.id.check_box_todo_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(!checkBox.isChecked());
                    if (checkBox.isChecked()){
                        TodoItemListFragment.selectedItemsCount++;
                    }else {
                        TodoItemListFragment.selectedItemsCount--;
                    }

                    mOnRvItemClickListener.onItemClicked(getAdapterPosition());
                }

            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!TodoItemListFragment.searchMode) {
                        mOnRvItemClickListener.onItemLongClicked(getAdapterPosition());
                    }
                    return true;
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

    public void addItems(Collection<TodoItem> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void updateItem(TodoItem item) {
        for (int i = 0; i < mData.size(); i++) {
            if (item.getId()== mData.get(i).getId()) {
                mData.set(i, item);
                notifyItemChanged(i);
            }
        }
    }

    public void removeAllItems(Collection<Integer> removedPositionsList) {

            for (int i: removedPositionsList) {
                mData.remove(i);
                notifyItemRemoved(i);
            }
    }

    public void updateSearchListItems(Collection<TodoItem> items) {

        mData = new ArrayList<>();
        mData.addAll(items);

        notifyDataSetChanged();
    }

    public void setmOnRvItemClickListener(OnRvItemClickListener mOnRvItemClickListener) {
        this.mOnRvItemClickListener = mOnRvItemClickListener;
    }

    public interface OnRvItemClickListener{
        void onItemClicked(int pos);
        void onItemLongClicked(int pos);
    }
}
