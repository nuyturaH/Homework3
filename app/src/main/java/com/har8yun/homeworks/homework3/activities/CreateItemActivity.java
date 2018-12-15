package com.har8yun.homeworks.homework3.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.har8yun.homeworks.homework3.R;
import com.har8yun.homeworks.homework3.models.TodoItem;
import com.har8yun.homeworks.homework3.models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreateItemActivity extends AppCompatActivity {
    public static final String ARG_TODOITEM = "arg.todoitem";

    private TodoItem mTodoItem = new TodoItem();

    EditText titleInput;
    EditText descriptionInput;
    TextView dateView;
    DatePickerDialog datePickerDialog;
    EditText usersInput;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        titleInput = findViewById(R.id.input_title);
        descriptionInput = findViewById(R.id.input_description);
        dateView = findViewById(R.id.date);
        usersInput = findViewById(R.id.input_users);
        saveButton = findViewById(R.id.btn_save);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseDate();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodoItem.setTitle(titleInput.getText().toString());
                mTodoItem.setDescription(descriptionInput.getText().toString());
                mTodoItem.setUsers(giveUsers());
                save();
                finish();
            }
        });

    }

    private void chooseDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(CreateItemActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date mDate = new Date(year-1900,monthOfYear,dayOfMonth);
                mTodoItem.setDate(mDate);
                dateView.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private List giveUsers(){
        List<User> users = new ArrayList<>();
        String line1[] = usersInput.getText().toString().split("\\r?\\n");
        Log.e("abov", ""+line1.length);
        if(line1.length==0) {
            return users;
        }
        for (int i = 0; i < line1.length; i++) {
            String line2[] = line1[i].split(",");

            Log.e("abov2", ""+line2.length);
            users.add(new User(line2[0],line2[1]));
            Log.e("abov3", ""+line2[0] + "====" + line2[1]);

        }
        return users;
    }

    private void save() {
        if (mTodoItem != null) {
            Intent intent = new Intent();
            Bundle args = new Bundle();
            args.putParcelable(ARG_TODOITEM, mTodoItem);
            for (User u: mTodoItem.getUsers()) {
                Log.e("abov8",""+u.getFullName()+"===="+u.getMail());
            }
            intent.putExtras(args);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
    }
}