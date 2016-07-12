package com.andcup.android.app.integralwall.view.task.biz.load;

import com.andcup.lib.download.DownloadListener;
import com.andcup.lib.download.DownloadManager;
import com.andcup.lib.download.TaskCreator;
import com.andcup.lib.download.data.model.DownloadResource;
import com.andcup.lib.download.data.model.DownloadTask;

import java.io.File;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class TaskLoader {

    final String DOWNLOADING = ".download";
    final String DOWNLOADED = "";

    private DownloadTask mDownloadTask;
    private String       mFilePath;

    public TaskLoader(){

    }

    public void setFilePath(String filepath){
        mFilePath = filepath;
        mDownloadTask = TaskQuery.query(mFilePath);
    }

    public boolean auto(){
        mDownloadTask = TaskQuery.query(mFilePath);
        // finish.
        if(isCompleted()){
            return false;
        }
        startIfNeed();
//        if(isDownloading()){
//            pause();
//        }else{
//            startIfNeed();
//        }
        return true;
    }

    private void startIfNeed(){
        if( null != mDownloadTask ){
            resume();
        }else{
            start();
        }
    }

    public void pause(){
        if( null != mDownloadTask && !mDownloadTask.isPause()){
            DownloadManager.getInstance().pause(mDownloadTask.getTaskId());
        }
    }

    public boolean isStarted(){
        if( null != mDownloadTask){
            return true;
        }
        return false;
    }

    public boolean isCompleted(){
        return isCompleted(true);
    }

    public boolean isCompleted(boolean judgeFile){
        if( null != mDownloadTask){
            if(mDownloadTask.isCompleted()){
                // file has delete
                return judgeFile ? isDownloadFileExists(DOWNLOADED): true;
            }
        }
        return false;
    }

    public boolean isPause(){
        if( null != mDownloadTask){
            return mDownloadTask.isPause();
        }
        return false;
    }

    public boolean isDownloading(){
        if( null != mDownloadTask){
            return mDownloadTask.isDownloading();
        }
        return false;
    }

    public int getProgress(){
        if( null != mDownloadTask){
            return mDownloadTask.getProgress();
        }
        return 0;
    }

    public String getLocalPath(){
        if(null == mDownloadTask){
            return null;
        }
        List<DownloadResource> resources = mDownloadTask.getResources();
        if( null != resources && resources.size() > 0){
            return resources.get(0).getLocalPath();
        }
        return null;
    }

    public long getTaskId(){
        if( null != mDownloadTask){
            return mDownloadTask.getTaskId();
        }
        return -1;
    }

    public void addListener(DownloadListener downloadListener){
        DownloadManager.getInstance().addDownloadListener(downloadListener);
    }

    public void removeListener(DownloadListener downloadListener){
        DownloadManager.getInstance().removeDownloadListener(downloadListener);
    }

    private void resume(){
        List<DownloadResource> resources = mDownloadTask.getResources();
        if( null != resources && resources.size() > 0){
            if(!isDownloadFileExists(DOWNLOADING)){
                // file has delete.
                DownloadManager.getInstance().delete(mDownloadTask.getTaskId(), true);
                // start new download.
                start();
            }else{
                // resume exist task.
                DownloadManager.getInstance().start(mDownloadTask.getTaskId());
            }
        }else{
            start();
        }
    }

    private boolean isDownloadFileExists(String prefix){
        List<DownloadResource> resources = mDownloadTask.getResources();
        if( null != resources && resources.size() > 0){
            File file = new File(resources.get(0).getLocalPath() + prefix);
            return file.exists();
        }
        return false;
    }

    private void start(){
        TaskCreator creator = TaskCreator.creator();
        creator.setTitle(mFilePath);
        creator.setDescription(mFilePath);
        creator.addResource(mFilePath, mFilePath);
        mDownloadTask = DownloadManager.getInstance().add(creator);
    }
}
