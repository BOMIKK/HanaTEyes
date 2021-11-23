package com.example.hanateyes;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String fin_num;
    public String account;

    public User(String name, String fin_num, String account) {
        this.name=name;
        this.fin_num=fin_num;
        this.account=account;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", fin_num='" + fin_num + '\'' +
                ", account='" + account + '\'' +
                '}';
    }

    public String getFin_num() {
        return fin_num;
    }

    public void setFin_num(String fin_num) {
        this.fin_num = fin_num;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}