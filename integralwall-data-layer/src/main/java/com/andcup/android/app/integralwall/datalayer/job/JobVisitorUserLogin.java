package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public class JobVisitorUserLogin extends AbsJobUserLogin {

    @Override
    public BaseEntity<LoginEntity> login() throws IOException {
        long time = getTimestamp();
        return apis().visitor(getUniqueId(),time,md5(getUniqueId() + time + getKey()))
                .execute()
                .body();
    }
}
