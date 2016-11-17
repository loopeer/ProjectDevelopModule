package com.loopeer.flowlayout;

import android.content.Context;
import android.view.View;

public abstract class FlowAdapter {

    private Context mContext;
    private FlowLayout mFlowLayout;

    public FlowAdapter(FlowLayout flowLayout, Context context) {
        mContext = context;
        mFlowLayout = flowLayout;
    }

    public abstract View getItemView(int position);

    public abstract String[] getContents();

    public abstract int getItemCount();

    public Context getContext() {
        return mContext;
    }

    public abstract int getSelectedCount();

    public void notifyDataChanged() {
        mFlowLayout.updateViews();
    }

}
