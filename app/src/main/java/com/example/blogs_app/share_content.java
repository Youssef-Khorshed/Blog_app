package com.example.blogs_app;

public class share_content {

   private String shared_img,share_user_img,shared_user_name,shared_user_date
           ,shared_user_title, shared_user_description,user_img,user_name
           ,user_date,user_comment,shared_post_key;

    public share_content() {
    }


    public share_content(String shared_img, String share_user_img, String shared_user_name, String shared_user_date, String shared_user_title, String shared_user_description, String user_img, String user_name, String user_date, String user_comment, String shared_post_key) {
        this.shared_img = shared_img;
        this.share_user_img = share_user_img;
        this.shared_user_name = shared_user_name;
        this.shared_user_date = shared_user_date;
        this.shared_user_title = shared_user_title;
        this.shared_user_description = shared_user_description;
        this.user_img = user_img;
        this.user_name = user_name;
        this.user_date = user_date;
        this.user_comment = user_comment;
        this.shared_post_key = shared_post_key;
    }

    public String getShared_img() {
        return shared_img;
    }

    public void setShared_img(String shared_img) {
        this.shared_img = shared_img;
    }

    public String getShare_user_img() {
        return share_user_img;
    }

    public void setShare_user_img(String share_user_img) {
        this.share_user_img = share_user_img;
    }

    public String getShared_user_name() {
        return shared_user_name;
    }

    public void setShared_user_name(String shared_user_name) {
        this.shared_user_name = shared_user_name;
    }

    public String getShared_user_date() {
        return shared_user_date;
    }

    public void setShared_user_date(String shared_user_date) {
        this.shared_user_date = shared_user_date;
    }

    public String getShared_user_title() {
        return shared_user_title;
    }

    public void setShared_user_title(String shared_user_title) {
        this.shared_user_title = shared_user_title;
    }

    public String getShared_user_description() {
        return shared_user_description;
    }

    public void setShared_user_description(String shared_user_description) {
        this.shared_user_description = shared_user_description;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_date() {
        return user_date;
    }

    public void setUser_date(String user_date) {
        this.user_date = user_date;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getShared_post_key() {
        return shared_post_key;
    }

    public void setShared_post_key(String shared_post_key) {
        this.shared_post_key = shared_post_key;
    }
}
