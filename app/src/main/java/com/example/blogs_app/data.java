package com.example.blogs_app;

public class data {
   private String name,phone,email,userphoto, userid;

    public data(String name, String phone, String email, String userphoto, String userid) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userphoto = userphoto;
        this.userid = userid;
    }

    public data() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
