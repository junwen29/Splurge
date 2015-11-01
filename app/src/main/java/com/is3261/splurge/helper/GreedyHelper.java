package com.is3261.splurge.helper;

import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 11/1/2015.
 */
public class GreedyHelper {

    private ArrayList<User> users;

    //note number of users on both maps might not tally
    private Map<User, Float> expenseMap;
    private Map<User, Float> paymentMap;

    private Map<User, Float> debtMap;
    private Map<User, Float> lendMap;

    public GreedyHelper(ArrayList<User> users, Map<User, Float> expenseMap, Map<User, Float> paymentMap) {
        this.users = users;
        this.expenseMap = expenseMap;
        this.paymentMap = paymentMap;
        init();
    }

    public void init(){
        createDebtMap();
        createLendMap();
    }

    public void createDebtMap(){
        debtMap = new HashMap<>();
        for (User user: users) {
            debtMap.put(user, Float.valueOf("0.00")); // puts a default value

            if (expenseMap.containsKey(user)){
                //user got spend
                Float expense = expenseMap.get(user);
                Float payment = paymentMap.get(user);
                Float debt = expense - payment;
                if (debt > 0)
                    debtMap.put(user, debt);
            }
        }
    }

    public void createLendMap(){
        lendMap = new HashMap<>();
        for (User user: users) {
            lendMap.put(user, Float.valueOf("0.00")); // puts a default value

            if (expenseMap.containsKey(user)){
                //user got spend
                Float expense = expenseMap.get(user);
                Float payment = paymentMap.get(user);
                Float lend = payment - expense ;
                if (lend > 0)
                    lendMap.put(user, lend);
            }
        }
    }
}
