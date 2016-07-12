package com.andcup.android.app.integralwall.view.task.biz;

import android.content.Context;

import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.datalayer.tools.TimeUtils;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.task.biz.load.TaskLoader;
import com.andcup.android.database.activeandroid.query.Update;
import com.andcup.android.frame.datalayer.sql.action.SqlDelete;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.sql.action.SqlSelect;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class TaskBusiness {

    public interface OnLoadListener {
        void onComplete(String filepath);
    }

    TaskDetailEntity mEntity;
    Context mContext;
    TaskLoader mTaskLoader;

    public TaskBusiness(Context context, TaskLoader taskLoader) {
        mContext = context;
        mTaskLoader = taskLoader;
    }

    public void setTaskDetailEntity(TaskDetailEntity mTaskDetailEntity) {
        this.mEntity = mTaskDetailEntity;
    }

    public void start(String filepath) {
        // has install and first install app.
        if (AndroidUtils.isApkInstalled(mContext, mEntity.getAppUri()) && (Sharef.isDownload(mEntity.getAppUri()) || !mEntity.isFirstDayTask())) {
            AndroidUtils.runApp(mContext, mEntity.getAppUri());
            setupMonitor(filepath);
        } else if (AndroidUtils.isApkInstalled(mContext, mEntity.getAppUri()) && mEntity.isOptionVarify()) {
            AndroidUtils.runApp(mContext, mEntity.getAppUri());
        } else if (!mTaskLoader.auto()) {
            // has download finish.
            if (Action.install(mContext, filepath)) {
                setupMonitor(filepath);
            }
        }
    }

    private void setupMonitor(String filepath) {
        if (!mEntity.isTodayCompleted()) {
            EventBus.getDefault().post(Action.create(filepath, mEntity));
        }
    }

    public static class Action {

        public static boolean install(Context context, String filepath) {
            if (AndroidUtils.isVerify(context, filepath)) {
                AndroidUtils.installApkWithPrompt(new File(filepath), context);
                return true;
            } else {
                try {
                    File file = new File(filepath);
                    file.delete();
                } catch (Exception e) {

                }
            }
            return false;
        }

        public static TaskBusinessEntity create(String filepath, TaskDetailEntity taskDetailEntity) {
            TaskBusinessEntity entity = new TaskBusinessEntity();
            entity.setOptionInfo(taskDetailEntity.getOptionTitle());
            entity.setDate(TimeUtils.getCurrentDay());
            entity.setLimitRunTime(getLimitTime(taskDetailEntity));
            entity.setScore(Long.parseLong(taskDetailEntity.getOptionScore()));
            entity.setUserId(UserProvider.getInstance().getUid());
            entity.setApkPackage(taskDetailEntity.getAppUri());
            entity.setTaskOptionId(taskDetailEntity.getTaskOptionId());
            entity.setTaskId(taskDetailEntity.getTaskId());
            entity.setAppPath(filepath);
            entity.setIsNeedSnapshot(taskDetailEntity.isOptionSnapshot());
            return entity;
        }

        public static void delete(TaskBusinessEntity taskBusinessEntity) {
            SqlDelete<TaskBusinessEntity> operator = new SqlDelete<TaskBusinessEntity>(
                    TaskBusinessEntity.class,
                    Where.taskBusiness(taskBusinessEntity.getTaskId(), null));
            operator.delete();
        }

        public static TaskBusinessEntity query(String taskId, String optionId) {
            SqlSelect<TaskBusinessEntity> operator = new SqlSelect<>(TaskBusinessEntity.class, Where.taskBusiness(taskId, optionId));
            List<TaskBusinessEntity> list = operator.select(1);
            try {
                return list.get(0);
            } catch (Exception e) {

            }
            return null;
        }

        public static void update(TaskBusinessEntity taskBusinessEntity) {
            new Update(TaskBusinessEntity.class).set("mTimeEnd=?", taskBusinessEntity.getTimeEnd()).execute();
        }

        public static void insert(TaskBusinessEntity taskBusinessEntity) {
            SqlInsert<TaskBusinessEntity> operator = new SqlInsert<>(TaskBusinessEntity.class);
            operator.insert(taskBusinessEntity);
        }

        private static int getLimitTime(TaskDetailEntity taskDetailEntity) {
            List<TaskDetailEntity.TaskOption> taskOptions = taskDetailEntity.getTaskOptions();
            for (int i = 0; null != taskOptions && i < taskOptions.size(); i++) {
                if (taskDetailEntity.getTaskOptionId().equals(taskOptions.get(i).getOptionId())) {
                    return taskOptions.get(i).getResidenceTime();
                }
            }
            return 0;
        }
    }
}
