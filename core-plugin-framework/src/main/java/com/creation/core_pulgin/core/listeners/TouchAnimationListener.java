package com.creation.core_pulgin.core.listeners;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;

public abstract class TouchAnimationListener implements View.OnTouchListener {

    private boolean handleFrequentTouch;

    public TouchAnimationListener() {
        this.handleFrequentTouch = true;
    }

    public TouchAnimationListener(boolean handleFrequentTouch) {
        this.handleFrequentTouch = handleFrequentTouch;
    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                scaleDown(v).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        disableFrequentTouch(v);
                        scaleUp(v);
                    }
                });
                break;
            default:
                break;
        }
        return true;
    }

    private void disableFrequentTouch(final View view) {
        if (!handleFrequentTouch) {
            return;
        }
        view.setEnabled(false);
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
            }
        }, 750);
    }

    private ViewPropertyAnimator scaleDown(View view) {
        return view.animate().scaleX(0.95f).scaleY(0.95f).setDuration(50);
    }

    private ViewPropertyAnimator scaleUp(final View view) {
        return view.animate().scaleX(1f).scaleY(1f).setDuration(50).withEndAction(new Runnable() {
            @Override
            public void run() {
                onClick();
            }
        });
    }

    public abstract void onClick();
}