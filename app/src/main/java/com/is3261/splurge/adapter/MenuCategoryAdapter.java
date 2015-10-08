package com.is3261.splurge.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.is3261.splurge.R;
import com.is3261.splurge.model.MenuCategory;
import com.is3261.splurge.model.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junwen29 on 10/8/2015.
 */
public class MenuCategoryAdapter extends BaseAdapter{

    private List<MenuCategory> mMenuCategories;
    private final LayoutInflater mLayoutInflater;
    private final Activity mActivity;
    private final Resources mResources;

    private final static int NUM_MENU_CATEGORIES = 5;

    public final static String PLAN_TRIP_EXPENSES = "1";
    public final static String RECORD_TRIP_EXPENSES = "2";
    public final static String LOANS = "3";
    public final static String DEBTS = "4";
    public final static String MEAL_BILL_CALCULATOR = "5";


    public MenuCategoryAdapter(Activity activity) {
        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity.getApplicationContext());
        mResources = activity.getResources();
        mMenuCategories = setupMenuCategories();
    }


    @Override
    public int getCount() {
        return mMenuCategories.size();
    }

    @Override
    public MenuCategory getItem(int position) {
        return mMenuCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMenuCategories.get(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_menu_category, parent, false);
            convertView.setTag(new MenuCategoryViewHolder((LinearLayout) convertView));
        }

        MenuCategoryViewHolder holder = (MenuCategoryViewHolder) convertView.getTag();
        MenuCategory menuCategory = getItem(position);
        Theme theme = menuCategory.getTheme();
        setMenuCategoryIcon(menuCategory, holder.icon);
        convertView.setBackgroundColor(getColor(theme.getWindowBackgroundColor()));
        holder.title.setText(menuCategory.getName());
        holder.title.setTextColor(getColor(theme.getTextPrimaryColor()));
        holder.title.setBackgroundColor(getColor(theme.getPrimaryColor()));

        return convertView;
    }

    private void setMenuCategoryIcon(MenuCategory menuCategory, ImageView icon) {
        //TODO load image icons https://www.google.com/design/icons/
        switch (menuCategory.getId()){
            case PLAN_TRIP_EXPENSES:
                icon.setImageResource(R.drawable.icon_plan_trip_expenses);
                break;
            case RECORD_TRIP_EXPENSES:
                icon.setImageResource(R.drawable.icon_record_trip_expenses);
                break;
            case LOANS:
                icon.setImageResource(R.drawable.icon_loans);
                break;
            case DEBTS:
                icon.setImageResource(R.drawable.icon_debts);
                break;
            case MEAL_BILL_CALCULATOR:
                icon.setImageResource(R.drawable.icon_meal_bills_calculator);
                break;
            default:
        }
    }

    private int getColor(@ColorRes int colorRes) {
        return mResources.getColor(colorRes);
    }

    private List<MenuCategory> setupMenuCategories(){
        List<MenuCategory> tmpMenuCategories = new ArrayList<>(NUM_MENU_CATEGORIES);

        MenuCategory menuCategory1 = new MenuCategory("Plan Trip Expenses", PLAN_TRIP_EXPENSES, Theme.valueOf("yellow"));
        MenuCategory menuCategory2 = new MenuCategory("Record Trip Expenses", RECORD_TRIP_EXPENSES, Theme.valueOf("blue"));
        MenuCategory menuCategory3 = new MenuCategory("Loan(s) to Others", LOANS, Theme.valueOf("green"));
        MenuCategory menuCategory4 = new MenuCategory("Debt(s) to Others", DEBTS, Theme.valueOf("red"));
        MenuCategory menuCategory5 = new MenuCategory("Meal Bills Calculator", MEAL_BILL_CALCULATOR, Theme.valueOf("purple"));

        tmpMenuCategories.add(menuCategory1);
        tmpMenuCategories.add(menuCategory2);
        tmpMenuCategories.add(menuCategory3);
        tmpMenuCategories.add(menuCategory4);
        tmpMenuCategories.add(menuCategory5);

        return  tmpMenuCategories;
    }
}
