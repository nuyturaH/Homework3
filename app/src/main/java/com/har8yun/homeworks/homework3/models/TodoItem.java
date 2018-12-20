package com.har8yun.homeworks.homework3.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class TodoItem implements Parcelable{

    private long id = new Random().nextLong();
    private String title;
    private String description;
    private Date date = new Date();
    private String repetition;
    private int priority;
    private ArrayList<User> users;

    public TodoItem() {
    }

    public TodoItem(long id, String title, String description, Date date, String repetition, int priority, ArrayList<User> users) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.repetition = repetition;
        this.priority = priority;
        this.users = users;
    }

    protected TodoItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
        repetition = in.readString();
        priority = in.readInt();
        users = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date.getTime());
        dest.writeString(repetition);
        dest.writeInt(priority);
        dest.writeTypedList(users);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TodoItem> CREATOR = new Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }


}