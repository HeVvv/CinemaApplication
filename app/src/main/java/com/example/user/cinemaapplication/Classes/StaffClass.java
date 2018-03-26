package com.example.user.cinemaapplication.Classes;

import java.util.List;

/**
 * Created by User on 01.11.2017.
 */

public class StaffClass {

    private static StaffClass mainUser;

    public static StaffClass getUser() {
        return mainUser;
    }
    public static void setUser(StaffClass mainUser) {
        StaffClass.mainUser = mainUser;
    }
    private String login;
    private String password;
    private List<String> rolesSet;
    private String name;

    public StaffClass(){
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    public List<String> getRolesSet(){
        return rolesSet;
    }
    public String getName(){
        return name;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public void setPasssword(String password){
        this.password = password;
    }
    public void setname(String name){
        this.name = name;
    }
    public void setRolesSet(List<String> rolesSet){
        this.rolesSet = rolesSet;
    }

}
