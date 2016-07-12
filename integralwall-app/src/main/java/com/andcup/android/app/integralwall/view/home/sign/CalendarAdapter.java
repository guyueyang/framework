package com.andcup.android.app.integralwall.view.home.sign;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.SignedDateEntity;
import com.andcup.android.app.integralwall.view.base.BaseListAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.integralwall.commons.tools.CalendarUtils;
import com.yl.android.cpa.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/21.
 */
public class CalendarAdapter extends BaseListAdapter{

    private ArrayList<CalendarUtils.Day> mDays;
    private Calendar        mCalendar;
    private Context         mContext;
    private static final String FILL = "0";

    public CalendarAdapter(Context context, Calendar c, int passDays, String orderDay) {
        super(context);
        this.mCalendar = c;
        this.mContext  = context;
        mDays = CalendarUtils.getDaysOfMonth(this.mCalendar, passDays, orderDay);
    }

    public void notifyDataSetChanged(List<SignedDateEntity> signedDateEntities){
        for(int i = 0; i< mDays.size(); i++){
            mDays.get(i).setOrdered(false);
            String day = mDays.get(i).getName();
            if(!TextUtils.isEmpty(day) && day.length() <= 1){
                day = FILL + day;// 01 02 03 ... 09 and so on.
            }
            for(int j = 0; null != signedDateEntities && j < signedDateEntities.size(); j++){
                String[] days = signedDateEntities.get(j).getDate().split("-");
                if(days[days.length - 1].equals(day)){
                    mDays.get(i).setOrdered(true);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return mDays.get(i);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public int getItemCount() {
        return mDays == null? 0: mDays.size();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_calendar;
    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    class ViewHolder extends BaseViewHolder{

        static final int BOTTOM_LEFT  = 28;
        static final int BOTTOM_RIGHT = 34;

        @Bind(R.id.tv_day)
        TextView mTvDay;
        @Bind(R.id.cb_check)
        CheckBox mCbCheck;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            CalendarUtils.Day day = mDays.get(position);
            mTvDay.setText(day.getName());
            if(position == BOTTOM_LEFT){
                mTvDay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_calendar_bottom_left));
            }
            if(position == BOTTOM_RIGHT){
                mTvDay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_calendar_bottom_right));
            }
            mTvDay.setEnabled(day.getType() == CalendarUtils.Day.DayType.TODAY);
            mCbCheck.setChecked(day.isOrdered());
        }
    }
}
