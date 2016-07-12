package com.andcup.android.app.integralwall.datalayer.activieandroid;

import com.andcup.android.app.integralwall.datalayer.model.CashingItemEntity;
import com.andcup.android.app.integralwall.datalayer.model.DailyTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.ExperienceEntity;
import com.andcup.android.app.integralwall.datalayer.model.FamilyInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.FamilyMemberEntity;
import com.andcup.android.app.integralwall.datalayer.model.FirstLandingTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.HotTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.MemberEntity;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.app.integralwall.datalayer.model.MyTaskQuickEntity;
import com.andcup.android.app.integralwall.datalayer.model.RaidersEntity;
import com.andcup.android.app.integralwall.datalayer.model.RankEntity;
import com.andcup.android.app.integralwall.datalayer.model.RewardEntity;
import com.andcup.android.app.integralwall.datalayer.model.ScoreDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.ShareInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.SignedDateEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskFeatureEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskLimitEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskQuickEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskUnionPlatformEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.database.activeandroid.Model;
import com.andcup.android.frame.datalayer.provider.ActiveAndroidProvider;
import com.andcup.lib.download.data.model.DownloadResource;
import com.andcup.lib.download.data.model.DownloadTask;
import com.andcup.lib.download.data.model.ResourceRepository;

import java.util.Arrays;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/11.
 */
public class IntegralWallModels implements ActiveAndroidProvider.ModelApis {
    @Override
    public List<Class<? extends Model>> list() {
        return Arrays.asList(MODELS);
    }

    public static Class<? extends Model>[] MODELS = new Class[]{
            DownloadResource.class,
            DownloadTask.class,
            ResourceRepository.class,

            CashingItemEntity.class,
            DailyTaskEntity.class,
            ExperienceEntity.class,
            FamilyInfoEntity.class,
            FamilyMemberEntity.class,
            FirstLandingTaskEntity.class,
            HotTaskEntity.class,
            LoginEntity.class,
            MemberEntity.class,
            MsgEntity.class,
            MyTaskQuickEntity.class,
            TaskEntity.class,
            RaidersEntity.class,
            RankEntity.class,
            RewardEntity.class,
            ScoreDetailEntity.class,
            ShareInfoEntity.class,
            SignedDateEntity.class,
            TaskBusinessEntity.class,
            TaskDetailEntity.class,
            TaskEntity.class,
            TaskFeatureEntity.class,
            TaskQuickEntity.class,
            TaskUnionPlatformEntity.class,
            UserInfoEntity.class,
            TaskDetailEntity.TaskOption.class,
            TaskLimitEntity.class
    };
}
