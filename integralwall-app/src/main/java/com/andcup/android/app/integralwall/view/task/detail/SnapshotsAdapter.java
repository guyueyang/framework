package com.andcup.android.app.integralwall.view.task.detail;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.integralwall.commons.widget.SmartImageView;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class SnapshotsAdapter extends BaseAdapter {

    List<String> mSnapshots;

    public SnapshotsAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<String> snaphots){
        mSnapshots = snaphots;
        if(!Sharef.isFlowEnabled(true)){
            mSnapshots = null;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_snapshots;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return null == mSnapshots? 0 : mSnapshots.size();
    }

    class ViewHolder extends BaseViewHolder{

        @Bind(R.id.giv_snapshot)
        SmartImageView mGivSnapshot;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            mGivSnapshot.setImageURI(Uri.parse(mSnapshots.get(position)));
        }
    }
}
