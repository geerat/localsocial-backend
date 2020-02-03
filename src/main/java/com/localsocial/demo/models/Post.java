package com.localsocial.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @Column(name="post_id")
    private String postId;

    @Column(name="username")
    private String username;

    @Column(name="message")
    private String message;

    @Column(name="timestamp")
    private String timestamp;

    @Column(name="likes")
    private String likes;

    @Column(name="like_count")
    private int likeCount;

    @Transient
    private Boolean likeStatus;

    public Boolean getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(Boolean likeStatus) {
        this.likeStatus = likeStatus;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
