package com.is3261.splurge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.SpiltMealActivity;
import com.is3261.splurge.dummy.LoadDummyUsers;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SpiltMealFragment2 extends BaseFragment {

    ListView lv;
    LoadDummyUsers load = new LoadDummyUsers();
    ArrayList<User> list = load.getUserList();
    HashMap<User, Boolean> userMap;
    ArrayList<User> selectedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spilt_meal_fragment2, container, false);

        lv = (ListView) view.findViewById(R.id.listView_splitmeal_members);

        UsersAdapter adapter = new UsersAdapter(getActivity(), list);
        lv.setAdapter(adapter);
        Button mbtn = (Button) view.findViewById(R.id.button_sm_next2);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpiltMealActivity activity = (SpiltMealActivity) getActivity();
                selectedList = new ArrayList<>();
                userMap = activity.getUserMap();
                System.out.println("************************" + userMap.toString());
                    for(int i = 0; i < list.size(); i++){
                        if(userMap.get(list.get(i)) == Boolean.TRUE)
                            selectedList.add(list.get(i));
                    }
                activity.setSelectedFriendList(selectedList);
                if(activity.getUserExpense().size() < 0) {
                   activity.setUpUserItem(selectedList);
                }
            }
        });

        return view;
    }



    public class UsersAdapter extends ArrayAdapter<User> {

        User user;
        public UsersAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sm_members, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_isJoin);

            // Populate the data into the template view using the data object
            tvName.setText(user.getUsername());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   CheckBox cb = (CheckBox) view;
                   SpiltMealActivity activity = (SpiltMealActivity) getActivity();
                   activity.updateEntry(user, cb.isChecked());
                }
            });
            // Return the completed view to render on screen
            return convertView;
        }


    }


}
