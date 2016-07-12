package com.andcup.android.app.integralwall.datalayer;

import android.content.Context;
import android.util.Log;

import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.query.Update;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlDelete;
import com.andcup.android.frame.datalayer.sql.action.SqlSelect;

import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/10.
 */
public class UserProvider {

    private static UserProvider INST;

    private LoginEntity     mLoginInfo;
    private UserInfoEntity  mUserInfo;
    private Context         mContext;
    private final String EMPTY = "";

    public static void init(Context context){
        if( null == INST){
            INST = new UserProvider(context);
        }
    }

    private UserProvider(Context context){
        mContext = context;
        record();
    }

    public static UserProvider getInstance(){
        return INST;
    }

    public void login(LoginEntity loginEntity){
        mLoginInfo = loginEntity;
    }

    public void setUserInfo(UserInfoEntity userInfoEntity){
        mUserInfo = userInfoEntity;
    }

    public void clearCache(){
        mUserInfo  = null;
        mLoginInfo = null;
        SqlDelete<LoginEntity> dataOperator = new SqlDelete<>(LoginEntity.class);
        dataOperator.delete();
        SqlDelete<UserInfoEntity> userInfo = new SqlDelete<>(UserInfoEntity.class);
        userInfo.delete();
    }

    private void record(){
        try{
            //获取登录信息
            SqlSelect<LoginEntity> dataOperator = new SqlSelect<>(LoginEntity.class);
            List<LoginEntity> login = dataOperator.select(1);
            if( null != login && login.size() > 0){
                mLoginInfo = login.get(0);
            }
            //获取用户信息
            SqlSelect<UserInfoEntity> userInfo = new SqlSelect<>(UserInfoEntity.class);
            List<UserInfoEntity> userInfos = userInfo.select(1);
            if( null != userInfos && userInfos.size() > 0){
                mUserInfo = userInfos.get(0);
            }
        }catch (Exception e){

        }
    }

    @Deprecated
    public  void updateUserInfo(UserInfoEntity userInfoEntity) {
        new Update(UserInfoEntity.class).set(
                DbColumn.USER_SEX + "=?," +
                        DbColumn.USER_NICK_NAME + "=?," +
                        DbColumn.USER_AVATAR + "=?," +
                        DbColumn.USER_NEW_BIE + "=?," +
                        DbColumn.USER_SEX + "=?",
                userInfoEntity.getmSex(),
                userInfoEntity.getNickName(),
                userInfoEntity.getAvatar(),
                userInfoEntity.getFirstLandingStep())
                .execute();
    }

    public UserInfoEntity getUserInfo() {
        return mUserInfo;
    }

    public String getInviteUid(){
        try{
            return mUserInfo.getInviteUid();
        }catch (Exception e){
            return "0";
        }
    }

    public String getUid(){
        try{
            return mLoginInfo.getUid();
        }catch (Exception e){
            return "";
        }
    }

    public String getNickName(){
        try{
            return mUserInfo.getNickName();
        }catch (Exception e){
            return EMPTY;
        }
    }

    public void setNickName(String nickName){
        mUserInfo.setmNickName(nickName);
    }

    public String getMobile(){
        if( null != mUserInfo){
            return mUserInfo.getMobile();
        }
        return EMPTY;
    }

    public String getAccessToken(){
        if( null != mLoginInfo){
            return mLoginInfo.getAccessToken();
        }
        return EMPTY;
    }
    public boolean isFirstLandingFinish(){
        if(null != mUserInfo){
            return mUserInfo.isFirstLandingFinish();
        }
        return false;
    }
    public int getFirstLandingStep(){
        if( null !=mUserInfo){
            return mUserInfo.getFirstLandingStep();
        }
        return 0;
    }

    public boolean isLogin(){
        return null != mLoginInfo && null != mUserInfo;
    }

    public LoginEntity getLoginInfo() {
        return mLoginInfo;
    }

    public static class User{
        public static void update(UserInfoEntity userInfoEntity){
            new Update(UserInfoEntity.class).set(
                    DbColumn.USER_SEX + "=?," +
                            DbColumn.USER_NICK_NAME + "=?," +
                            DbColumn.USER_AVATAR + "=?," +
                            DbColumn.USER_NEW_BIE + "=?," +
                            DbColumn.USER_SEX + "=?",
                    userInfoEntity.getmSex(),
                    userInfoEntity.getNickName(),
                    userInfoEntity.getAvatar(),
                    userInfoEntity.getFirstLandingStep())
                    .execute();
//            new Update(UserInfoEntity.class)
//                    .set(DbColumn.USER_AVATAR + "=?", userInfoEntity.getAvatar())
//                    .execute();
        }
    }
}
