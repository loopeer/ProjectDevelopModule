package loopeer.com.compatinset;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

public class InsetViewAnimator extends ViewAnimator {
    public InsetViewAnimator(Context context) {
        this(context, null);
    }

    public InsetViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        InsetHelper.setupForInsets(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }
}
