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

    public User(String givenName, String givenState, String givenTown, String givenAddress, String givenBio){
        name = givenName;
        state = givenState;
        town = givenTown;
        address = givenAddress;
        bio = givenBio;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", town='" + town + '\'' +
                ", address='" + address + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }


}
