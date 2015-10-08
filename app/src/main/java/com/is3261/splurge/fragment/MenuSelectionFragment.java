package com.is3261.splurge.fragment;


import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.is3261.splurge.R;
import com.is3261.splurge.activity.DebtActivity;
import com.is3261.splurge.activity.LoanActivity;
import com.is3261.splurge.activity.MealBillsActivity;
import com.is3261.splurge.activity.PlanTripActivity;
import com.is3261.splurge.adapter.MenuCategoryAdapter;
import com.is3261.splurge.db.RecordTripActivity;
import com.is3261.splurge.helper.TransitionHelper;
import com.is3261.splurge.model.MenuCategory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuSelectionFragment extends Fragment {

    private MenuCategoryAdapter mMenuAdapter;

    public static MenuSelectionFragment newInstance() {
        return new MenuSelectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupMenuGrid((GridView) view.findViewById(R.id.menus));
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupMenuGrid(GridView menuView) {
        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = getActivity();
                startOtherActivityWithTransition(activity, view.findViewById(R.id.menu_title),
                        mMenuAdapter.getItem(position));
            }
        });
        mMenuAdapter = new MenuCategoryAdapter(getActivity());
        menuView.setAdapter(mMenuAdapter);
    }

    private void startOtherActivityWithTransition(Activity activity , View toolbar, MenuCategory menuCategory){
        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
        ActivityOptions sceneTransitionAnimation = ActivityOptions
                .makeSceneTransitionAnimation(activity, pairs);

        // Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();

        switch (menuCategory.getId()){
            case MenuCategoryAdapter.PLAN_TRIP_EXPENSES:
                activity.startActivity(PlanTripActivity.getStartIntent(activity, menuCategory), transitionBundle);
                break;
            case MenuCategoryAdapter.RECORD_TRIP_EXPENSES:
                activity.startActivity(RecordTripActivity.getStartIntent(activity, menuCategory), transitionBundle);
                break;
            case MenuCategoryAdapter.LOANS:
                activity.startActivity(LoanActivity.getStartIntent(activity, menuCategory), transitionBundle);
                break;
            case MenuCategoryAdapter.DEBTS:
                activity.startActivity(DebtActivity.getStartIntent(activity, menuCategory), transitionBundle);
                break;
            case MenuCategoryAdapter.MEAL_BILL_CALCULATOR:
                activity.startActivity(MealBillsActivity.getStartIntent(activity, menuCategory), transitionBundle);
                break;
        }
    }
}
