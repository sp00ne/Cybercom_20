package com.cybercom.farzonelabs.cybercom20;

/**
 * Created by mofar1 on 2015-07-13.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScheduleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflateAndSetup(inflater, container);
    }

    private View inflateAndSetup(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        if(AnimationClass.PREV_BACKGROUND != null){
            AnimationClass.switchSectionColor(1, rootView);
        }

        //Init the color animation if there is one and assign the new PREV_BACKGROUND
        AnimationClass.PREV_BACKGROUND = rootView.getBackground();
        return rootView;
    }

}