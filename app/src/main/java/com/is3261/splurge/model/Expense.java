package com.is3261.splurge.model;

/**
 * Created by Vicky on 27/10/15.
 */
public class Expense  {
    public final long id;

    protected String amount;
    protected String pay;
    protected User spender;
    protected User borrower;
    protected boolean isSettled;

    public Expense(long id) {
        this.id = id;
    }

    public Expense(long id, String amount, String pay, User spender, User borrower, boolean isSettled) {
        this.id = id;
        this.amount = amount;
        this.pay = pay;
        this.spender = spender;
        this.borrower = borrower;
        this.isSettled = isSettled;
    }

    public long getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public User getSpender() {
        return spender;
    }

    public void setSpender(User spender) {
        this.spender = spender;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setIsSettled(boolean isSettled) {
        this.isSettled = isSettled;
    }
}
