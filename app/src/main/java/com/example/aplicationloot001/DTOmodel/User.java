package com.example.aplicationloot001.DTOmodel;

import android.content.Entity;


public class User  {

    private int id;

    private String username;
    private String password;
    private int lootcoin;

    public User(){}

    public User(int id ,String username,String password){
        this.id=id;
        this.username=username;
        this.password=password;
        this.lootcoin=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLootcoin() {
        return lootcoin;
    }

    public void setLootcoin(int lootcoin) {
        this.lootcoin = lootcoin;
    }
}
