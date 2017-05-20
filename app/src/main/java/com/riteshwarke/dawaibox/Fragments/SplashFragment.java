package com.riteshwarke.dawaibox.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.riteshwarke.dawaibox.Activities.MainActivity;
import com.riteshwarke.dawaibox.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

@BindView(R.id.textView4)
    TextView textView4;
    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init() {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    SearchFragment searchFragment = new SearchFragment();
                    ((MainActivity)getActivity()).loadFragment(searchFragment, null, R.id.my_container, "null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        handler.postDelayed(runnable, 3000);

        textView4.clearAnimation();
        TranslateAnimation transAnim = new TranslateAnimation(0, 0,
               -(getDisplayHeight()/2),0);
        transAnim.setStartOffset(0);
        transAnim.setDuration(2000);
        transAnim.setFillAfter(true);
        transAnim.setInterpolator(new BounceInterpolator());
        transAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView4.clearAnimation();
                final int left = textView4.getLeft();
                final int top = textView4.getTop();
                final int right = textView4.getRight();
                final int bottom = textView4.getBottom();
                textView4.layout(left, top, right, bottom);

            }
        });
        textView4.startAnimation(transAnim);

    }

    private int getDisplayHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }

}
