package com.is3261.splurge.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.is3261.splurge.R;

/**
 * Created by junwen29 on 10/8/2015.
 */
public class MenuCategoryViewHolder {

    protected TextView title;
    protected ImageView icon;

    public MenuCategoryViewHolder(LinearLayout container) {
        icon = (ImageView) container.findViewById(R.id.menu_icon);
        title = (TextView) container.findViewById(R.id.menu_title);
    }
}
