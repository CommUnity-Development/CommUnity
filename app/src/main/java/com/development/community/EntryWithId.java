package com.development.community;

public class EntryWithId extends Entry {

    private String id;

    public EntryWithId(Date date, Time time, String destination, String task, String username, int status, String id){
        super(date, time, destination, task, username, status);
        this.id = id;
    }

    public EntryWithId(){
        super();
        this.id = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
