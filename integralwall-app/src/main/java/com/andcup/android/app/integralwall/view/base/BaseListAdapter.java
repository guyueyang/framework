package com.andcup.android.app.integralwall.view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public abstract class BaseListAdapter extends android.widget.BaseAdapter {

    protected Context mContext;
    protected LayoutInflater  mInflater;

    public BaseListAdapter(Context context){
        mContext  = context;
        mInflater = LayoutInflater.from(mContext);
    }


    public abstract int getLayoutId(int viewType);
    public abstract BaseViewHolder   createViewHolder(View itemView, int viewType);
    public abstract int getItemCount() ;

    @Override
    public final int getCount() {
        return getItemCount();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int viewType = getItemViewType(i);
        View convertView = LayoutInflater.from(mContext).inflate(getLayoutId(viewType), viewGroup, false);
        BaseViewHolder holder = createViewHolder(convertView, viewType);
        holder.onBindView(i);
        return convertView;
    }
}
