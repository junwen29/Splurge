package com.is3261.splurge.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.activity.SpiltMealActivity;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.model.User;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SplitMealFragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SplitMealFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplitMealFragment3 extends BaseFragment {


    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_split_meal_fragment3, container, false);
        SpiltMealActivity activity = (SpiltMealActivity) getActivity();

        lv = (ListView) view.findViewById(R.id.listView_sm_selected_members);

        System.out.println("Fragment3:" + activity.getUserExpense());

        MyAdapter adapter = new MyAdapter(activity.getUserExpense());
        lv.setAdapter(adapter);
        return view;
       }


    public class MyAdapter extends BaseAdapter {
        private final ArrayList mData;

        public MyAdapter(Map<User, Float> map) {
            mData = new ArrayList();
            mData.addAll(map.entrySet());
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Map.Entry<User, Float> getItem(int position) {
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
                result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sm_member_expense_detail, parent, false);
            } else {
                result = convertView;
            }

            Map.Entry<User, Float> item = getItem(position);
            SpiltMealActivity activity = (SpiltMealActivity) getActivity();

            // TODO replace findViewById by ViewHolder
            ((TextView) result.findViewById(R.id.tvName)).setText(item.getKey().getUsername());
            ((TextView) result.findViewById(R.id.tvCurrency)).setText(activity.getCurrency());
            ((TextView) result.findViewById(R.id.tvExpense)).setText(item.getValue().toString());

            return result;
        }
    }




}
