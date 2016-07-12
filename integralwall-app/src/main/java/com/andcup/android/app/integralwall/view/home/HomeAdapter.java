package com.andcup.android.app.integralwall.view.home;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.HotTaskEntity;
import com.andcup.android.app.integralwall.event.CheckNavigatorItemEvent;
import com.andcup.android.app.integralwall.event.OpenFirstLandingTaskEvent;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.app.integralwall.tools.TaskHelper;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public class HomeAdapter extends BaseAdapter{

    List<HomeItem> mHomeItem;

    public HomeAdapter(Context context){
        super(context);
    }

    public void notifyDataSetChanged(List<HomeItem> homeItems){
        mHomeItem = homeItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mHomeItem.get(position).getType();
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType){
            case HomeItem.TYPE_TITLE:
                return R.layout.list_item_title;
            case HomeItem.TYPE_TASK:
                return R.layout.list_item_task;
            case HomeItem.TYPE_NEW_USER_REWARD:
                return R.layout.list_item_new_user_task;
            case HomeItem.TYPE_DAILY_TASK:
                return R.layout.list_item_reward_daily;
            case HomeItem.TYPE_MORE:
                return R.layout.list_item_more;
        }
        return 0;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        switch (viewType){
            case HomeItem.TYPE_TITLE:
                return new TitleViewHolder(itemView);
            case HomeItem.TYPE_TASK:
                return new TaskViewHolder(itemView);
            case HomeItem.TYPE_DAILY_TASK:
            case HomeItem.TYPE_NEW_USER_REWARD:
                return new NewUserRewardsViewHolder(itemView);
            case HomeItem.TYPE_MORE:
                return new MoreViewHolder(itemView);
            default:
                return new TaskViewHolder(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return null == mHomeItem ? 0 : mHomeItem.size();
    }

    class MoreViewHolder extends BaseViewHolder {
        public MoreViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            EventBus.getDefault().post(new CheckNavigatorItemEvent(1));
        }
    }

    class NewUserRewardsViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_score)
        TextView mTvScore;
        public NewUserRewardsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            String score = (String) mHomeItem.get(mPosition).getData();
            String textScore = mContext.getResources().getString(R.string.format_score, score);
            mTvScore.setText(FormatString.newScore(textScore, score));
        }
        @Override
        public void onClick(View view){
            super.onClick(view);
            EventBus.getDefault().post(new OpenFirstLandingTaskEvent());
        }
    }

    class TitleViewHolder extends BaseViewHolder {
        public TitleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            EventBus.getDefault().post(new CheckNavigatorItemEvent(1));
        }
    }

    class TaskViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_content)
        TextView mTvContent;
        @Bind(R.id.tv_score)
        TextView mTvScore;
        @Bind(R.id.riv_avatar)
        URIRoundedImageView mUriAvatar;

        public TaskViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            HotTaskEntity task = (HotTaskEntity) mHomeItem.get(mPosition).getData();
            mTvTitle.setText(task.getName());
            mTvContent.setText(task.getIntro());

            String score = String.valueOf(task.getLowestScore());
            String textScore = mContext.getResources().getString(R.string.format_score, score);
            mTvScore.setText(FormatString.newScore(textScore, score));
            mUriAvatar.setImageURI(Uri.parse(task.getImage()));
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            HotTaskEntity task = (HotTaskEntity) mHomeItem.get(mPosition).getData();
            TaskHelper.openTask(task.getTaskId());
        }
    }
}
