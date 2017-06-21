package com.loopeer.developutils;


import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.StringRes;
import android.widget.EditText;
import android.widget.TextView;

public class CaptchaHelper {
    private CountDownTimer mCountDownTimer;
    private Context mContext;
    private TextView mCaptchaTextView;
    private EditText mInputEditText;

    CaptchaHelper(TextView textView, EditText editText) {
        mCaptchaTextView = textView;
        mInputEditText = editText;
        mContext = mCaptchaTextView.getContext();
    }

    void apply(final Params params) {
        mCountDownTimer = new CountDownTimer(params.timeSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCaptchaTextView.setText(
                        mContext.getString(params.timeRemainStringRes, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mCaptchaTextView.setEnabled(mInputEditText.getText().toString().trim().length() == 11);
                mCaptchaTextView.setText(mContext.getString(params.sendStringRes));
            }
        };
    }

    public void start() {
        mCountDownTimer.start();
        mCaptchaTextView.setEnabled(false);
    }

    public void cancel() {
        mCountDownTimer.cancel();
    }

    public static class Builder {

        Params mParams;

        public Builder(TextView textView, EditText editText) {
            mParams = new Params();
            mParams.view = textView;
            mParams.inputView = editText;
        }

        public Builder setSendText(@StringRes int text) {
            mParams.sendStringRes = text;
            return this;
        }

        public Builder setTimeRemainText(@StringRes int text) {
            mParams.timeRemainStringRes = text;
            return this;
        }

        public Builder setTimeFuture(int timeSeconds) {
            mParams.timeSeconds = timeSeconds;
            return this;
        }

        public CaptchaHelper build() {
            CaptchaHelper captchaHelper = new CaptchaHelper(mParams.view, mParams.inputView);
            if (mParams.sendStringRes == 0)
                mParams.sendStringRes = R.string.developutils_phone_captcha_send;
            if (mParams.timeRemainStringRes == 0)
                mParams.timeRemainStringRes = R.string.developutils_phone_captcha_time_remain;
            if (mParams.timeSeconds == 0) {
                mParams.timeSeconds = 60;
            }
            captchaHelper.apply(mParams);
            return captchaHelper;
        }
    }

    static class Params {
        TextView view;
        EditText inputView;
        int sendStringRes;
        int timeRemainStringRes;
        int timeSeconds;
    }
}
