package com.is3261.splurge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.base.GridViewAdapter;
import com.is3261.splurge.model.Expense;
import com.is3261.splurge.view.DebtCard;

import java.util.ArrayList;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class DebtsAdapter extends GridViewAdapter<Expense> {

    public DebtsAdapter(ArrayList<Expense> mItems, Context mContext) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridview_debt, parent, false);
            holder = new ViewHolder();
            holder.debtCard = (DebtCard) convertView.findViewById(R.id.card_debt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expense expense = getItem(position);
        holder.debtCard.update(expense);

        return convertView;
    }

    static class ViewHolder {
        DebtCard debtCard;
    }
}

