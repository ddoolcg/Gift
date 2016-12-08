package com.lcg.gift.bean;

import java.io.Serializable;

public class UserInfor implements Serializable {
    private long id;
    private String username;
    private String token;
    private long lastLoginTime;
    private int points;
    private String nickname;
    private long robberyTime, robTime;

    public UserInfor() {
        token = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getRobberyTime() {
        return robberyTime;
    }

    public void setRobberyTime(long robberyTime) {
        this.robberyTime = robberyTime;
    }

    public long getRobTime() {
        return robTime;
    }

    public void setRobTime(long robTime) {
        this.robTime = robTime;
    }

    @Override
    public String toString() {
        return "UserInfor{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", points=" + points +
                ", nickname='" + nickname + '\'' +
                ", robberyTime=" + robberyTime +
                ", robTime=" + robTime +
                '}';
    }
}