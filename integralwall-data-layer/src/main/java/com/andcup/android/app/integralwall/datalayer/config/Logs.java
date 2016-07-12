package com.andcup.android.app.integralwall.datalayer.config;

import android.os.Environment;

import com.andcup.android.app.integralwall.datalayer.tools.IOUtils;

import java.io.File;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/19.
 */
public interface Logs {

    String LOG1_PATH = "/lylog1.txt";
    String LOG2_PATH = "/lylog2.txt";

    void log(String message);

    void clear();

    Logs LOG1 = new Logs() {
        @Override
        public void log(String message) {
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + LOG1_PATH;
            IOUtils.save(message + "\n", filepath, false);
        }

        @Override
        public void clear() {
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + LOG1_PATH;
            File file = new File(filepath);
            if(file.exists()){
                file.delete();
            }
        }
    };

    Logs LOG2 = new Logs() {
        @Override
        public void log(String message) {
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + LOG2_PATH;
            IOUtils.save(message + "\n", filepath, false);
        }

        @Override
        public void clear() {
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + LOG2_PATH;
            File file = new File(filepath);
            if(file.exists()){
                file.delete();
            }
        }
    };
}
