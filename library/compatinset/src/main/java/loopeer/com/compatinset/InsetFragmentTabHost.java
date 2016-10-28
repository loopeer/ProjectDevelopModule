package loopeer.com.compatinset;


import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

public class InsetFragmentTabHost extends FragmentTabHost {

    public InsetFragmentTabHost(Context context) {
        this(context, null);
    }

    public InsetFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        InsetHelper.setupForInsets(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }

}
