package com.andcup.android.app.integralwall.view.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    protected Context mContext;
    protected LayoutInflater  mInflater;

    public BaseAdapter(Context context){
        mContext  = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false);
        return createViewHolder(convertView, viewType);
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(position);
    }

    public abstract int getLayoutId(int viewType);
    public abstract BaseViewHolder   createViewHolder(View itemView, int viewType);
}
