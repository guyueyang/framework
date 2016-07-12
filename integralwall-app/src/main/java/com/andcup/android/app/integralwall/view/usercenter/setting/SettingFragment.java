package com.andcup.android.app.integralwall.view.usercenter.setting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.andcup.android.app.integralwall.IntegralWallApplication;
import com.andcup.android.app.integralwall.IntegralWallUpdate;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.tools.LinkViewHelper;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.dialog.LogoutFragment;
import com.andcup.android.app.integralwall.view.task.biz.load.TaskQuery;
import com.andcup.android.integralwall.commons.loading.LoadingIndicatorView;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.android.integralwall.commons.tools.CacheManager;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.andcup.android.update.Update;
import com.andcup.android.update.model.UpdateEntity;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.yl.android.cpa.R;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SettingFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener{

    @Bind(R.id.tv_cache)
    TextView mTvCacheSize;
    @Bind(R.id.tv_check_update)
    TextView mTvCheckUpdate;
    @Bind(R.id.tg_message)
    ToggleButton mTgMessage;
    @Bind(R.id.tg_picture)
    ToggleButton mTgPicture;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        setTitle(R.string.score_setting);
        setCacheSize();

        mTgPicture.setChecked(Sharef.isFlowEnabled(false));
        mTgPicture.setOnCheckedChangeListener(this);

    }

    @OnClick(R.id.tv_login_out)
    protected void  onLoginOut(){
        Dialog.LOGIN_OUT.build(LogoutFragment.class, null).show(getFragmentManager());
    }
    @OnClick(R.id.tv_question)
    protected void onQuestion(){
        LinkViewHelper.open(getActivity(),"http://www.lyzip.com/api/article/faq",getString(R.string.frequently_asked_questions));
    }

    @OnClick(R.id.tv_about_me)
    protected void onAboutMe(){
        go2(AboutFragment.class,null);
    }

    @OnClick(R.id.tv_feed_back)
    protected void onFeedBack() {
        go2(FeedBackFragment.class,null);
    }

    @OnClick(R.id.tv_check_update)
    protected void onCheckUpdate(){
        showLoading();
        SdkManager.INST.mUpdate.checkUpdate(new Update.UpdateListener() {
            @Override
            public void onComplete(UpdateEntity updateEntity) {
                if(!updateEntity.isUpgrade()){
                    SnackBar.make(getContext(), getString(R.string.update_no)).show();
                }else{
                    Update.getInstance().show(getChildFragmentManager());
                }
                hideLoading();
            }

            @Override
            public void onError(Throwable throwable) {
                hideLoading();
            }
        });
    }

    @OnClick(R.id.tv_clear_cache)
    public void clearCache(View view){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                CacheManager.cleanCustomCache(IntegralWallApplication.INST.getExternalCacheDir().getAbsolutePath());
                //CacheManager.cleanCustomCache(IntegralWallApplication.INST.getFilesDir().getAbsolutePath());
                //CacheManager.cleanFiles(IntegralWallApplication.INST.getApplicationContext());
                CacheManager.cleanCustomCache(AndroidUtils.getDiskCacheDir(getActivity()));
                // stop all tasks.
                TaskQuery.clear();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setCacheSize();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void setCacheSize(){
        try {
            long size = CacheManager.getFolderSize(IntegralWallApplication.INST.getExternalCacheDir());
            size += CacheManager.getFolderSize(new File(AndroidUtils.getDiskCacheDir(getActivity())));
            //size += CacheManager.getFolderSize(IntegralWallApplication.INST.getFilesDir());
            if(size < 1024 * 1024){
                size = 0;
            }
            String cacheSize = CacheManager.getFormatSize(size);
            mTvCacheSize.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == mTgPicture){
            Sharef.setFlowEnabled(isChecked);
        }
    }
}
