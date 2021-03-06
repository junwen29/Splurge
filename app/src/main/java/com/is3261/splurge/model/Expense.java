package com.is3261.splurge.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Vicky on 27/10/15.
 */
public class Expense  {
    @SerializedName("id")
    public final long id;
    @SerializedName("amount")
    protected String amount;
    @SerializedName("currency")
    protected String currency;
    @SerializedName("spender")
    protected User spender;
    @SerializedName("borrower")
    protected User borrower;
    @SerializedName("isSettled")
    protected boolean isSettled;
    @SerializedName("created_at")
    private Date createdAt;

    public Expense(long id) {
        this.id = id;
    }

    public Expense(long id, String amount, String currency, User spender, User borrower, boolean isSettled) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Date getCreatedAt() {
        return createdAt;
    }
}
