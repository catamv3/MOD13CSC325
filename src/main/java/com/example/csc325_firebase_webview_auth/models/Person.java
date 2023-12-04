/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.csc325_firebase_webview_auth.models;

/**
 *
 * @author MoaathAlrajab
 */
public class Person {

    private String username;
    private String password;
    private String name;
    private String major;
    private int age;

    public Person(String userName, String password, String name, String major, int age) {
        this.username = userName;
        this.password = password;
        this.name = name;
        this.major = major;
        this.age = age;
    }

    public Person(String name, String major, int age) {
        this.name = name;
        this.major = major;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
    
}
