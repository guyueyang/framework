package com.andcup.android.integralwall.commons.tools;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by Amos on 2015/9/9.
 */
public class CacheManager {

    /**
     * * (/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir().getAbsolutePath());
    }

    /**
     * *(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory("/data/data/"
                + context.getPackageName() + "/databases");
    }

    /**
     * * SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory("/data/data/"
                + context.getPackageName() + "/shared_prefs");
    }

    /**
     * * * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * /data/data/com.xxx.xxx/files * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir().getAbsolutePath());
    }

    /**
     * * (/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir().getAbsolutePath());
        }
    }
    /**
     * *
     *
     * @param filePath
     * */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(filePath);
    }

    /**
     * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * *
     *
     * @param directory
     */
    @Deprecated
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    public static void deleteFilesByDirectory(String filePath){
        Log.v("Cache", "Folder = " + filePath);
        File file = new File(filePath);
        if(!file.exists()){
            return;
        }
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File subFile :files){
                deleteFilesByDirectory(subFile.getAbsolutePath());
            }
        }
        file.delete();
    }

    //
    //Context.getExternalFilesDir() --> SDCard/Android/data/package/files/dir
    //Context.getExternalCacheDir() --> SDCard/Android/data/package/cache/dir
    public static long getFolderSize(File file) throws Exception {
        Log.v("Cache", "Folder = " + file.getAbsolutePath());
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                //
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     *
     *
     * @param deleteThisPath
     * @param
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {// Ŀ¼
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + " KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " TB";
    }


    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }
}
