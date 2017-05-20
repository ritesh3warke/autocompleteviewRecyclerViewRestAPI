package com.riteshwarke.dawaibox.Helpers;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Ritesh Warke on 20/05/17.
 */

public class AnimationUtil {

    public static void animate(RecyclerView.ViewHolder holder, Boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?200:-200,0);
        animatorTranslateY.setDuration(500);
        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();
    }

}
