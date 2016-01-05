package cn.wei.zslq.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * @author Stay
 * @version create time：Mar 24, 2015 10:47:15 AM
 */
public class FileUtil {
    public static String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String APP = "zslq";
    public final static String TMP = "tmp";

    public static String getAppRoot() {
        File file = new File(ROOT, APP);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getTmpDir() {
        File file = new File(getAppRoot(), TMP);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String createTmpFile() {
        return getTmpDir() + File.separator + System.currentTimeMillis() + ".tmp";
    }

    public static String createTmpFile(String name) {
        return getTmpDir() + File.separator + name;
    }

    public static String getDownloadDir() {
        String dir = getAppRoot() + File.separator + "download";
        return checkDir(dir);
    }

    public static String getImgDir() {
        String dir = getAppRoot() + File.separator + "img";
        return checkDir(dir);
    }

    public static String getEmoDir() {
        String dir = getAppRoot() + File.separator + "emo";
        return checkDir(dir);
    }

    public static String getEmoDir(String group) {
        String dir = getEmoDir() + File.separator + group;
        return checkDir(dir);
    }

    public static String getEmoPath(String group, String emo) {
        return getEmoDir(group) + File.separator + emo;
    }

    private static String checkDir(String dir) {
        File directory = new File(dir);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        return dir;
    }

    public static String getDownloadPath(String name) {
        return getDownloadDir() + File.separator + name;
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static String getFilePathByUri(Activity activity, Uri uri) {
        String path = null;
        Cursor cursor = activity.managedQuery(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        // cursor will be automatically closed above 4.0
        // cursor.close();
        cursor = null;
        return path;
    }


}
