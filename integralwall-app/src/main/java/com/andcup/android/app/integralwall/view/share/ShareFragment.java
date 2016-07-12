package com.andcup.android.app.integralwall.view.share;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import com.andcup.android.app.integralwall.IntegralWallApplication;
import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobMyShareInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobShareSuccessful;
import com.andcup.android.app.integralwall.datalayer.model.ShareInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.event.ShareEvent;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class ShareFragment extends GateDialogFragment{

    @Restore(BundleKey.SHARE_CONTENT)
    ShareContent mShareContent;
    ShareInfoEntity mShareInfoEntity;
    UMSocialService mUmSocialService;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        mUmSocialService = UMServiceFactory.getUMSocialService("com.umeng.share");
        mUmSocialService.getConfig().closeToast();
        load();
        call();
//        ShareSuccess();
//        shareContent();
    }

    private void call(){
        call(new JobMyShareInfo(), new SimpleCallback<BaseEntity<ShareInfoEntity>>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(), e.getMessage()).show();
            }
        });
    }

    private void load(){
        loader(ShareInfoEntity.class, Where.user(), new SqlLoader.CallBack<ShareInfoEntity>() {
            @Override
            public void onUpdate(ShareInfoEntity shareInfoEntity) {
                mShareInfoEntity = shareInfoEntity;
            }
        });
    }


    @OnClick(R.id.btn_friend_circle)
    public void onFriendCircle(){
        if( null == mShareInfoEntity){
            return;
        }
        CircleShareContent weChatCircle = new CircleShareContent();
        weChatCircle.setTitle(mShareInfoEntity.getTitle());
        weChatCircle.setTargetUrl(mShareInfoEntity.getUrl());
        weChatCircle.setShareContent(mShareInfoEntity.getContent());
        weChatCircle.setShareImage(new UMImage(getActivity(), mShareInfoEntity.getShareImage()));
        mUmSocialService.setShareMedia(weChatCircle);
        shareTo(SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    @OnClick(R.id.btn_qzone)
    public void onQzone(){
        shareContent();
        SdkManager.configOnActivityChanged(getActivity());
        if( null == mShareInfoEntity){
            return;
        }
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setTargetUrl(mShareInfoEntity.getUrl());
        qzone.setShareContent(mShareInfoEntity.getContent());
        qzone.setShareImage(new UMImage(getActivity(), mShareInfoEntity.getShareImage()));
        qzone.setTitle(mShareInfoEntity.getTitle());
        mUmSocialService.setShareMedia(qzone);
        shareTo(SHARE_MEDIA.QZONE);
    }

    @OnClick(R.id.btn_wechat)
    public void onWeChat(){
        shareContent();
        if( null == mShareInfoEntity){
            return;
        }
        WeiXinShareContent weiXinShareContent = new WeiXinShareContent();
        weiXinShareContent.setTargetUrl(mShareInfoEntity.getUrl());
        weiXinShareContent.setShareContent(mShareInfoEntity.getContent());
        weiXinShareContent.setShareImage(new UMImage(getActivity(), mShareInfoEntity.getShareImage()));
        weiXinShareContent.setTitle(mShareInfoEntity.getTitle());
        mUmSocialService.setShareMedia(weiXinShareContent);
        shareTo(SHARE_MEDIA.WEIXIN);
    }

    @OnClick(R.id.btn_twitter)
    public void onWeiBo(){
        shareContent();
        if( null == mShareInfoEntity){
            return;
        }
        SinaShareContent sinaShareContent = new SinaShareContent();
        sinaShareContent.setShareImage(new UMImage(getActivity(), mShareInfoEntity.getShareImage()));
        sinaShareContent.setShareContent(mShareInfoEntity.getTitle() + " \r\n " + mShareInfoEntity.getContent() + "\r\n" + mShareInfoEntity.getUrl());
        mUmSocialService.setShareMedia(sinaShareContent);
        shareTo(SHARE_MEDIA.SINA);
    }

    private void shareTo(SHARE_MEDIA media){
        try {
            mUmSocialService.postShare(getActivity(), media,
                    new SocializeListeners.SnsPostListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                            if (eCode == 200) {
                                if (mShareContent != null) {
                                    if(!TextUtils.isEmpty(mShareContent.mNewbieGuide)) {
                                        if (mShareContent.mNewbieGuide.equals("1")) {
                                            ShareSuccess();
                                        }
                                    }
                                }
                                SnackBar.make(IntegralWallApplication.INST, getString(R.string.share_success)).show();
                            }
//                            else {
//                                SnackBar.make(IntegralWallApplication.INST, getString(R.string.share_failed)).show();
//                            }
                            mUmSocialService.getConfig().cleanListeners();
                        }
                    });
        }catch(Exception e){

        }
    }

    protected void ShareSuccess(){
        call(new JobShareSuccessful(), new SimpleCallback<BaseEntity>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity baseEntity) {
                EventBus.getDefault().post(new ShareEvent(0));
            }
        });
    }

    protected void shareContent(){
        if(mShareContent!=null){
            if(!TextUtils.isEmpty(mShareContent.mShareContent)){
                mShareInfoEntity.setContent(mShareContent.mShareContent);
            }
            if(!TextUtils.isEmpty(mShareContent.mShareUrl)){
                mShareInfoEntity.setUrl(mShareContent.mShareUrl);
            }
            if(!TextUtils.isEmpty(mShareContent.mShareAvatar)){
                mShareInfoEntity.setShareImage(mShareContent.mShareAvatar);
            }
        }
    }
}
