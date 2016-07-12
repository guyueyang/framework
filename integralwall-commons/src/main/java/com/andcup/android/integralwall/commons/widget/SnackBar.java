package com.andcup.android.integralwall.commons.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Amos on 2015/9/19.
 */
public class SnackBar {

    Context mContext;
    CharSequence mValue;

    public static SnackBar make(Context context, CharSequence value){
        SnackBar snackBar = new SnackBar();
        snackBar.mContext = context;
        snackBar.mValue   = value;
        return snackBar;
    }

    public void show(){
        if(!TextUtils.isEmpty(mValue)){
            try{
                Toast.makeText(mContext, mValue, Toast.LENGTH_LONG).show();
            }catch (Exception e){

            }
        }
    }
}
