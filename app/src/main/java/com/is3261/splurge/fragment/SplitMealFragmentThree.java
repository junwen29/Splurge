package com.is3261.splurge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.is3261.splurge.R;
import com.is3261.splurge.fragment.base.BaseFragment;
import com.is3261.splurge.helper.OwnerStore;
import com.is3261.splurge.model.Owner;
import com.is3261.splurge.model.User;
import com.is3261.splurge.view.DishCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplitMealFragmentThree extends BaseFragment implements View.OnClickListener {

    public interface FragmentThreeListener {
        void onFragmentThreeNextSelected(Map<User,Float> expenseMap, ArrayList<User> spenders);
    }

    private View mView;
    private LinearLayout mContainer;
    private FloatingActionButton mFab;
    private Button mNextButton;

    private int mDishIndex = 0;
    private ArrayList<User> mSelectedFriends;
    private Owner mOwner;
    private Map<User,Float> mExpenseMap;
    private FragmentThreeListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_split_meal_fragment3, container, false);
        init(mView);

        mSelectedFriends = new ArrayList<>();
        mExpenseMap = new HashMap<>();

        OwnerStore ownerStore = new OwnerStore(getContext());
        Long id = Long.parseLong(ownerStore.getOwnerId());
        mOwner = new Owner(id);
        mOwner.setUsername(ownerStore.getUsername());

        return mView;
    }

    /**
     * find all views and initialise them
     */
    private void init(View view){
        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mNextButton = (Button) view.findViewById(R.id.button_sm_next3);

        mFab.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
    }

    private void addDishCard(){
        if (mContainer == null) return;

        mDishIndex++;
        final DishCard dishCard = new DishCard(getContext());
        dishCard.setTitle("Dish " + mDishIndex);

        final Spinner spinner = dishCard.getSpinner();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        adapter.add(mOwner);
        adapter.addAll(mSelectedFriends);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dishCard.getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainer.removeView(dishCard);

                // If there are no rows remaining, show the empty view.
                if (mContainer.getChildCount() == 0) {
                    mView.findViewById(R.id.empty).setVisibility(View.VISIBLE);
                }
            }
        });

        mContainer.addView(dishCard);
        dishCard.requestFocus();
        // Hide the "empty" view since there is now at least one item in the list.
        mView.findViewById(R.id.empty).setVisibility(View.GONE);
    }

    public void setSelectedFriends(ArrayList<User> selectedFriends){
        if (selectedFriends != null){
            mSelectedFriends = selectedFriends;
            mContainer.removeAllViews();
            mDishIndex = 0;
        }
    }

    /**
     * function for the next button
     * do validation before moving on to next page
     */

    private void mapExpeneses(){
        boolean hasEmptyFields = false;
        View focusView = null;

        int childViewCounts = mContainer.getChildCount();
        if (childViewCounts == 0){
            Snackbar.make(mContainer, "No expense." ,Snackbar.LENGTH_LONG).show();
            return;
        }

        //reset all mappings
        mExpenseMap.clear();

        for (int i = 0; i < childViewCounts; i++){
            DishCard dishCard = (DishCard) mContainer.getChildAt(i);
            EditText editText = dishCard.getEditText();
            editText.setError(null); //reset error
            String amount = editText.getEditableText().toString();

            //check for empty fields
            if (TextUtils.isEmpty(amount)){
                focusView = editText;
                editText.setError(getString(R.string.error_field_required));
                hasEmptyFields = true;
                break; // exit for loop
            }

            User spender = (User) dishCard.getSpinner().getSelectedItem();

            if (mExpenseMap.get(spender) == null){
                mExpenseMap.put(spender, Float.parseFloat(amount));
            } else {
                mExpenseMap.put(spender, mExpenseMap.get(spender) + Float.parseFloat(amount));
            }
        }

        if (hasEmptyFields){
            focusView.requestFocus();
        } else {
            //get all possible users
            ArrayList<User> allUsers = new ArrayList<>(mSelectedFriends);
            allUsers.add(mOwner);

//            ArrayList<User> spenders = new ArrayList<>();
//            //add user if he is a spender only
//            for (User user : allUsers) {
//                if (mExpenseMap.containsKey(user)){
////                    if (mExpenseMap.get(user) > 0){
//                        spenders.add(user);
////                    }
//                }
//            }
            mCallback.onFragmentThreeNextSelected(mExpenseMap, allUsers);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentThreeListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement FragmentThreeListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                addDishCard();
                break;
            case R.id.button_sm_next3:
                mapExpeneses();
                break;
        }
    }
}
