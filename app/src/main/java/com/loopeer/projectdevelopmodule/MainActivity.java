package com.loopeer.projectdevelopmodule;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopeer.appupdate.AppUpdate;
import com.loopeer.compatinset.statusbar.StatusBarFontHelper;

import java.util.List;

/**
 * Routing Activity for other samples
 */
public class MainActivity extends AppCompatActivity {

    private static final String CATEGORY_SAMPLE = "com.loopeer.projectdevelopmodule.sample.SAMPLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ResolveInfoAdapter(this, getAllSampleActivities()));
        list.addItemDecoration(new DividerItemDecoration(new ColorDrawable(0xFFDCDCDC)));


        AppUpdate.apply(this
                , "message"
                , "描述"
                , "https://pro-app-qn.fir.im/aebe6a0c49d3cc9bec50e2f44559e7d2d2b56566.apk?attname=app-loopeer-release.apk_2.1.1.apk&e=1498097801&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:NYbyH9LFuJAArm3rnIn6l79KS5Q="
                , getString(R.string.app_name)
                , R.mipmap.ic_launcher);
    }

    private List<ResolveInfo> getAllSampleActivities() {
        Intent filter = new Intent();
        filter.setAction(Intent.ACTION_RUN);
        filter.addCategory(CATEGORY_SAMPLE);
        return getPackageManager().queryIntentActivities(filter, 0);
    }

    private void onRouteClicked(ResolveInfo route) {
        ActivityInfo activity = route.activityInfo;
        ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
        startActivity(new Intent(Intent.ACTION_VIEW).setComponent(name));
    }

    class ResolveInfoAdapter extends RecyclerView.Adapter<ResolveInfoAdapter.ResolveInfoViewHolder> {

        private final PackageManager pm;
        private final LayoutInflater inflater;
        private final List<ResolveInfo> samples;

        private ResolveInfoAdapter(Context context, List<ResolveInfo> resolveInfos) {
            this.samples = resolveInfos;
            this.inflater = LayoutInflater.from(context);
            this.pm = context.getPackageManager();
        }

        @Override
        public ResolveInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_route, viewGroup, false);
            return new ResolveInfoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ResolveInfoViewHolder viewHolder, int i) {
            ResolveInfo item = samples.get(i);
            viewHolder.textView.setText(item.loadLabel(pm));
        }

        @Override
        public int getItemCount() {
            return samples.size();
        }

        class ResolveInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public final TextView textView;

            public ResolveInfoViewHolder(View view) {
                super(view);
                this.textView = (TextView) view.findViewById(android.R.id.text1);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(@NonNull View v) {
                onRouteClicked(samples.get(getAdapterPosition()));
            }
        }
    }

}
