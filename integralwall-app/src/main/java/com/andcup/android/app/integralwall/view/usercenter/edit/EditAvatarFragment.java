package com.andcup.android.app.integralwall.view.usercenter.edit;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobEditAvatar;
import com.andcup.android.app.integralwall.datalayer.model.AvatarEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.datalayer.task.RxAsyncTask;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.andcup.android.widget.cropper.UCrop;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/24.
 */
public class EditAvatarFragment extends GateDialogFragment {

    public static final String AVATAR_NAME = "avatar.png";
    public static final int TAKE_PHOTO = 1;
    public static final int TAKE_PHOTO_FROM_CAMERA = 3;
    public static final int PICK_PHOTO = 2;
    private static final int IMAGE_SIZE = 150;
    @Bind(R.id.cp_avatar)
    URIRoundedImageView mCpAvatar;
    String mAvatarPath;
    String mResultUri;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_head_portrait;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mCpAvatar.setEditImageURI(Uri.parse(UserProvider.getInstance().getUserInfo().getAvatar()));
    }

    @Override
    public void onResume() {
        super.onResume();
        //fix: 部分机型图片裁剪返回不会走result，导致无法上传头像解决方案.
        if( !TextUtils.isEmpty(mResultUri) ){
            Uri result = Uri.parse(mResultUri);
            mCpAvatar.setEditImageURI(result);
            mAvatarPath = result.getPath();
//            mResultUri  = null;
        }
    }

    @OnClick(R.id.btn_take_photo)
    public void takePhoto(View view) {
        //获取完整图像.
        String filepath = Environment.getExternalStorageDirectory() + "/" + AVATAR_NAME;
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(filepath);//localTempImgDir和localTempImageFileName是自己定义的名字
        Uri u = Uri.fromFile(f);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        startActivityForResult(intent, TAKE_PHOTO_FROM_CAMERA);
        //获取缩略图方法.
        //startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PHOTO);
    }

    @OnClick(R.id.btn_pick_avatar)
    public void pickAvatar(View view) {
        final String IMAGE_TYPE = "image/*.png";
        Intent pickAlbum = new Intent(Intent.ACTION_PICK, null);
        pickAlbum.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
        try {
            startActivityForResult(pickAlbum, PICK_PHOTO);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            //
            mAvatarPath = resultUri.getPath();
            mCpAvatar.setEditImageURI(resultUri);

        } else if (requestCode == TAKE_PHOTO || requestCode == PICK_PHOTO || requestCode == TAKE_PHOTO_FROM_CAMERA) {
            if (requestCode == TAKE_PHOTO) {
                capture(data);
            } else if (requestCode == PICK_PHOTO) {
                crop(AndroidUtils.parseImagePathOnActivityResult(getActivity(), data));
            }else if(requestCode == TAKE_PHOTO_FROM_CAMERA){
                crop(Environment.getExternalStorageDirectory() + "/" + AVATAR_NAME);
            }
        }
    }

    private String getAvatarUri(String filepath) {
        if (TextUtils.isEmpty(filepath)) {
            return null;
        }
        String uri = filepath;
        if (!uri.startsWith("file://")) {
            uri = "file://" + filepath;
        }
        return uri;
    }

    private void crop(String filepath) {
        String uri = getAvatarUri(filepath);
        if (null != uri) {
            mResultUri = "file:///data/data/" + getActivity().getPackageName() + "/" + System.currentTimeMillis() + AVATAR_NAME;
            //png.
            UCrop.Options options = new UCrop.Options();
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            options.setCompressionQuality(50);
            UCrop.of(Uri.parse(uri), Uri.parse(mResultUri))
                    .withOptions(options)
                    .withAspectRatio(1,1)
                    .start(getActivity());
        }
    }

    private void capture(Intent data) {
        new RxAsyncTask<String>() {
            @Override
            public String onExecute() {
                return captureAvatar(data);
            }

            @Override
            public void onSuccess(String s) {
                crop(s);
            }
        }.execute();
    }

    private String captureAvatar(Intent data) {
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        String filepath = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + AVATAR_NAME;
        File myCaptureFile = new File(filepath);
        try {
            if (myCaptureFile.exists()) {
                myCaptureFile.delete();
            }
            myCaptureFile.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
            return filepath;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onOk() {
        if(TextUtils.isEmpty(mAvatarPath)){
            return;
        }
        try {
            File imageFile = new File(mAvatarPath);
            if (!imageFile.exists()){
                return;
            }
        }catch (Exception e){
        }
        showLoading();
        call(new JobEditAvatar(mAvatarPath), new SimpleCallback<BaseEntity<AvatarEntity>>() {
            @Override
            public void onError(Throwable e) {
                hideLoading();
                SnackBar.make(getContext(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<AvatarEntity> avatarEntityBaseEntity) {
                SnackBar.make(getContext(), avatarEntityBaseEntity.getMessage()).show();
                EventBus.getDefault().post(new AvatarEntity());
                dismissAllowingStateLoss();
                hideLoading();
            }
        });
    }

    @Override
    protected void onCancel() {
        dismissAllowingStateLoss();
    }
}
