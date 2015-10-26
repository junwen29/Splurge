package com.is3261.splurge.dummy;

import com.is3261.splurge.model.User;

import java.util.ArrayList;

/**
 * Created by home on 26/10/15.
 */
public class LoadDummyUsers {

    ArrayList<User> userList;

    public LoadDummyUsers (){
        User u1;
        User u2;
        User u3;
        u1 = new User();
        u1.setUsername("Jun Wen");
        u2 = new User();
        u2.setUsername("Vicky Zheng");
        u3 = new User();
        u3.setUsername("Akira Hirakawa");
        userList = new ArrayList<User> ();
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);

    }

    public ArrayList<User> getUserList() {
        return userList;
    }

}
