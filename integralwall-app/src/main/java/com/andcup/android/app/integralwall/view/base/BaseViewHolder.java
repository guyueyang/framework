package com.andcup.android.app.integralwall.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andcup.widget.utils.AutoUtils;

import butterknife.ButterKnife;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected int mPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
        // auto layout.
        AutoUtils.autoSize(itemView);
        // bind view.
        ButterKnife.bind(this, itemView);
        // set onclick
        itemView.setOnClickListener(this);
    }

    public void onBindView(int position){
        // save position
        mPosition = position;
    }

    public <T> T findViewById(int id){
        return (T) itemView.findViewById(id);
    }

    @Override
    public void onClick(View view) {

    }
}
