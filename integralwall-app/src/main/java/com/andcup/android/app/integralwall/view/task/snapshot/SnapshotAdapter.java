package com.andcup.android.app.integralwall.view.task.snapshot;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.andcup.android.app.integralwall.event.AddSnapshotEvent;
import com.andcup.android.app.integralwall.view.base.BaseListAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/13.
 */
public class SnapshotAdapter extends BaseListAdapter {

    List<String> mImageList = new ArrayList<>();
    final int MAX = 10;

    public SnapshotAdapter(Context context) {
        super(context);
    }

    public void addSnapshot(int position, String filepath){
        if(position >= mImageList.size()){
            mImageList.add(filepath);
        }else{
            mImageList.set(position, filepath);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.grid_item_snapshot;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        int size = mImageList.size() + 1;
        if(size >= MAX){
            return mImageList.size();
        }
        return size;
    }

    public List<String> getImageList() {
        return mImageList;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public class ViewHolder extends BaseViewHolder{

        @Bind(R.id.iv_snapshot)
        SnapshotImageView mIvSnapshot;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            if(position == mImageList.size()){
                mIvSnapshot.addSnapshot();
            }else{
                mIvSnapshot.setImageURI(Uri.parse("file://" + mImageList.get(position)));
            }
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            EventBus.getDefault().post(new AddSnapshotEvent(mPosition));
        }
    }
}
