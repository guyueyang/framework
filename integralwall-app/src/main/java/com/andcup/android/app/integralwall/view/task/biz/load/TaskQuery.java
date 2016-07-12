package com.andcup.android.app.integralwall.view.task.biz.load;

import android.text.TextUtils;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.query.Select;
import com.andcup.lib.download.DownloadManager;
import com.andcup.lib.download.data.loader.DownloadTaskDao;
import com.andcup.lib.download.data.model.DownloadTask;

import java.util.List;


/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class TaskQuery {

    public static DownloadTask query(String appPath){
        List<DownloadTask> taskList = queryTasks(appPath);
        if(taskList != null && taskList.size() > 0){
            return taskList.get(0);
        }
        return null;
    }

    private static List<DownloadTask> queryTasks(String description){
        if(TextUtils.isEmpty(description)){
            return null;
        }
        return DownloadTaskDao.getTasks(DbColumn.DESCRIPTION + " =?", description);
    }

    public static void clear( ){
        DownloadManager.getInstance().pauseAll();
        List<DownloadTask> tasks = (new Select()).from(DownloadTask.class).execute();
        for( int i = 0; null != tasks && i< tasks.size(); i++){
            DownloadManager.getInstance().delete(tasks.get(i).getTaskId(), true);
        }
    }
}
