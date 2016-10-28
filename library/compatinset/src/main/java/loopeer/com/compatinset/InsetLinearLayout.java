package loopeer.com.compatinset;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class InsetLinearLayout extends LinearLayout {

    public InsetLinearLayout(Context context) {
        this(context, null);
    }

    public InsetLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InsetHelper.setupForInsets(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }
}
