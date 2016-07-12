package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.AvatarEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/24.
 */
public class JobUpload extends IntegralWallJob<BaseEntity<AvatarEntity>> {

    public  JobUpload(){}

    @Override
    public BaseEntity<AvatarEntity> start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        Long   time=getTimestamp();
        String sgin=md5(uid+time+getKey());
        return null;
    }
}
