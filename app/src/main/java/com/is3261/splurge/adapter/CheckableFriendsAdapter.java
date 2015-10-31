package com.is3261.splurge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.base.ListViewAdapter;
import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junwen29 on 10/31/2015.
 */
public class CheckableFriendsAdapter extends ListViewAdapter<User> {

    Map<User, Boolean> mMap;

    public CheckableFriendsAdapter(ArrayList<User> mItems, Context mContext) {
        super(mItems, mContext);

        // add all friends to a hash map
        mMap = new HashMap<>();
        for (User user : mItems) {
            mMap.put(user, false);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sm_members, parent, false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tvName);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox_isJoin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String username = getItem(position).getUsername();
        holder.text.setText(username);
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                User user = getItem(position);
                mMap.put(user, isChecked);
            }
        });

        return convertView;
    }

    public ArrayList<User> getSelectedFriends(){
        ArrayList<User> list = new ArrayList<>();
        for (User user : mItems) {
            if (mMap.get(user))
                list.add(user);
        }

        return list;
    }

    static class ViewHolder {
        TextView text;
        CheckBox checkbox;
    }
}
