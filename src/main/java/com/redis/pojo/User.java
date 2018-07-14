package com.redis.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userName;
    private int age;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String userName, int age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {

        return "User[" + id + "," + userName +
                "," + age + "]";
    }
}
