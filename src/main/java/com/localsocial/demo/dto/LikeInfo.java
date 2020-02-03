package com.localsocial.demo.dto;

public class LikeInfo {
    private String username;
    private Boolean like;  //true for like, false for unlike

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }
}
