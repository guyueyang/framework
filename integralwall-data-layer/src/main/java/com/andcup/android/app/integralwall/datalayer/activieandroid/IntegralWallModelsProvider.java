package com.andcup.android.app.integralwall.datalayer.activieandroid;

import com.andcup.android.database.activeandroid.content.ContentProvider;
import com.andcup.android.frame.datalayer.RepositoryFactory;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/11.
 */
public class IntegralWallModelsProvider extends ContentProvider{

    @Override
    public boolean onCreate() {
        //init database.
        RepositoryFactory.ACTIVEANDROID.with(getContext()).create(IntegralWallModels.class);
        return super.onCreate();
    }
}
