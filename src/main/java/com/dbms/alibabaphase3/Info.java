package com.dbms.alibabaphase3;

import Model.User;

public class Info {

    private static Info instance;
    private User user;

    private Info(){

    }

    public static Info getInstance(){
        if(instance == null){
            instance = new Info();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
