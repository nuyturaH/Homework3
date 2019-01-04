package com.har8yun.homeworks.homework3.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.har8yun.homeworks.homework3.R;
import com.har8yun.homeworks.homework3.models.TodoItem;
import com.har8yun.homeworks.homework3.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CreateItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String ARG_TODOITEM = "arg.todoitem";
    public static final String ARG_TODO_ITEM = "arg.todo.item";

    private TodoItem mTodoItem = new TodoItem();
    private Calendar cal = Calendar.getInstance();
    private boolean isDateViewPressed;

    private EditText titleInput;
    private EditText descriptionInput;
    private TextView dateView;
    private TextView timeView;
    private RadioGroup radioPriorityGroup;
    private RadioGroup radioRepetitionGroup;

    private RadioButton radioPriorityButton;
    private RadioButton radioRepetitionButton;
    private CheckBox repeatCheckbox;
    private EditText usersInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        titleInput = findViewById(R.id.input_title);
        descriptionInput = findViewById(R.id.input_description);
        dateView = findViewById(R.id.date);
        timeView = findViewById(R.id.time);
        //usersInput = findViewById(R.id.input_users);
        radioPriorityGroup = findViewById(R.id.radiogroup_priorities);
        radioRepetitionGroup = findViewById(R.id.radiogroup_repetitions);
        repeatCheckbox = findViewById(R.id.checkbox_repeat);

        Button saveButton = findViewById(R.id.btn_save);

        if (getIntent().hasExtra(ARG_TODO_ITEM)) {
            mTodoItem = getIntent().getParcelableExtra(ARG_TODO_ITEM);
            fillData(mTodoItem);
        }

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDateViewPressed = true;
                chooseDate();

            }
        });

        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDateViewPressed){
                    chooseTime();
                }
                else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "At First Select Date", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        repeatCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    radioRepetitionGroup.setVisibility(View.VISIBLE);
                    radioRepetitionGroup.check(R.id.radiobutton_daily);



                }else {
                    radioRepetitionGroup.clearCheck();
                    radioRepetitionGroup.setVisibility(View.GONE);

                    mTodoItem.setRepetition(null);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodoItem.setTitle(titleInput.getText().toString());
                mTodoItem.setDescription(descriptionInput.getText().toString());
                //mTodoItem.setUsers(giveUsers());

                int prSelectedId = radioPriorityGroup.getCheckedRadioButtonId();
                radioPriorityButton = findViewById(prSelectedId);
                switch (radioPriorityButton.getText().toString()){
                    case "Red":
                        mTodoItem.setPriority(0xFFCC0000);
                        break;
                    case "Orange":
                        mTodoItem.setPriority(0xFFFF8800);
                        break;
                    case "Blue":
                        mTodoItem.setPriority(0xFF0099CC);
                        break;
                    case "Gray":
                        mTodoItem.setPriority(0xFF888888);
                        break;
                    }


                if (titleInput.getText().toString().equals("")){
                    titleInput.setError("Enter Title");
                    return;
                }
                if (descriptionInput.getText().toString().equals("")){
                    descriptionInput.setError("Enter Description");
                    return;
                }
                if (!isDateViewPressed){
                    Toast.makeText(CreateItemActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(repeatCheckbox.isChecked()){
                    int repSelectedId = radioRepetitionGroup.getCheckedRadioButtonId();
                    radioRepetitionButton = findViewById(repSelectedId);
                    mTodoItem.setRepetition(radioRepetitionButton.getText().toString());
                }

                save();
                finish();
            }
        });


        descriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (null != descriptionInput.getLayout() && descriptionInput.getLayout().getLineCount() > 3) {
                    descriptionInput.getText().delete(descriptionInput.getText().length() - 1, descriptionInput.getText().length());
                    Toast.makeText(CreateItemActivity.this, "You can only write 3 lines", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fillData(TodoItem todoItem) {
        RadioButton dRadioRepetitionButton = findViewById(R.id.radiobutton_daily);
        RadioButton wRadioRepetitionButton = findViewById(R.id.radiobutton_weekly);
        RadioButton mRadioRepetitionButton = findViewById(R.id.radiobutton_monthly);

        titleInput.setText(todoItem.getTitle());
        descriptionInput.setText(todoItem.getDescription());
        Date mDate = todoItem.getDate();
        cal.setTime(mDate);
        isDateViewPressed = true;
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat simpleDateformat2 = new SimpleDateFormat("hh:mm");
        dateView.setText(simpleDateformat.format(todoItem.getDate()));
        timeView.setText(simpleDateformat2.format(todoItem.getDate()));
        if (todoItem.getRepetition()!=null) {
            repeatCheckbox.setChecked(true);
            radioRepetitionGroup.setVisibility(View.VISIBLE);

            if (todoItem.getRepetition().equals(dRadioRepetitionButton.getText().toString())){
                dRadioRepetitionButton.setChecked(true);
            }else if (todoItem.getRepetition().equals(wRadioRepetitionButton.getText().toString())){
                wRadioRepetitionButton.setChecked(true);
            }else if (todoItem.getRepetition().equals(mRadioRepetitionButton.getText().toString())){
                mRadioRepetitionButton.setChecked(true);
            }
        }else {
            radioRepetitionGroup.clearCheck();
            radioRepetitionGroup.setVisibility(View.GONE);
        }

        switch (todoItem.getPriority()){
            case 0xFFCC0000:
                radioPriorityGroup.check(R.id.radio_btn_red);
                break;
            case 0xFFFF8800:
                radioPriorityGroup.check(R.id.radio_btn_orange);
                break;
            case 0xFF0099CC:
                radioPriorityGroup.check(R.id.radio_btn_blue);
                break;
            case 0xFF888888:
                radioPriorityGroup.check(R.id.radio_btn_gray);
                break;
        }



    }

    private void chooseDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateItemActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.setTime(mTodoItem.getDate());
                cal.set(Calendar.YEAR,year);
                cal.set(Calendar.MONTH,monthOfYear);
                cal.set(Calendar.DATE,dayOfMonth);
                mTodoItem.setDate(cal.getTime());

                SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd MMM yyyy");
                dateView.setText(simpleDateformat.format(mTodoItem.getDate()));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void chooseTime(){
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateItemActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.setTime(mTodoItem.getDate());
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);
                mTodoItem.setDate(cal.getTime());

                SimpleDateFormat simpleDateformat2 = new SimpleDateFormat("hh:mm");
                timeView.setText(simpleDateformat2.format(mTodoItem.getDate()));
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

//    private ArrayList<User> giveUsers(){
//        ArrayList<User> users = new ArrayList<>();
//        if (!usersInput.getText().toString().matches("")) {
//            String line1[] = usersInput.getText().toString().split("\\r?\\n");
//            for (int i = 0; i < line1.length; i++) {
//                String line2[] = line1[i].split(",");
//                users.add(new User(line2[0], line2[1]));
//            }
//        }
//        return users;
//    }

    private void save() {
        if (mTodoItem != null) {
            Intent intent = new Intent();
            Bundle args = new Bundle();
            args.putParcelable(ARG_TODOITEM, mTodoItem);
            intent.putExtras(args);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}