package com.andcup.android.app.integralwall.view.usercenter.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobFeedBack;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.dd.processbutton.iml.ActionProcessButton;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/25.
 */
public class FeedBackFragment extends BaseFragment {

    @Bind(R.id.et_content)
    EditText mEtContent;
    @Bind(R.id.btn_commit)
    ActionProcessButton mPbCommit;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed_back;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        setTitle(R.string.feedback);
        mPbCommit.setMode(ActionProcessButton.Mode.ENDLESS);
    }

    @OnClick(R.id.btn_commit)
    public void commit(View view){
        String content = mEtContent.getText().toString();
        if(!TextUtils.isEmpty(content)){
            mPbCommit.setProgress(10);
            mPbCommit.setEnabled(false);
            call(new JobFeedBack(content), new SimpleCallback<BaseEntity>() {
                @Override
                public void onError(Throwable e) {
                    mPbCommit.setProgress(0);
                    mPbCommit.setEnabled(true);
                    SnackBar.make(getActivity(),e.getMessage()).show();
                }

                @Override
                public void onSuccess(BaseEntity baseEntity) {
                    mPbCommit.setProgress(0);
                    mPbCommit.setEnabled(true);
                    SnackBar.make(getActivity(),baseEntity.getMessage()).show();
                    getActivity().finish();
                }
            });
        }else {
            SnackBar.make(getContext(),getString(R.string.error_feed_back)).show();
        }
    }
}
