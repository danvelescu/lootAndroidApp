package com.example.aplicationloot001;

import com.example.aplicationloot001.DTOmodel.User;

public class Message extends User {
    private int id;
    private String subject;
    private String text;

    public Message(){

    }
    public Message(int id,String subject,String text){
        this.id = id;
        this.subject = subject;
        this.text = text;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
    public String toString() {
        return "Id:[" + this.getId() + "] Subject:[" + this.getSubject() + "] Text:[" + this.getText() + "]";
    }
}
