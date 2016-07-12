package com.andcup.android.app.integralwall.view.navigator;

import com.andcup.android.app.integralwall.view.family.FamilyFragment;
import com.andcup.android.app.integralwall.view.home.NestHomeFragment;
import com.andcup.android.app.integralwall.view.task.TaskFragment;
import com.andcup.android.app.integralwall.view.usercenter.UserFragment;
import com.andcup.android.frame.view.RxFragment;
import com.yl.android.cpa.R;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/10.
 */
public enum NavigatorItem {
    Home(NestHomeFragment.class, R.string.navigator_item_home, R.drawable.selector_navigator_item_home),
    Task(TaskFragment.class, R.string.navigator_item_task, R.drawable.selector_navigator_item_task),
    Family(FamilyFragment.class, R.string.navigator_item_family, R.drawable.selector_navigator_item_family),
    User(UserFragment.class, R.string.navigator_item_user, R.drawable.selector_navigator_item_user);

    int mTitle;
    int mIcon;
    Class<? extends RxFragment> mClazz;

    NavigatorItem(Class<? extends RxFragment> clazz, int title, int icon){
        mTitle = title;
        mIcon  = icon;
        mClazz = clazz;
    }

    public int getTitle() {
        return mTitle;
    }

    public int getIcon() {
        return mIcon;
    }

    public Class<? extends RxFragment> getClazz() {
        return mClazz;
    }
}
