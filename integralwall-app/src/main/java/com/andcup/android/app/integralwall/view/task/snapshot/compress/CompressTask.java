package com.andcup.android.app.integralwall.view.task.snapshot.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.andcup.android.frame.AndcupApplication;
import com.andcup.android.frame.datalayer.task.RxAsyncTask;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/6/7.
 */
public class CompressTask extends RxAsyncTask<List<String>> {

    List<String> mImageList;

    public CompressTask(List<String>imageList){
        mImageList = imageList;
    }

    @Override
    public List<String> onExecute() {
        List<String> imageList = new ArrayList<>();
        for( int i = 0; i< mImageList.size(); i++){
            String path = AndroidUtils.getDiskCacheDir(AndcupApplication.getInstance()) + "/task_upload_" + i + ".jpg";
            imageList.add(path);
            File file = new File(path);
            file.delete();
            Bitmap bitmap = BitmapFactory.decodeFile(mImageList.get(i));
            ImageUtils.compressBmpToFile(bitmap, file);
            Log.v(CompressTask.class.getName(), "path = " + path);
        }
        return imageList;
    }
}
