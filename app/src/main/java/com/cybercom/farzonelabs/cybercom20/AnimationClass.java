package com.cybercom.farzonelabs.cybercom20;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Keeps track of relevant animation data
 * Created by mofar1 on 2015-07-14.
 */
public class AnimationClass {
    /**
     * Create the variable that needs to be passed in for the Load screen color
     */
    public static Drawable PREV_BACKGROUND;

    /**
     * The color iterator for the different fragments
     * @param sectionNumber - Which fragment number we are switching to
     * @param rootView - the rootView object.
     */

    public static void switchSectionColor(int sectionNumber, View rootView){
        // Extract the color of the loading screen when loading is complete
        // If the animation has ended the variable is set, otherwise it's null.
        if(PREV_BACKGROUND != null){

            // Determine current background color
            int sectionColorFrom = getSectionColorFrom();

            // Determine which color we should switch to
            int sectionColorTo = getSectionColorTo(rootView, sectionNumber);

            // Animate both action bar, status bar and toolbar
            animateBackgroundStatusBarToolBar(rootView, MainActivity.mDrawerLayout, MainActivity.mToolbar, sectionColorFrom, sectionColorTo);

        }
    }

    public static void switchFABColor(View actionButtonView, int colorToId){

        FloatingActionButton fab = (FloatingActionButton) actionButtonView;
        int fabColorFrom = getFabColorFrom(fab);



    }

    private static void animateBackgroundStatusBarToolBar(View view, final DrawerLayout layout, final Toolbar toolbar, int colorFrom, int colorTo) {


        // Background -SETUP
        ObjectAnimator objAnimBackground = new ObjectAnimator().ofArgb(view.getBackground()
                , "backgroundColor", colorFrom, colorTo);
        objAnimBackground.setTarget(view);

        // Toolbar -SETUP
        ValueAnimator valAnimToolbar = ValueAnimator.ofArgb(colorFrom, colorTo);
        valAnimToolbar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
             @Override
             public void onAnimationUpdate(ValueAnimator animation) {
                toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
             }
        });
        
        // Statusbar -SETUP
        ValueAnimator valAnimStatusBar = ValueAnimator.ofArgb(colorFrom, colorTo);
        valAnimStatusBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                layout.invalidate();
                layout.setStatusBarBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });
        
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(objAnimBackground,valAnimToolbar,valAnimStatusBar);
        animatorSet.start();

        /*
        // Start animations
        objAnimBackground.setDuration(1000);
        objAnimToolbar.setDuration(1000);

        objAnimBackground.start();
        objAnimToolbar.start();
        valAnimStatusBar.start();
        */
    }

    private static int getFabColorFrom(FloatingActionButton fab){
        int colorFrom = Color.TRANSPARENT;     //set a default color in case there was an error.
        Drawable background = fab.getBackground();

        if (background instanceof ColorDrawable) {
            background.setAlpha(255);
            colorFrom = ((ColorDrawable) background).getColor();
        }
        return colorFrom;
    }

    private static int getSectionColorFrom() {
        int colorFrom = Color.TRANSPARENT;     //set a default color in case there was an error.
        Drawable background = PREV_BACKGROUND;
        if (background instanceof ColorDrawable) {
            background.setAlpha(255);
            colorFrom = ((ColorDrawable) background).getColor();
        }
        return colorFrom;
    }


    private static int getSectionColorTo(View view, int menuItem){
        int colorTo = 0;
        switch (menuItem) {
            case 1:
                colorTo = view.getResources().getColor(R.color.OrangeCybercom);
                break;
            case 2:
                colorTo = view.getResources().getColor(R.color.BlueCybercom);
                break;
            case 3:
                colorTo = view.getResources().getColor(R.color.GreenCybercom);
                break;
        }

        return colorTo;
    }
}
