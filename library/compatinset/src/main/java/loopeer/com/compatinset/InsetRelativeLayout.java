package loopeer.com.compatinset;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class InsetRelativeLayout extends RelativeLayout {

    public InsetRelativeLayout(Context context) {
        this(context, null);
    }

    public InsetRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        InsetHelper.setupForInsets(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }
}
