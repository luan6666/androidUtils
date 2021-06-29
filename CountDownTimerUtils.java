package com.smz.lexunuser.util;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CountDownTimerUtils extends CountDownTimer {
    private WeakReference<TextView> mTextView;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = new WeakReference(textView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        //用弱引用 先判空 避免崩溃
        if (mTextView.get() == null) {
            cancle();
            return;
        }
        mTextView.get().setClickable(false); //设置不可点击
        mTextView.get().setTextColor(Color.WHITE);
        mTextView.get().setText(millisUntilFinished / 999 + ""); //设置倒计时时间
        //  mTextView.setBackgroundResource(R.drawable.bg_identify_code_press); //设置按钮为灰
//        SpannableString spannableString = new SpannableString(mTextView.get().getText().toString());
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.WHITE);
//        try {
//            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        } catch (Exception e) {
//        }
//        mTextView.get().setText(spannableString);
    }

    @Override
    public void onFinish() {
        //用软引用 先判空 避免崩溃
        if (mTextView.get() == null) {
            cancle();
            return;
        }
        mTextView.get().setText("获取");
        mTextView.get().setClickable(true);//重新获得点击
        //mTextView.setBackgroundResource(R.drawable.bg_identify_code_normal);
    }

    public void cancle() {
        if (this != null) {
            this.cancel();
        }
    }
}