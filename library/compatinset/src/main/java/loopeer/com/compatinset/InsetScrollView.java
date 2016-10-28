package loopeer.com.compatinset;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ScrollView;

public class InsetScrollView extends ScrollView {

    public InsetScrollView(Context context) {
        this(context, null);
    }

    public InsetScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InsetHelper.setupForInsets(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }

}
