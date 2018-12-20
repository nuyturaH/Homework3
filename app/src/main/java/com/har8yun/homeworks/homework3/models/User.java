package com.har8yun.homeworks.homework3.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
    private String fullName;
    private String mail;

    public User(String fullName, String mail) {
        this.fullName = fullName;
        this.mail = mail;
    }

    public User(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(mail);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return  User.createFromParcel(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private static User createFromParcel(Parcel source) {
        User user = new User();

        user.fullName = source.readString();
        user.mail = source.readString();

        return user;
    }

    @Override
    public String toString() {
        return fullName + "|" + mail + "\n";
    }
}
