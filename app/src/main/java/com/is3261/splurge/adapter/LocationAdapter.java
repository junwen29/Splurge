package com.is3261.splurge.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.is3261.splurge.R;
import com.is3261.splurge.adapter.base.GridViewAdapter;
import com.is3261.splurge.model.TripLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by junwen29 on 11/5/2015.
 */
public class LocationAdapter extends GridViewAdapter<TripLocation> {

    private final List<TripLocation> mUnfilteredItems;
    private Filter mFilter;

    public LocationAdapter(Context context) {
        super(new ArrayList<TripLocation>(), context);
        mUnfilteredItems = new ArrayList<>();
    }

    public void addAllUnfilteredItems(Collection<TripLocation> items) {
        mUnfilteredItems.addAll(items);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || (holder = (ViewHolder) convertView.getTag()) == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_location, parent, false);
            convertView.setTag(holder = new ViewHolder(convertView));
        }

        TripLocation location = getItem(position);
        holder.nameLabel.setText(location.name);
        holder.addressLabel.setText(location.street);

        return convertView;
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    constraint = constraint.toString().toLowerCase(Locale.US);
                    FilterResults result = new FilterResults();
                    if (TextUtils.isEmpty(constraint)) {
                        result.count = mUnfilteredItems.size();
                        result.values = mUnfilteredItems;
                    } else {
                        List<TripLocation> filteredItems = new ArrayList<>();
                        for (TripLocation i : mUnfilteredItems) {
                            if (i.name.toLowerCase(Locale.US).contains(constraint)) {
                                filteredItems.add(i);
                            }
                        }

                        result.count = filteredItems.size();
                        result.values = filteredItems;
                    }
                    return result;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results.count == getCount()) {
                        return;
                    }

                    clear();
                    addAll((List<TripLocation>) results.values);
                    notifyDataSetChanged();
                }
            };
        }

        return mFilter;
    }

    static class ViewHolder {
        TextView nameLabel;
        TextView addressLabel;

        public ViewHolder(View v) {
            nameLabel = (TextView) v.findViewById(R.id.text_name);
            addressLabel = (TextView) v.findViewById(R.id.text_address);
        }

    }
}
