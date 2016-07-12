package com.andcup.android.integralwall.commons.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.ClipboardManager;
import android.util.Log;

import com.andcup.android.integralwall.commons.widget.SnackBar;

import java.io.File;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class AndroidUtils {

    public static String parseImagePathOnActivityResult(Context context, Intent data) {
        final String CONTENT = "content";
        final String FILE = "file";
        String filePath = null;
        Uri uri = data.getData();
        if (CONTENT.equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (null == cursor) {
                return null;
            }
            try {
                if (cursor.moveToNext()) {
                    filePath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                }
            } finally {
                cursor.close();
            }
        }
        // 从文件中选择
        if (FILE.equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
    }

    public static String getDiskCacheDir(Context context) {
        String DOWNLOAD = "/";
        String path = null;
        try{
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                download.mkdir();
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + DOWNLOAD + context.getPackageName();
            }
        }catch (Exception e){

        }
        return path;
    }

    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param context
     */
    public static void requestStoragePermissions(Activity context) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    context,
                    PERMISSIONS,
                    1
            );
        }
    }

    public static boolean isApkInstalled(Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();

        try {
            pm.getPackageInfo(packageName, 0);
            Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
            if(null != launchIntent) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException var4) {
            ;
        }

        return false;
    }

    public static void installApkWithPrompt(File apkFile, Context context) {
        Intent promptInstall = new Intent("android.intent.action.VIEW");
        promptInstall.addFlags(268435456);
        promptInstall.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(promptInstall);
    }

    public static boolean runApp(Context context, String apk) {
        try {
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(apk);
            context.startActivity(LaunchIntent);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static boolean isVerify(Context context, String filePath){
        boolean result = false;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(filePath,PackageManager.GET_ACTIVITIES);
            if (info != null) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }
        if(!result){
            SnackBar.make(context, "安装包出错.请重新下载安装.").show();
        }
        return result;
    }

    public static boolean copy(String content, Context context){
        try{
            ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content.trim());
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public static void getAppPackages(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> mPacks = pm.getInstalledPackages(0);
        for(PackageInfo info : mPacks){
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                Log.e(AndroidUtils.class.getName(), "user : " + info.packageName + "   " + info.applicationInfo.flags);
            } else {
                Log.e(AndroidUtils.class.getName(), "system : " + info.packageName + "   " + info.applicationInfo.flags);
            }
        }
    }
}
