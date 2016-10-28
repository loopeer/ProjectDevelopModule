package loopeer.com.compatinset;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class InsetFramelayout extends FrameLayout {

    public InsetFramelayout(Context context) {
        this(context, null);
    }

    public InsetFramelayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetFramelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InsetHelper.setupForInsets(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }

}
