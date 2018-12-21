package com.har8yun.homeworks.homework3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.har8yun.homeworks.homework3.R;
import com.har8yun.homeworks.homework3.adapter.ToDoItemRecyclerAdapter;
import com.har8yun.homeworks.homework3.models.TodoItem;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity {
    public static final String ARG_TODOITEM2 = "arg.todoitem2";
    public static final int REQUEST_CODE_1 = 13;
    private static final int REQUEST_CODE_EDIT = 14;
    public static TodoItem todoItem;
    TodoItem item;

    List<TodoItem> todoItemList = new ArrayList<>();

    TextView infoText;
    ToDoItemRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoText = findViewById(R.id.info);

        findViewById(R.id.btn1_activity_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateItemActivity();
            }
        });
    }

    private void openCreateItemActivity(){
        Intent intent = new Intent(this, CreateItemActivity.class);
        startActivityForResult(intent, REQUEST_CODE_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED)
        switch (requestCode) {
            case REQUEST_CODE_1:
                if (resultCode == RESULT_OK) {
                    handleTodoItemResult(data);

                }
                break;
            case REQUEST_CODE_EDIT: {
                if (resultCode == RESULT_OK) {
                    TodoItem todoItem = data.getParcelableExtra(CreateItemActivity.ARG_TODOITEM);
                    mRecyclerAdapter.updateItem(todoItem);

                    for (int i = 0; i < todoItemList.size(); i++) {
                        if (todoItem.getId()==todoItemList.get(i).getId()){
                            todoItemList.set(i, todoItem);
                        }
                    }

                }
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void handleTodoItemResult(Intent data) {
        Bundle args = data.getExtras();
        if (args != null) {
            todoItem = data.getExtras().getParcelable(CreateItemActivity.ARG_TODOITEM);
            todoItemList.add(todoItem);
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        mRecyclerAdapter = new ToDoItemRecyclerAdapter();
        mRecyclerAdapter.setmOnRvItemClickListener(
                new ToDoItemRecyclerAdapter.OnRvItemClickListener() {
                    @Override
                    public void onItemClicked(int pos) {

                        item = todoItemList.get(pos);
                        //Toast.makeText(MainActivity.this, "Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        openEditTodoItem(item);
                    }
                }
        );

        RecyclerView recyclerView = findViewById(R.id.recycler_activity_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.addItems(todoItemList);
    }

    private void openEditTodoItem(TodoItem todoItem) {
        Intent intent = new Intent(this, CreateItemActivity.class);
        intent.putExtra(CreateItemActivity.ARG_TODO_ITEM, todoItem);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }
}
