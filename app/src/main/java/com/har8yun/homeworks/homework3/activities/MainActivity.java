package com.har8yun.homeworks.homework3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.har8yun.homeworks.homework3.R;
import com.har8yun.homeworks.homework3.models.TodoItem;
import com.har8yun.homeworks.homework3.models.User;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_1 = 13;

    TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoText = findViewById(R.id.info);

        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
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
        switch (requestCode) {
            case REQUEST_CODE_1:
                if (resultCode == RESULT_OK) {
                    handleTodoItemResult(data);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void handleTodoItemResult(Intent data) {
        Bundle args = data.getExtras();
        if (args != null) {
            TodoItem todoItem = data.getExtras().getParcelable(CreateItemActivity.ARG_TODOITEM);

            infoText.setVisibility(View.VISIBLE);
            infoText.setText("Title: " + todoItem.getTitle() + "\n"
                    + "Description: " + todoItem.getDescription() + "\n"
                    + "Date: " + todoItem.getDate().getDate() + "." + (todoItem.getDate().getMonth()+1) + "." + (todoItem.getDate().getYear()+1900) + "\n"
                    + "Users: "+ todoItem.getUsers().size()+"\n"+todoItem.getUsers().toString());


            for (User u: todoItem.getUsers()) {
                Log.e("abov8",""+u.getFullName()+"===="+u.getMail());
            }
        }
    }
}
