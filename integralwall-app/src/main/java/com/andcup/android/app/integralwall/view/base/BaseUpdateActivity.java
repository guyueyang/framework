package com.andcup.android.app.integralwall.view.base;

import android.os.Bundle;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.update.Update;
import com.andcup.android.update.model.UpdateEntity;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/6/21.
 */
public abstract class BaseUpdateActivity extends BaseActivity{

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        UpdateEntity entity = SdkManager.INST.mUpdate.getUpdateEntity();
        if( null == entity){
            checkUpdate();
        }else if(entity.isUpgrade()){
            if(SdkManager.INST.mUpdate.isShow()){
                SdkManager.INST.mUpdate.show(getSupportFragmentManager());
            }
        }
    }

    private void checkUpdate(){
        SdkManager.INST.mUpdate.checkUpdate(new Update.UpdateListener() {
            @Override
            public void onComplete(UpdateEntity updateEntity) {
                if(updateEntity.isUpgrade()){
                    SdkManager.INST.mUpdate.show(getSupportFragmentManager());
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }
}
