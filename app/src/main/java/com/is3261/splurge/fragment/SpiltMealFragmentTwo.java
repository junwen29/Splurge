package com.is3261.splurge.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import java.util.Iterator;
import java.util.Map;

public class SpiltMealFragmentTwo extends BaseFragment {

    ListView lv;
    LoadDummyUsers load = new LoadDummyUsers();
    ArrayList<User> list = load.getUserList();
    HashMap<User, Boolean> userMap;
    HashMap<User, Boolean> selectedUserMap;
    OnNextSelectListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spilt_meal_fragment2, container, false);
        lv = (ListView) view.findViewById(R.id.listView_splitmeal_members);

        selectedUserMap = new HashMap<>();

        SpiltMealActivity activity = (SpiltMealActivity) getActivity();

        userMap = activity.getUserMap();
        MyAdapter adapter = new MyAdapter(userMap);
        lv.setAdapter(adapter);


        Button mbtn = (Button) view.findViewById(R.id.button_sm_next2);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpiltMealActivity activity = (SpiltMealActivity) getActivity();
                userMap = activity.getUserMap();
                Iterator it = userMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<User, Boolean> pair = (Map.Entry)it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    if(pair.getValue()){
                        selectedUserMap.put(pair.getKey(), pair.getValue());
                    }
                }
                activity.setUpUserItem(selectedUserMap);
                mCallback.onNextSelected_sm2();
            }
        });

        return view;
    }

    public interface OnNextSelectListener {
        public void onNextSelected_sm2();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnNextSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    public class MyAdapter extends BaseAdapter {
        private final ArrayList mData;
        Map.Entry<User, Boolean> item;

        public MyAdapter(Map<User, Boolean> map) {
            mData = new ArrayList();
            mData.addAll(map.entrySet());
            item = null;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Map.Entry<User, Boolean> getItem(int position) {
            return (Map.Entry) mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO implement you own logic with ID
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View result;

            if (convertView == null) {
                result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sm_members, parent, false);
            } else {
                result = convertView;
            }

            item = getItem(position);

            // TODO replace findViewById by ViewHolder
            ((TextView) result.findViewById(R.id.tvName)).setText(item.getKey().getUsername());
            CheckBox checkBox = (CheckBox) result.findViewById(R.id.checkbox_isJoin);
            checkBox.setChecked(item.getValue());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    SpiltMealActivity activity = (SpiltMealActivity) getActivity();
                    userMap.put(item.getKey(),item.getValue());
                    activity.updateEntry(item.getKey(), cb.isChecked());
                }
            });

            return result;
        }
    }



}
