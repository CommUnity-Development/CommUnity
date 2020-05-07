package com.development.community;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String state;
    private String town;
    private String address;
    private String bio;
    private String profilePicUrl;
    private String token;
    private String uid;

    public User(){
        name = "";
        state = "";
        town = "";
        address = "";
        bio = "";
        profilePicUrl = "";
        token = "";
    }

    public User(String uid, String givenName, String givenState, String givenTown, String givenAddress, String givenBio){
        name = givenName;
        state = givenState;
        town = givenTown;
        address = givenAddress;
        bio = givenBio;
        profilePicUrl = "";
        token = "";
        this.uid = uid;
    }

    public User(String uid, String givenName, String givenState, String givenTown, String givenAddress, String givenBio, String givenProfilePicUrl){

        name = givenName;
        state = givenState;
        town = givenTown;
        address = givenAddress;
        bio = givenBio;
        profilePicUrl = givenProfilePicUrl;
        this.uid = uid;
        token = "";

    }

    public User(String givenName,String givenToken){
        token = givenToken;
        name = givenName;
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

    public void setUid(String uid) { this.uid = uid; }

    public String getUid() { return this.uid; }

    @Override @NonNull
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", town='" + town + '\'' +
                ", address='" + address + '\'' +
                ", bio='" + bio + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }


    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


}
