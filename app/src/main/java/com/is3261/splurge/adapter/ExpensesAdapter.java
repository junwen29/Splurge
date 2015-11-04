package com.is3261.splurge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.base.GridViewAdapter;
import com.is3261.splurge.model.Expense;
import com.is3261.splurge.view.ExpenseCard;

import java.util.ArrayList;

/**
 * Created by junwen29 on 11/4/2015.
 */
public class ExpensesAdapter extends GridViewAdapter<Expense> {

    public ExpensesAdapter(ArrayList<Expense> mItems, Context mContext) {
        super(mItems, mContext);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridview_expense, parent, false);
            holder = new ViewHolder();
            holder.expenseCard = (ExpenseCard) convertView.findViewById(R.id.card_expense);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expense expense = getItem(position);
        holder.expenseCard.update(expense);

        return convertView;
    }

    static class ViewHolder {
        ExpenseCard expenseCard;
    }
}
