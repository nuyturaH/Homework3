package com.har8yun.homeworks.homework3.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoItem implements Parcelable{
    private long id;
    private String title;
    private String description;
    private Date date = new Date();
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        for (User u : users) {
            Log.e("abov5",""+u.getFullName()+"===="+u.getMail());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date.getTime());
        dest.writeList(users);
    }

    public static final Parcelable.Creator<TodoItem> CREATOR = new Parcelable.Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel source) {
            return  TodoItem.createFromParcel(source);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };

    private static TodoItem createFromParcel(Parcel source) {
        TodoItem todoItem = new TodoItem();
        todoItem.users = new ArrayList<>();
        todoItem.id = source.readLong();
        todoItem.title = source.readString();
        todoItem.description = source.readString();
        todoItem.date = new Date(source.readLong());
        source.readTypedList(todoItem.users,User.CREATOR);
        return todoItem;
    }
}
