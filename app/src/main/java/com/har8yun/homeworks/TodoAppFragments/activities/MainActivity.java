package com.har8yun.homeworks.TodoAppFragments.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.har8yun.homeworks.TodoAppFragments.R;
import com.har8yun.homeworks.TodoAppFragments.fragments.TodoItemFragment;
import com.har8yun.homeworks.TodoAppFragments.fragments.TodoItemListFragment;
import com.har8yun.homeworks.TodoAppFragments.models.TodoItem;
import com.har8yun.homeworks.TodoAppFragments.util.EditOnClickListener;
import com.har8yun.homeworks.TodoAppFragments.util.SortOnClickListener;


public class MainActivity extends AppCompatActivity implements TodoItemListFragment.OnFragmentInteractionListener, TodoItemFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private TodoItemListFragment todoItemListFragment;
    private TodoItemFragment todoItemFragment;
    public static boolean isEnabled;

    EditOnClickListener editOnClickListener;
    SortOnClickListener sortOnClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoItemListFragment = new TodoItemListFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, todoItemListFragment);
        fragmentTransaction.commit();

        todoItemFragment = new TodoItemFragment();

        editOnClickListener = todoItemFragment;
        sortOnClickListener = todoItemListFragment;
    }

    @Override
    public void onFragmentInteraction() {
        sort.setVisible(false);
        todoItemFragment = new TodoItemFragment();
        editOnClickListener = todoItemFragment;

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, todoItemFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        edit.setVisible(true);
    }

    @Override
    public void onFragmentInteraction2(TodoItem t) {
        todoItemFragment = new TodoItemFragment();
        editOnClickListener = todoItemFragment;

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, todoItemFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(TodoItem t) {
        todoItemFragment = new TodoItemFragment();
        editOnClickListener =  todoItemFragment;
        TodoItemListFragment.todoItem = t;

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, todoItemListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void editButton(boolean b) {
        if (b){
            edit.setVisible(true);
            search.setVisible(false);
            sort.setVisible(false);
            TodoItemListFragment.searchMode= false;
            edit.setIcon(R.drawable.ic_edit);

        }else {
            edit.setVisible(false);
            search.setVisible(true);
            sort.setVisible(true);
            searchView.onActionViewCollapsed();
        }
    }

    private MenuItem edit;
    private MenuItem sort;
    private MenuItem search;
    private SearchView searchView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        edit = menu.findItem(R.id.action_menu_edit);
        sort = menu.findItem(R.id.action_menu_sort);
        search = menu.findItem(R.id.action_menu_search);


        searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(todoItemListFragment);

        edit.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_edit:
                if (isEnabled){
                    edit.setIcon(R.drawable.ic_edit);
                }else {
                    edit.setIcon(R.drawable.ic_check);
                }
                editOnClickListener.editOnClickListener(isEnabled);
                isEnabled = !isEnabled;
                return true;
            case R.id.action_menu_sort:
                sortOnClickListener.sortOnClickListener();
                return true;
        }

        return true;
    }


}

