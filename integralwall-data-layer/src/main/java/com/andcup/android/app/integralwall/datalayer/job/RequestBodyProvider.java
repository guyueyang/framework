package com.andcup.android.app.integralwall.datalayer.job;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/16.
 */
public interface RequestBodyProvider<T> {

    RequestBody build(T data);

    RequestBodyProvider<String> STRING = new RequestBodyProvider<String>() {
        @Override
        public RequestBody build(String data) {
            return RequestBody.create(MediaType.parse("multipart/form-data"), data);
        }
    };

    RequestBodyProvider<File> FILE = new RequestBodyProvider<File>() {

        @Override
        public RequestBody build(File file) {
            return RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }
    };
}
