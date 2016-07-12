package com.andcup.android.app.integralwall.third.tools;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2016/3/16.
 */
public class IOUtils {
    static String UTF8     = "utf-8";
    static int BUFFER_SIZE = 8192;

    public IOUtils() {
    }

    public static void silentlyClose(Closeable... closeables) {
        Closeable[] var1 = closeables;
        int var2 = closeables.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Closeable cl = var1[var3];

            try {
                if (cl != null) {
                    cl.close();
                }
            } catch (Exception var6) {
                Log.e("IOUtils", "silentlyClose", var6);
            }
        }

    }

    public static String readToString(InputStream is) throws IOException {
        byte[] data = readToByteArray(is);
        return new String(data, UTF8);
    }

    public static byte[] readToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];

        int len;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }

        return baos.toByteArray();
    }
}
