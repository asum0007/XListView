package com.asum.xlistview.listview.adapter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;

/**
 * @Author XJW
 * @CreateTime 2016/10/13
 */
public class XAnimationAdapter extends AnimationAdapter {
    private Animator[] animators;
    private XAnimAdapterCallBack callBack;

    public XAnimationAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, XAnimAdapterCallBack callBack) {
        super(adapter);
        this.callBack = callBack;

        if (callBack == null) {
            setFirstOnly(false);
        } else {
            setFirstOnly(callBack.firstOnly());
        }
    }

    protected Animator[] getAnimators(View view) {
        animators = null;
        initializeAnim(view);

        return animators;
    }

    private void initializeAnim(View view) {
        if (callBack == null) {
            animators = new Animator[0];
            return;
        }

        Animator[] animators1 = callBack.fromAnimators(view);
        Animator[] animators2 = null;

        int[] animResId = callBack.fromXmls(view);
        if (animResId != null) {
            animators2 = new Animator[animResId.length];
            for (int i = 0; i < animators2.length; i++) {
                animators2[i] = AnimatorInflater.loadAnimator(view.getContext(), animResId[i]);
            }
        }

        int length = (animators1 == null ? 0 : animators1.length) + (animators2 == null ? 0 : animators2.length);
        animators = new Animator[length];

        int nowIndex = 0;
        if (animators1 != null) {
            for (int i = 0; i < animators1.length; i++) {
                animators[nowIndex] = animators1[i];
                nowIndex++;
            }
        }
        if (animators2 != null) {
            for (int i = 0; i < animators2.length; i++) {
                animators[nowIndex] = animators2[i];
                nowIndex++;
            }
        }
    }

    public interface XAnimAdapterCallBack {
        int[] fromXmls(View view);

        Animator[] fromAnimators(View view);

        boolean firstOnly();
    }
}
