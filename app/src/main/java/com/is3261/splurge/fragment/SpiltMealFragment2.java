package com.is3261.splurge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.SpiltMealActivity;
import com.is3261.splurge.dummy.LoadDummyUsers;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.User;

import java.util.ArrayList;

public class SpiltMealFragment2 extends BaseFragment {

    ListView lv;
    LoadDummyUsers load = new LoadDummyUsers();
    ArrayList<User> list = load.getUserList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spilt_meal_fragment2, container, false);

        lv = (ListView) view.findViewById(R.id.listView_splitmeal_members);


        UsersAdapter adapter = new UsersAdapter(getActivity(), list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpiltMealActivity activity = (SpiltMealActivity) getActivity();
                activity.setUser(list.get(position));
                ViewPager vp = (ViewPager) getActivity().findViewById(R.id.viewpager_spiltmeal);
                Toast.makeText(getActivity(), "Items " + list.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                vp.setCurrentItem(2);


            }

        });

        return view;
    }

    public void updateSelectedUser(User user){
        SpiltMealActivity activity = (SpiltMealActivity) getActivity();
        activity.setUser(user);

    }

    public class UsersAdapter extends ArrayAdapter<User> {
        public UsersAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            User user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sm_members, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

            // Populate the data into the template view using the data object
            tvName.setText(user.getUsername());
            // Return the completed view to render on screen
            return convertView;
        }
    }


}
