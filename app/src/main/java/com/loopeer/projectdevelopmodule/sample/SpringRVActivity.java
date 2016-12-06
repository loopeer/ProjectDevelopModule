package com.loopeer.projectdevelopmodule.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopeer.projectdevelopmodule.R;
import com.loopeer.springviewgroup.SpringRecyclerView;


public class SpringRVActivity extends AppCompatActivity {

    private SpringRecyclerView mSpringRecyclerView;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_rv);
        mSpringRecyclerView = (SpringRecyclerView) findViewById(R.id.recycler_view);
        initView();
    }

    private void initView() {
        mSpringRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter();
        mSpringRecyclerView.setAdapter(mAdapter);
    }

    public void LessItemClick(View view) {
        mAdapter.setItemCount(3);
    }

    public void MoreItemClick(View view) {
        mAdapter.setItemCount(20);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.VH> {

        private int mCount = 20;

        public void setItemCount(int count) {
            mCount = count;
            notifyDataSetChanged();
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.list_item_spring_recyclerview, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return mCount;
        }

        class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
            }

            public void bind(final int position) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Click:" + position + "!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
