package com.loopeer.projectdevelopmodule.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.loopeer.developutils.ClickSpanHelper;
import com.loopeer.developutils.DoubleClickHelper;
import com.loopeer.projectdevelopmodule.R;
import com.loopeer.projectdevelopmodule.databinding.ActivityBaseDevelopUtilsBinding;

public class BaseDevelopUtilsActivity extends AppCompatActivity {

    ActivityBaseDevelopUtilsBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_develop_utils);

        setUpClickSpan();
        setUpDoubleClick();
    }

    private void setUpClickSpan() {
        new ClickSpanHelper.Builder(mBinding.textTestClickSpan
                , R.string.util_click_span_helper_content)
                .setHighlightColor(0)
                .setColor(R.color.colorAccent)
                .setSpanUnderLine(false)
                .setSpanBold(false)
                .addClickSpanParam(R.string.util_click_span_helper_01, widget -> {
                    Toast.makeText(widget.getContext(), "Click 01", Toast.LENGTH_SHORT).show();
                })
                .addClickSpanParam(R.string.util_click_span_helper_02, widget -> {
                    Toast.makeText(widget.getContext(), "Click 02", Toast.LENGTH_SHORT).show();
                })
                .addClickSpanParam(R.string.util_click_span_helper_03, new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(widget.getContext(), "Click 03", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setUnderlineText(true);
                        ds.setColor(ContextCompat.getColor(BaseDevelopUtilsActivity.this, R.color.colorPrimary));
                    }
                }).build();
    }

    private void setUpDoubleClick() {
        new DoubleClickHelper.Builder(mBinding.itemDoubleClick)
                .setDoubleClickListener(e -> Toast.makeText(BaseDevelopUtilsActivity.this
                        , "Double Click", Toast.LENGTH_SHORT).show())
                .setSingleClickListener(event -> Toast.makeText(BaseDevelopUtilsActivity.this
                        , "Single Click", Toast.LENGTH_SHORT).show())
                .build();
    }
}
