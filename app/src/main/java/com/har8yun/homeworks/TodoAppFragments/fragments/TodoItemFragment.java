package com.har8yun.homeworks.TodoAppFragments.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.har8yun.homeworks.TodoAppFragments.activities.MainActivity;
import com.har8yun.homeworks.TodoAppFragments.R;
import com.har8yun.homeworks.TodoAppFragments.util.EditOnClickListener;
import com.har8yun.homeworks.TodoAppFragments.models.TodoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TodoItemFragment extends Fragment implements AdapterView.OnItemSelectedListener, EditOnClickListener {

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
    Button saveButton;

    private OnFragmentInteractionListener mListener;



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener.editButton(false);
    }

    void enableAll(boolean b){
        titleInput.setEnabled(b);
        descriptionInput.setEnabled(b);
        dateView.setEnabled(b);
        timeView.setEnabled(b);
        repeatCheckbox.setEnabled(b);
        saveButton.setEnabled(!b);
        for(int i = 0; i < radioPriorityGroup.getChildCount(); i++){
            (radioPriorityGroup.getChildAt(i)).setEnabled(b);
        }
        for(int i = 0; i < radioRepetitionGroup.getChildCount(); i++){
            (radioRepetitionGroup.getChildAt(i)).setEnabled(b);
        }
            Log.v("hhhh","enableAll");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_todo_item, container, false);

        mListener.editButton(true);

        titleInput = view.findViewById(R.id.input_title);
        descriptionInput = view.findViewById(R.id.input_description);
        dateView = view.findViewById(R.id.date);
        timeView = view.findViewById(R.id.time);
        radioPriorityGroup = view.findViewById(R.id.radiogroup_priorities);
        radioRepetitionGroup = view.findViewById(R.id.radiogroup_repetitions);
        repeatCheckbox = view.findViewById(R.id.checkbox_repeat);

        saveButton = view.findViewById(R.id.btn_save);


        if(TodoItemListFragment.isEdited){
            mTodoItem = TodoItemListFragment.item;
            fillData(mTodoItem,view);
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
                    Snackbar snackbar = Snackbar.make(view.findViewById(android.R.id.content), "At First Select Date", Snackbar.LENGTH_LONG);
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

                int prSelectedId = radioPriorityGroup.getCheckedRadioButtonId();
                radioPriorityButton = view.findViewById(prSelectedId);
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
                    Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(repeatCheckbox.isChecked()){
                    int repSelectedId = radioRepetitionGroup.getCheckedRadioButtonId();
                    radioRepetitionButton = view.findViewById(repSelectedId);
                    mTodoItem.setRepetition(radioRepetitionButton.getText().toString());
                }

                mListener.onFragmentInteraction(mTodoItem);
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
                    Toast.makeText(getActivity(), "You can only write 3 lines", Toast.LENGTH_SHORT).show();
                }
            }
        });
        MainActivity.isEnabled = false;
        enableAll(false);

        return  view;

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

    @Override
    public void editOnClickListener(boolean b) {
        enableAll(!b);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(TodoItem t);
        void editButton(boolean b);
    }

    private void chooseDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void fillData(TodoItem todoItem, View v) {
        RadioButton dRadioRepetitionButton = v.findViewById(R.id.radiobutton_daily);
        RadioButton wRadioRepetitionButton = v.findViewById(R.id.radiobutton_weekly);
        RadioButton mRadioRepetitionButton = v.findViewById(R.id.radiobutton_monthly);

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
}
