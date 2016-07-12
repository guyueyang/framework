package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.SignedDateEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/21.
 */
public class JobSignedDateList extends IntegralWallJob<BaseListEntry<String>> {

    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM");
    public static String     YEAR_OF_MONTH = DATE_FORMAT.format(new Date());

    @Override
    public BaseListEntry<String> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time  = getTimestamp();
        String sign = md5(uid + YEAR_OF_MONTH + time + getKey());
        return apis().getSignedDateList(uid, YEAR_OF_MONTH, time, sign).execute().body();
    }

    @Override
    public void finish(BaseListEntry<String> stringBaseListEntry) throws JobException {
        super.finish(stringBaseListEntry);
        List<String> dateList = stringBaseListEntry.getBaseListData().getList();
        String userId = UserProvider.getInstance().getUid();
        List<SignedDateEntity> signInDates = new ArrayList<>();
        for(int i = 0; null != dateList && i< dateList.size(); i++){
            SignedDateEntity dates = new SignedDateEntity();
            dates.setUserId(userId);
            dates.setYearMonth(YEAR_OF_MONTH);
            dates.setDate(dateList.get(i));
            signInDates.add(dates);
        }
        SqlInsert<SignedDateEntity> signInDateDao = new SqlInsert<>(SignedDateEntity.class, Where.signInDate(userId, YEAR_OF_MONTH));
        signInDateDao.insert(signInDates, 0);
    }
}
