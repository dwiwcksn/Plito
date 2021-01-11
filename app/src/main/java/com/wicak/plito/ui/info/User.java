package com.wicak.plito.ui.info;

public class User {



    private String id;
    private String username;
    private String imageURL;
    private String email;
    private String kodeKelas;

    public User(String id, String username, String imageURL, String email, String kodeKelas) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.email = email;
        this.kodeKelas = kodeKelas;
    }

    public User(){

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getKodeKelas() {
        return kodeKelas;
    }

    public void setKodeKelas(String kodeKelas) {
        this.kodeKelas = kodeKelas;
    }
}
