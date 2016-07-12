package com.andcup.android.app.integralwall.datalayer.tools;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class IOUtils {
    static String UTF8 = "utf-8";
    static int BUFFER_SIZE = 8192;

    public IOUtils() {
    }

    public static void silentlyClose(Closeable... closeables) {
        Closeable[] var1 = closeables;
        int var2 = closeables.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Closeable cl = var1[var3];

            try {
                if(cl != null) {
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
        while((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }

        return baos.toByteArray();
    }

    public static void save(String s, String filename, boolean newFile) {
        try {
            File file = new File(filename);
            if(newFile){
                file.delete();
            }
            FileOutputStream outStream = new FileOutputStream(filename, true);
            OutputStreamWriter writer = new OutputStreamWriter(outStream);
            writer.write(s);
            writer.write("/n");
            writer.flush();
            writer.close();//记得关闭
            outStream.close();
        } catch (Exception e) {
            Log.e("m", "file write error");
        }
    }
}
