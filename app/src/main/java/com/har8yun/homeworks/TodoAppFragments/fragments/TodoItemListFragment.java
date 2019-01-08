package com.har8yun.homeworks.TodoAppFragments.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.FloatingActionButton;
import android.widget.SearchView;

import com.har8yun.homeworks.TodoAppFragments.adapter.TodoItemRecyclerAdapter;
import com.har8yun.homeworks.TodoAppFragments.R;
import com.har8yun.homeworks.TodoAppFragments.util.SortOnClickListener;
import com.har8yun.homeworks.TodoAppFragments.models.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TodoItemListFragment extends Fragment implements SortOnClickListener, SearchView.OnQueryTextListener {


    private OnFragmentInteractionListener mListener;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    public static int selectedItemsCount;

    private List<Integer> removedItemsPosition = new ArrayList<>();

    private ActionMode mActionMode;
    public static boolean multiSelectMode;
    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            multiSelectMode = true;
            initRecyclerView();
            actionMode.setTitle(selectedItemsCount + " Selected");
            actionMode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            Log.e("mmmm","PrepareActionMode");
            addButton.hide();
            removedItemsPosition.clear();
            selectedItemsCount = 0;
            mode.setTitle("Select items");

            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            Log.e("mmmm","ActionItemClicked");
            switch (menuItem.getItemId()){
                case R.id.action_menu_delete:
                    Collections.reverse(removedItemsPosition);
                    mRecyclerAdapter.removeAllItems(removedItemsPosition);
                    actionMode.finish();
            }
            return false;
        }


        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

            addButton.show();
            multiSelectMode = false;

            for (int i = 0; i < removedItemsPosition.size(); i++) {
                for (int j = 0; j < todoItemList.size(); j++) {
                    if (j==removedItemsPosition.get(i)){
                        todoItemList.remove(j);
                    }
                }
            }
            initRecyclerView();
            mActionMode = null;
        }
    };

    public static TodoItem todoItem;
    public static TodoItem item;
    List<TodoItem> todoItemList = new ArrayList<>();

    TodoItemRecyclerAdapter mRecyclerAdapter;
    public static boolean isEdited;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_item_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_activity_main);
        addButton = view.findViewById(R.id.btn1_activity_main);

        searchMode = false;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction();
            }
        });

        if (isEdited && todoItem !=null){
            mRecyclerAdapter.updateItem(todoItem);
            initRecyclerView();
            todoItem = null;
        }else if (todoItem != null){
            todoItemList.add(todoItem);
            todoItem = null;
            initRecyclerView();
        }else {
            isEdited = false;
            initRecyclerView();
        }

        return view;
    }

    private void initRecyclerView() {
        mRecyclerAdapter = new TodoItemRecyclerAdapter();
        mRecyclerAdapter.setmOnRvItemClickListener(
                new TodoItemRecyclerAdapter.OnRvItemClickListener() {
                    @Override
                    public void onItemClicked(int pos) {
                        if (mActionMode == null){
                            isEdited = true;
                            item = todoItemList.get(pos);
                            Log.e("yyyy","serachmode "+String.valueOf(searchMode));
                            if (searchMode){
                                item = newList.get(pos);
                            }

                            mListener.onFragmentInteraction2(item);
                        }else {
                            item = todoItemList.get(pos);

                            item.setSelected(!item.isSelected());
                            Log.e("zzz",""+item.getTitle()+" "+String.valueOf(item.isSelected())+" position is " + pos);
                            if (item.isSelected()){
                                removedItemsPosition.add(pos);
                                Log.e("zzz","Add to removed list "+item.getTitle());

                            } else {
                                for (int i = 0; i < removedItemsPosition.size(); i++) {
                                    if (removedItemsPosition.get(i)==pos){
                                        removedItemsPosition.remove(i);

                                    }
                                }
                            }

                            mActionMode.setTitle(selectedItemsCount + " Selected");
                        }

                    }

                    @Override
                    public void onItemLongClicked(int pos) {
                        if (!searchMode) {
                            item = todoItemList.get(pos);
                            mActionMode = getActivity().startActionMode(modeCallBack);
                        }
                    }
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.addItems(todoItemList);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    boolean flag;
    @Override
    public void sortOnClickListener() {
        Collections.sort(todoItemList, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem o1, TodoItem o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        if (flag){
            Collections.reverse(todoItemList);
            flag = false;
        }else{
            flag = true;
        }
        initRecyclerView();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    List<TodoItem> newList;
    public static boolean searchMode;
    @Override
    public boolean onQueryTextChange(String newText) {
        searchMode = true;
        String userInput = newText.toLowerCase();


        newList = new ArrayList<>();

        for (TodoItem t: todoItemList) {
            if (t.getTitle().toLowerCase().contains(userInput) || t.getDescription().toLowerCase().contains(userInput)){
                newList.add(t);
            }
        }

        mRecyclerAdapter.updateSearchListItems(newList);

        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
        void onFragmentInteraction2(TodoItem t);
    }

}