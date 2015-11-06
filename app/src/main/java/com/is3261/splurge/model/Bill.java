package com.is3261.splurge.model;

import java.util.List;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class Bill {

    public long id;

    protected List<BillExpense> billExpenses;
    protected List<User> users;

    public Bill(long id) {
        this.id = id;
    }
}
