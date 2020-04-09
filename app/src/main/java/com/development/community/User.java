package com.development.community;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String state;
    private String town;
    private String address;
    private String bio;

    public User(){
        name = "";
        state = "";
        town = "";
        address = "";
        bio = "";
    }

}
