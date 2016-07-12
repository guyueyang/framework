package com.andcup.android.app.integralwall.view.usercenter.edit;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobEditBirthday;
import com.andcup.android.app.integralwall.datalayer.job.JobEditNickName;
import com.andcup.android.app.integralwall.datalayer.job.JobEditSex;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.model.AvatarEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.event.BindPhoneEvent;
import com.andcup.android.app.integralwall.event.DatePickEvent;
import com.andcup.android.app.integralwall.event.SexEvent;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.job.Schedule;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/24.
 */
public class EditFragment extends BaseFragment {

    public static int TYPE = 0X30;

    @Bind(R.id.riv_avatar)
    URIRoundedImageView mCpAvatar;
    @Bind(R.id.tv_birthday)
    TextView mTvBirthday;
    @Bind(R.id.tv_sex)
    TextView    mTvSex;
    @Bind(R.id.tv_phone)
    TextView    mTvPhone;
    @Bind(R.id.ll_password)
    LinearLayout mLlPassword;
    @Bind(R.id.et_nick_name)
    EditText mEtNickName;
    @Bind(R.id.ll_phone)
    LinearLayout mLlPhoen;

    String[]    mSexArray;

    UserInfoEntity mUserInfoEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(R.string.user_info);
        mCpAvatar.initWithAvatar();
        mEtNickName.setText(UserProvider.getInstance().getNickName());
        monitorUserInfo();
        editNickName();
    }

    private void monitorUserInfo(){
        loader(UserInfoEntity.class, new SqlLoader.CallBack<UserInfoEntity>() {
            @Override
            public void onUpdate(UserInfoEntity userInfoEntity) {
                mUserInfoEntity = UserProvider.getInstance().getUserInfo();
                mSexArray = getResources().getStringArray(R.array.array_sex);
                mCpAvatar.setEditImageURI(Uri.parse(mUserInfoEntity.getAvatar()));
                if (!TextUtils.isEmpty(mUserInfoEntity.getBirthday())) {
                    mTvBirthday.setText(mUserInfoEntity.getBirthday());
                }
                if (!TextUtils.isEmpty(getSex())) {
                    mTvSex.setText(getSex());
                }
                if (!TextUtils.isEmpty(mUserInfoEntity.getMobile())) {
                    mTvPhone.setText(mUserInfoEntity.getMobile());
                }

                if (UserProvider.getInstance().getLoginInfo().isVisitor()) {
                    String mobile = mUserInfoEntity.getMobile();
                    if (TextUtils.isEmpty(mobile)) {
                        /**开启绑定账号功能.*/
                        mLlPhoen.setVisibility(View.VISIBLE);
                    }
                }
                if (!UserProvider.getInstance().getLoginInfo().isRegister()) {
                    mLlPassword.setVisibility(View.GONE);
                }
                String mobile = mUserInfoEntity.getMobile();
                if (!TextUtils.isEmpty(mobile)) {
                    mLlPhoen.setVisibility(View.GONE);
                    mLlPassword.setVisibility(View.VISIBLE);
                } else {
                    mLlPhoen.setVisibility(View.VISIBLE);
                    mLlPassword.setVisibility(View.GONE);
                }

            }
        });
    }

    @OnClick(R.id.rl_head_portrait)
    protected void  HeadPortrait(){
        Dialog.EDIT.build(EditAvatarFragment.class,null).show(getChildFragmentManager());
    }
    @OnClick(R.id.ll_sex)
    protected void EditSex(){
        Dialog.EDIT.build(EditSexFragment.class,null).show(getChildFragmentManager());
    }
    @OnClick(R.id.ll_birthday)
    protected void EditBirthday(){
        Bundle bundle=new Bundle();
        bundle.putString(BundleKey.KEY_TYPE,String.valueOf(TYPE));
        Dialog.EDIT.build(EditDatePickerFragment.class,bundle).show(getChildFragmentManager());
    }
    @OnClick(R.id.ll_phone)
    protected void EditPhone(){
        Dialog.EDIT.build(EditBindAccountFragment.class,null).show(getChildFragmentManager());
    }
    @OnClick(R.id.ll_password)
    protected void EditPassword(){
        Dialog.EDIT.build(EditPasswordFragment.class, null).show(getChildFragmentManager());
    }

    public void editNickName(){
        mEtNickName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String text = mEtNickName.getText().toString();
                    if(TextUtils.isEmpty(text)){
                        SnackBar.make(getContext(),getString(R.string.error_nickname)).show();
                        return;
                    }
                    String regex = "^[a-z0-9A-Z\u4e00-\u9fa5—_——*]+$";
                    if (!text.matches(regex)) {
                        SnackBar.make(getContext(), getString(R.string.limits_char_number_chinese)).show();
                        return;
                    }
                    try {
                        if (text.getBytes("gb2312").length > 16) {
                            SnackBar.make(getContext(), getString(R.string.limits_nickname_length)).show();
                            return;
                        }
                    } catch (Exception e) {
                    }
                    callNickName();
                }
            }
        });
    }

    private void callNickName(){
        try {
            String text = mEtNickName.getText().toString();
            String nickname = mUserInfoEntity.getNickName();
            if (!text.equals(nickname)) {
                if (text.trim().length() > 0) {
                    mUserInfoEntity.setmNickName(text.trim());
                    //请求接口
                    UserProvider.User.update(mUserInfoEntity);
                    UserProvider.getInstance().setNickName(text.trim());
                    EventBus.getDefault().post(new UserInfoEntity());

                    call(new JobEditNickName(text.trim()), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity>());
                }
            }
        } catch (Exception e) {

        }
    }

    private String getSex(){
        return (mUserInfoEntity.getmSex() == mUserInfoEntity.SEX_NONE)?mSexArray[1]:
                (mUserInfoEntity.getmSex() == mUserInfoEntity.SEX_FEMALE ? mSexArray[0] : mSexArray[2]);
    }

    @Override
    public void onEvent(Object object){
        super.onEvent(object);
        editBirthday(object);
        editPhoneNumber(object);
        editSex(object);
        editAvatar(object);
    }

    private void editBirthday(Object object){
        if(object instanceof DatePickEvent){
            DatePickEvent pickEvent = (DatePickEvent) object;
            if(pickEvent.getType() == TYPE){
                mTvBirthday.setText(pickEvent.getDate());
                mUserInfoEntity.setBirthday(mTvBirthday.getText().toString());
                call(new JobEditBirthday(mTvBirthday.getText().toString()), new SimpleCallback<BaseEntity>());
            }
        }
    }

    private void editPhoneNumber(Object object) {
        super.onEvent(object);
        if(object instanceof BindPhoneEvent){
            mLlPhoen.setVisibility(View.GONE);
            mLlPassword.setVisibility(View.VISIBLE);
            mTvPhone.setText(UserProvider.getInstance().getUserInfo().getMobile());
        }
    }

    private void editSex(Object object){
        if(object instanceof SexEvent){
            SexEvent sexEvent = (SexEvent) object;
            mUserInfoEntity.setSex(sexEvent.getEvent());
            mTvSex.setText(getSex());
            call(new JobEditSex(sexEvent.getEvent()), new SimpleCallback<BaseEntity>() {
                @Override
                public void onError(Throwable e) {
                    SnackBar.make(getContext(), e.getMessage()).show();
                }
            });
        }
    }

    private void editAvatar(Object object) {
        super.onEvent(object);
        if(object instanceof AvatarEntity){
            mCpAvatar.setEditImageURI(Uri.parse(mUserInfoEntity.getAvatar()));
    }
        }

}
