package com.is3261.splurge.helper;

import com.is3261.splurge.model.Expense;
import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by junwen29 on 11/1/2015.
 */
public class GreedyHelper {

// Main for the purpose of test
//    public static void main(String[] args) {
//        ArrayList<User> users = new ArrayList<>();
//
//
//        User u1 = new User();
//        u1.setUsername("TJW");
//        users.add(u1);
//        User u2 = new User();
//        u2.setUsername("Jw");
//        users.add(u2);
//        User u3 = new User();
//        u3.setUsername("Vicky");
//        users.add(u3);
//        User u4 = new User();
//        u4.setUsername("Akira");
//        users.add(u4);
//
//        Map<User, Float> expenseMap = new HashMap<>();
//        Map<User, Float> paymentMap = new HashMap<>();
//
//        expenseMap.put(u1, new Float(5.5));
//        expenseMap.put(u2, new Float(5.5));
//        expenseMap.put(u3, new Float(5.5));
//        expenseMap.put(u4, new Float(5.5));
//
//        paymentMap.put(u1, new Float(10.0));
//        paymentMap.put(u2, new Float(0.0));
//        paymentMap.put(u3, new Float(12.0));
//        paymentMap.put(u4, new Float(0.0));
//
//        GreedyHelper gh = new GreedyHelper(users, expenseMap, paymentMap);
//
//        gh.constructBills();
//
//    }

    private ArrayList<User> users;

    //note number of users on both maps might not tally
    private Map<User, Float> expenseMap;
    private Map<User, Float> paymentMap;

    private Map<User, Float> debtMap;
    private Map<User, Float> lendMap;

    //temporary sorted list
    private Map<User, Float> sortedDebtMap;
    private Map<User, Float> sortedLendMap;

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
//            debtMap.put(user, Float.valueOf("0.00")); // puts a default value

            if (expenseMap.containsKey(user)){
                //user got spend
                Float expense = expenseMap.get(user);
                Float payment = paymentMap.get(user);
                Float debt = expense - payment;
                if (debt > 0) {
                    debtMap.put(user, debt);
                    System.out.println(user.getUsername() + " current total debts " + debt);
                }
            }
        }
    }

    public void createLendMap(){
        lendMap = new HashMap<>();
        for (User user: users) {
//            lendMap.put(user, Float.valueOf("0.00")); // puts a default value

            if (expenseMap.containsKey(user)){
                //user got spend
                Float expense = expenseMap.get(user);
                Float payment = paymentMap.get(user);
                Float lend = payment - expense ;
                if (lend > 0) {
                    lendMap.put(user, lend);
                System.out.println(user.getUsername() + " current total lends " + lend);
                }
            }
        }
    }



    private void constructBills(){

        Random generator = new Random();
        Float currentDebt;
        Float currentLend;
        Float currentBalance;
        Expense exp;

        while(lendMap.size() >0) {

            Object[] lendValues = lendMap.keySet().toArray();
            User randomLender = (User) lendValues[generator.nextInt(lendValues.length)];

            generator = new Random();
            Object[] debtValues = debtMap.keySet().toArray();

            System.out.println(debtMap.toString());

            User randomDebter = (User) debtValues[generator.nextInt(debtValues.length)];

            currentDebt = debtMap.get(randomDebter);
            currentLend = lendMap.get(randomLender);
            currentBalance = currentDebt - currentLend;

            if(currentBalance == 0 ){
                lendMap.remove(randomLender);
                debtMap.remove(randomDebter);
            }
            if(currentBalance > 0 ){

                exp = new Expense((new Random()).nextInt(), currentLend.toString(), "pay", randomLender, randomDebter, false);
                System.out.println(exp.getBorrower() + " borrows " + exp.getSpender() + " " + exp.getAmount());
                currentDebt = Math.abs(currentBalance);
                lendMap.remove(randomLender);
                debtMap.put(randomDebter, currentDebt);
            }
            if(currentBalance < 0)
            {
                exp = new Expense((new Random()).nextInt(), currentDebt.toString(), "pay", randomLender, randomDebter, false);
                System.out.println(exp.getBorrower() + " borrows " + exp.getSpender() + " " + exp.getAmount());
                currentLend = Math.abs(currentBalance);
                debtMap.remove(randomDebter);
                lendMap.put(randomLender, currentLend);
            }

        }
    }
}
