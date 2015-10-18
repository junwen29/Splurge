package com.is3261.splurge.fragment.base;

import android.support.v4.app.Fragment;

import com.is3261.splurge.activity.base.BaseActivity;
import com.is3261.splurge.api.SplurgeApi;

/**
 * Created by junwen29 on 10/16/2015.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((BaseActivity) getActivity()).getSplurgeApi().cancel(this);
    }

    public SplurgeApi getSplurgeApi() {
        return ((BaseActivity) getActivity()).getSplurgeApi();
    }

}
