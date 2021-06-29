package com.smz.lexunuser.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImageToLocal {

    public static String saveBitmap(String name, Bitmap bm, Context mContext) {
        //指定我们想要存储文件的地址
        String TargetPath = mContext.getFilesDir() + "/images/";
        //判断指定文件夹的路径是否存在
        if (!FileUtils.fileIsExist(TargetPath)) {

        } else {
            //如果指定文件夹创建成功，那么我们则需要进行图片存储操作
            File saveFile = new File(TargetPath, name);

            try {
                FileOutputStream saveImgOut = new FileOutputStream(saveFile);
                // compress - 压缩的意思
                bm.compress(Bitmap.CompressFormat.PNG, 80, saveImgOut);
                //存储完成后需要清除相关的进程
                saveImgOut.flush();
                saveImgOut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return TargetPath;
    }

    public static File uri2File(Activity activity, String path) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Uri uri = Uri.parse(path);
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }


}
