package com.example.cloneappinstagram.Model;

public class User {

    private String id;
    private String username,fullname,imageUrl,bio;

    public User() {
    }

    public User(String id, String username, String fullname, String imageUrl, String bio) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
