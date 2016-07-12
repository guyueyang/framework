package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/16.
 */
public class JobUploadImages extends IntegralWallJob<BaseEntity> {

    List<String> mImageList;
    String mMark;

    public JobUploadImages(String mark, List<String> images){
        mImageList = images;
        mMark      = mark;
    }

    @Override
    public BaseEntity start() throws IOException {
        return multiUpload().execute().body();
    }

    private Call<BaseEntity> multiUpload(){
        Map<String, RequestBody> requestMap = new HashMap<>();
        for(int i = 0; i< mImageList.size(); i++){
            File file = new File(mImageList.get(i));
            RequestBody body = RequestBodyProvider.FILE.build(file);
            requestMap.put("image" + i + "\"; filename=\"" + file.getName(), body);
        }
        String uid  = UserProvider.getInstance().getUid();
        RequestBody rbUid = RequestBodyProvider.STRING.build(uid);
        long   time = getTimestamp();
        String sign = md5(uid + time + getKey());
        RequestBody rbSign = RequestBodyProvider.STRING.build(sign);
        RequestBody rbMark = RequestBodyProvider.STRING.build(mMark);
        return apis().uploadImages(rbUid, requestMap, rbMark, time, rbSign);
    }
}
