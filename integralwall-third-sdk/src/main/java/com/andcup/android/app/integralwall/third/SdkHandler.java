package com.andcup.android.app.integralwall.third;

import android.content.Context;

import com.andcup.android.app.integralwall.third.tools.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Amos on 2015/11/2.
 */
public class SdkHandler {

    public static <T> T handler(Context context, String file, Class<T> tClass){
        try {
            InputStream inputStream = context.getAssets().open(file);
            String pc = IOUtils.readToString(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(pc, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
