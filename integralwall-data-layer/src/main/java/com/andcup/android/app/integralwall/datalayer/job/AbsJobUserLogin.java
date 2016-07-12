package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/14.
 */
public abstract class AbsJobUserLogin extends IntegralWallJob<BaseEntity<LoginEntity>> {

    @Override
    public final BaseEntity<LoginEntity> start() throws IOException {
        return login();
    }

    @Override
    public void  finish(BaseEntity<LoginEntity> login) throws JobException {
        SqlInsert<LoginEntity> dataOperator = new SqlInsert<>(LoginEntity.class);
        UserProvider.getInstance().login(login.body());
        dataOperator.insert(login.body());
    }

    public abstract BaseEntity<LoginEntity> login() throws IOException;
}
