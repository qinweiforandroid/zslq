package cn.wei.zslq.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.bmob.v3.listener.SaveListener;
import cn.wei.zslq.entity.ThunderAccount;
import http.Trace;

/**
 * Created by qinwei on 2016/1/6 13:31
 * email:qinwei_it@163.com
 */
public class BmobManager {
    public static void uploadXunLeiAccount(Context context) {
        ArrayList<ThunderAccount> accounts = getThunderAccounts(context);
        Trace.e("uploadXunLeiAccount:size="+accounts.size());
        for (int i = 0; i < accounts.size(); i++) {
            ThunderAccount account = accounts.get(i);
            account.save(context, new SaveListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {
                    Trace.e("onFailure:" + s);
                }
            });
        }
    }

    private static ArrayList<ThunderAccount> getThunderAccounts(Context context) {
        ArrayList<ThunderAccount> accounts = new ArrayList<>();
//        String filePath = "txt.txt"; // 文件和该类在同个目录下
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("txt.txt"))); // 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码,
            String str = null;
            ThunderAccount account = null;
            while ((str = reader.readLine()) != null) {
                str = str.replace("迅雷会员账号", "");
                String[] split = str.split("分享密码");
                account = new ThunderAccount();
                account.setAccountType(ThunderAccount.account_type_xunlei);
                account.setAccount(split[0]);
                account.setPassword(split[1]);
                accounts.add(account);
                Trace.e("用户名:" + split[0] + ",密码:" + split[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }
}
