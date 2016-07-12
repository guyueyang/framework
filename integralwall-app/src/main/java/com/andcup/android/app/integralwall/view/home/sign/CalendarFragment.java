package com.andcup.android.app.integralwall.view.home.sign;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobCalendar;
import com.andcup.android.app.integralwall.datalayer.job.JobSignedDateList;
import com.andcup.android.app.integralwall.datalayer.model.CalendarEntity;
import com.andcup.android.app.integralwall.datalayer.model.SignedDateEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.dialog.ContentDialogFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/21.
 */
public class CalendarFragment extends BaseFragment {

    @Bind(R.id.gv_calendar)
    GridView mGvCalendar;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.tv_month)
    TextView mTvMonth;
    @Bind(R.id.btn_sign_in)
    Button   mBtnSign;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    CalendarAdapter mCalendarAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvTitle.setText(R.string.home_sign_in);
        Calendar cale = Calendar.getInstance();
        mCalendarAdapter = new CalendarAdapter(getActivity(), cale, 1, null);
        mGvCalendar.setAdapter(mCalendarAdapter);
        mTvScore.setText(FormatString.newCalendar(UserProvider.getInstance().getUserInfo().getScore()));
        mTvMonth.setText(JobSignedDateList.YEAR_OF_MONTH);
        call();
        load();
    }

    @OnClick(R.id.iv_back)
    public void onBack(){
        getActivity().finish();
    }

    @OnClick(R.id.tv_rule)
    public void onRule(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.CONTENT, getString(R.string.sign_rule_detail));
        Dialog.CONTENT.build(ContentDialogFragment.class, bundle).show(getChildFragmentManager());
    }

    @OnClick(R.id.btn_sign_in)
    public void onSingInClick() {
        showLoading();
        call(new JobCalendar(), new SimpleCallback<BaseEntity<CalendarEntity>>() {

            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(), e.getMessage()).show();
                hideLoading();
            }

            @Override
            public void onSuccess(BaseEntity<CalendarEntity> entity) {
                mTvScore.setText(FormatString.newCalendar(entity.body().getTotalScore()));
                SnackBar.make(getActivity(), entity.getMessage()).show();
            }

            @Override
            public void onFinish() {
                call();
            }
        });
    }

    private void call() {
        call(new JobSignedDateList(), new SimpleCallback<BaseListEntry<String>>(){
            @Override
            public void onSuccess(BaseListEntry<String> stringBaseListEntry) {
                super.onSuccess(stringBaseListEntry);
                hideLoading();
            }
        });
    }

    private void load() {
        loader(SignedDateEntity.class, Where.signInDate(UserProvider.getInstance().getUid(),
                JobSignedDateList.YEAR_OF_MONTH), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                List<SignedDateEntity> entityList = SqlConvert.convertAll(cursor, SignedDateEntity.class);
                mCalendarAdapter.notifyDataSetChanged(entityList);
                todaySigned(entityList);
            }
        });
    }

    private void todaySigned(List<SignedDateEntity> entityList){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        for( int i = 0;null !=  entityList && i< entityList.size(); i++){
            if(entityList.get(i).getDate().equals(str)){
                mBtnSign.setEnabled(false);
                mBtnSign.setText(getString(R.string.has_sign_in));
            }
        }
    }
}
