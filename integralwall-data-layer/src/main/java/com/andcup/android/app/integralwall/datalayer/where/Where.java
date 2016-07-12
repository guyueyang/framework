package com.andcup.android.app.integralwall.datalayer.where;

import android.text.TextUtils;
import android.util.Log;

import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.app.integralwall.datalayer.tools.TimeUtils;
import com.andcup.android.frame.datalayer.sql.WhereProvider;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class Where {

    public static WhereProvider user(){
        return new WhereProvider(DbColumn.USER_UID, UserProvider.getInstance().getUid());
    }

    public static WhereProvider readed(int readed){
        WhereProvider where = new WhereProvider(DbColumn.USER_UID, UserProvider.getInstance().getUid());
        where.addEq(DbColumn.READED, readed);
        return where;
    }

    public static WhereProvider msgId(String noteId){
        WhereProvider where = new WhereProvider(DbColumn.USER_UID, UserProvider.getInstance().getUid());
        where.addEq(DbColumn.MSG_ID, noteId);
        return where;
    }

    public static WhereProvider taskDetail(String taskId){
        WhereProvider provider = new WhereProvider(DbColumn.USER_UID, UserProvider.getInstance().getUid());
        provider.addEq(DbColumn.TASK_ID, taskId);
        return provider;
    }

    public static WhereProvider taskBusiness(String taskId, String optionId){
        WhereProvider provider = new WhereProvider(DbColumn.USER_UID, UserProvider.getInstance().getUid());
        provider.addEq(DbColumn.TASK_ID, taskId);
        if(!TextUtils.isEmpty(optionId)){
            provider.addEq(DbColumn.OPTION_ID, optionId);
        }
        provider.addEq(DbColumn.DATE, TimeUtils.getCurrentDay());
        return provider;
    }

    public static WhereProvider ScoreDetail(int type){
        WhereProvider provider = new WhereProvider(DbColumn.USER_UID, UserProvider.getInstance().getUid());
        provider.addEq(DbColumn.TYPE, type);
        return provider;
    }

    public static WhereProvider signInDate(String uid, String yearMonth) {
        WhereProvider provider = new WhereProvider(DbColumn.USER_UID, UserProvider.getInstance().getUid());
        provider.addEq(DbColumn.YEAR_MONTH, yearMonth);
        return provider;
    }

    public static WhereProvider cashingItem(int type) {
        return new WhereProvider(DbColumn.TYPE, type);
    }

    public static WhereProvider rank(String rankType) {
        return new WhereProvider(DbColumn.RANK_TYPE, rankType);
    }

    public static WhereProvider memberLevel(String familyId, int level, String keyword) {
        WhereProvider pc = new WhereProvider(DbColumn.FAMILY_ID, familyId);
        pc.addEq(DbColumn.FAMILY_LEVEL, level);
        if(!TextUtils.isEmpty(keyword)){
            pc.addEq(DbColumn.FIND_KEYWORD, keyword);
        }
        return pc;
    }

}
