package com.development.community;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

/**
 * A Class that stores information about users
 */
public class User implements Serializable {
    private String name;
    private String state;
    private String town;
    private String address;
    private String bio;
    private String profilePicUrl;
    private String token;
    private String uid;


    /**
     * Default Constructor
     */
    public User(){
        name = "";
        state = "";
        town = "";
        address = "";
        bio = "";
        profilePicUrl = "";
        token = "";
    }

    /**
     * Parameter Constructor
     * @param uid The user's User ID
     * @param givenName The User's name or username
     * @param givenState The User's state
     * @param givenTown The User's town
     * @param givenAddress The user's address
     * @param givenBio The user's bio
     */
    public User(String uid, String givenName, String givenState, String givenTown, String givenAddress, String givenBio){
        name = givenName;
        state = givenState;
        town = givenTown;
        address = givenAddress;
        bio = givenBio;
        this.uid = uid;
    }

    /**
     * Parameter Constructor
     * @param uid The user's User ID
     * @param givenName The User's name or username
     * @param givenState The User's state
     * @param givenTown The User's town
     * @param givenAddress The user's address
     * @param givenBio The user's bio
     * @param givenToken The user's token
     */
    public User(String uid, String givenName, String givenState, String givenTown, String givenAddress, String givenBio,String givenToken){
        name = givenName;
        state = givenState;
        town = givenTown;
        address = givenAddress;
        bio = givenBio;
        token = givenToken;
        this.uid = uid;
    }


    /**
     * Parameter Constructor
     * @param name The user's name
     * @param state The user's state
     * @param town The user's town
     * @param address The user's address
     * @param bio The user's bio
     * @param profilePicUrl The user's profile picture URL
     * @param token The user's token
     * @param uid The user's User ID
     */
    public User(String uid,String name, String state, String town, String address, String bio, String profilePicUrl, String token) {
        this.name = name;
        this.state = state;
        this.town = town;
        this.address = address;
        this.bio = bio;
        this.profilePicUrl = profilePicUrl;
        this.token = token;
        this.uid = uid;
    }

    /**
     * Parameter Constructor
     * @param name The user's name
     * @param state The user's state
     * @param town The user's town
     * @param address The user's address
     * @param bio The user's bio
     * @param givenprofilePicUrl The user's profile picture URL
     * @param uid The user's User ID
     * @param filler1 dont ask...
     * @param filler2 dont ask again...
     */
    public User(String uid,String name, String state, String town, String address, String bio, String givenprofilePicUrl, String filler1,String filler2) {
        this.name = name;
        this.state = state;
        this.town = town;
        this.address = address;
        this.bio = bio;
        profilePicUrl = givenprofilePicUrl;
        this.uid = uid;
    }


    /**
     * Parameter Constructor
     * @param name The user's name
     * @param state The user's state
     * @param town The user's town
     * @param address The user's address
     * @param bio The user's bio
     * @param profilePicUrl The user's profile picture URL
     * @param uid The user's User ID
     */

    /**
     * Parameter Constructor
     * @param givenName The user's name
     * @param givenToken The user's token
     */
    public User(String givenName, String givenToken){
        token = givenToken;
        name = givenName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
