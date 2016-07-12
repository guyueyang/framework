package com.andcup.android.app.integralwall.view.start.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobOtherLogin;
import com.andcup.android.app.integralwall.datalayer.job.JobUserLogin;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.third.sdk.UMeng;
import com.andcup.android.app.integralwall.third.sdk.listener.SimpleAuthListener;
import com.andcup.android.app.integralwall.third.sdk.listener.SimpleDataListener;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.job.Schedule;
import com.andcup.android.integralwall.commons.tools.AuthParser;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.dd.processbutton.iml.ActionProcessButton;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.utils.OauthHelper;
import com.yl.android.cpa.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/14.
 */
@Deprecated
public class LoginFragment extends BaseFragment {

    @Bind(R.id.et_content)
    EditText mEtPhone;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    ActionProcessButton mBtnLogin;
    @Bind(R.id.btn_password_eye)
    ImageButton mBtnPasswordEye;
    AbsAuthEntry mAuthEntry;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mBtnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        mEtPhone.setText(Sharef.getPhone());
    }

    @OnClick(R.id.btn_password_eye)
    public void eyePassword(View view){
        int inputType = mEtPassword.getInputType();
        mEtPassword.setInputType(inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ?
                131201 : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        Editable text = mEtPassword.getText();
        mEtPassword.setSelection(text.length());
        if(inputType==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            mBtnPasswordEye.setImageResource(R.drawable.ic_eye_password_gone);
        }else {
            mBtnPasswordEye.setImageResource(R.drawable.ic_eye_password_visible);
        }
    }

    @OnClick(R.id.btn_find_password)
    public void findPassword(View view){
        go2(FindPasswordFragment.class, null);
    }

    @OnClick(R.id.btn_login)
    public void doLogin(View view){
        String username = mEtPhone.getText().toString();
        String password = mEtPassword.getText().toString();
        boolean ok = true;
        if(TextUtils.isEmpty(username) ){
            mEtPhone.setError(mEtPhone.getHint());
            ok = false;
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError(mEtPassword.getHint());
            ok = false;
        }
        if (!ok) {
            return;
        }
        mBtnLogin.setProgress(10);
        mBtnLogin.setEnabled(false);
        call(new JobUserLogin(username, password), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                go(NavigatorActivity.class);
                Sharef.setPhone(username);
            }

            @Override
            public void onError(Throwable e) {
                mBtnLogin.setEnabled(true);
                mBtnLogin.setProgress(0);
                SnackBar.make(getActivity(), e.getMessage()).show();
            }
        });
    }

    @OnClick(R.id.tv_twitter)
    public void twitter(final View view){
        auth(SHARE_MEDIA.SINA);
        viewDisable(view);
    }

    @OnClick(R.id.tv_we_chat)
    public void weChat(final View view){
        if(isWeixinAvilible(getContext())){
            auth(SHARE_MEDIA.WEIXIN);
            viewDisable(view);
        }else {
            SnackBar.make(getContext(),getString(R.string.weixin_no)).show();
        }

    }

    private void viewDisable(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    view.setEnabled(true);
                } catch (Exception e) {

                }
            }
        }, 1000);
    }

    private void auth(SHARE_MEDIA share_media){
//        if(OauthHelper.isAuthenticated(getActivity(), share_media)){
//            getAuthInfo(share_media);
//            return;
//        }
        UMeng.doAuth(getActivity(), share_media, new SimpleAuthListener() {
            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                super.onComplete(bundle, share_media);
                String uid = bundle.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    getAuthInfo(share_media);
                } else {
//                    hideLoading();
                    OauthHelper.remove(getActivity(), SHARE_MEDIA.SINA);
                    OauthHelper.remove(getActivity(), SHARE_MEDIA.WEIXIN);
                    mBtnLogin.setProgress(0);
                    mBtnLogin.setEnabled(true);
                    SnackBar.make(getActivity(), getString(R.string.auth_failed)).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
//                hideLoading();
                mBtnLogin.setProgress(0);
                mBtnLogin.setEnabled(true);
                SnackBar.make(getActivity(), e.getMessage()).show();
            }
        });
    }

    private void getAuthInfo(final SHARE_MEDIA shareMedia){
//        showLoading(getString(R.string.login_now));
        UMeng.getPlatformInfo(getActivity(), shareMedia, new SimpleDataListener() {
            @Override
            public void onComplete(int i, Map<String, Object> map) {
                super.onComplete(i, map);
                if (reLogin(map, shareMedia)) {
                    return;
                }
                switch (shareMedia) {
                    case SINA:
                        mAuthEntry = AuthParser.parseFromTwitter(map);
                        break;
                    case WEIXIN:
                        mAuthEntry = AuthParser.parseFromWeChat(map);
                        break;
                }
                try{
                    mAuthEntry.setAccessToken(OauthHelper.getAccessToken(getActivity(), shareMedia)[0]);
                }catch (Exception e){

                }
                doLogin();
            }
        });
    }

    private boolean reLogin(Map<String, Object> map, SHARE_MEDIA shareMedia){
        if( null == map || map.size() <= 0){
            if(OauthHelper.isAuthenticated(getActivity(), shareMedia)){
                OauthHelper.setUsid(getActivity(), shareMedia, null);
                auth(shareMedia);
                return true;
            }
        }
        return false;
    }

    private void doLogin() {
        mBtnLogin.setProgress(10);
        mBtnLogin.setEnabled(false);
        call(new JobOtherLogin(mAuthEntry), new SimpleCallback<BaseEntity<LoginEntity>>() {

            @Override
            public void onError(Throwable e) {
                mBtnLogin.setProgress(0);
                mBtnLogin.setEnabled(true);
                SnackBar.make(getActivity(),e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                if(loginEntityBaseEntity.getErrCode()==1){
                    GetUserInfo();
                }else if(loginEntityBaseEntity.getErrCode()==2){
                    mBtnLogin.setProgress(0);
                    mBtnLogin.setEnabled(true);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BundleKey.ABSAUTHENTRY,mAuthEntry);
                    go2(BindAccountFragment.class, bundle);
                }
            }
        });
    }

    public void GetUserInfo() {
        call(new JobGetUserInfo(), new SimpleCallback<BaseEntity<JobGetUserInfo>>() {
            @Override
            public void onError(Throwable e) {
                mBtnLogin.setProgress(0);
                mBtnLogin.setEnabled(true);
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<JobGetUserInfo> jobGetUserInfoBaseEntity) {
                mBtnLogin.setProgress(0);
                mBtnLogin.setEnabled(true);
                go(NavigatorActivity.class);
            }
        });
    }

    public boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(getString(R.string.weixin_package))) {
                    return true;
                }
            }
        }
        return false;
    }

}
