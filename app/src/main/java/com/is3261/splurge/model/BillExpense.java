package com.is3261.splurge.model;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class BillExpense {

    public final long id;

    protected String amount;
    protected String afterAmount;
    protected Float gst;
    protected Float svc;
    protected User spender;

    public BillExpense(long id) {
        this.id = id;
    }

    public BillExpense(long id, String amount, String afterAmount, Float gst, Float svc, User spender) {
        this.id = id;
        this.amount = amount;
        this.afterAmount = afterAmount;
        this.gst = gst;
        this.svc = svc;
        this.spender = spender;
    }
}
