package cn.wei.zslq.model.impl;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.wei.library.utils.TextUtil;
import cn.wei.library.utils.Trace;
import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.domain.User;
import cn.wei.zslq.model.ITalkModel;
import cn.wei.zslq.model.ViewModel;

/**
 * Created by qinwei on 2016/1/28 10:08
 * email:qinwei_it@163.com
 */
public class TalkModel extends ViewModel implements ITalkModel {
    public static final int PAGE_SIZE = 10;
    public ArrayList<Talk> talks;
    public Talk talk;//创建说说封装对象

    public TalkModel(Context context) {
        super(context);
        talks = new ArrayList<>();
    }

    @Override
    public void loadTalkList(int pageNum) {
        BmobQuery<Talk> query = new BmobQuery<>();
        query.setLimit(PAGE_SIZE);
        query.setSkip((pageNum - 1) * PAGE_SIZE);
        query.include("createUser");
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Talk>() {
            @Override
            public void onSuccess(List<Talk> list) {
                talks = (ArrayList<Talk>) list;
                onResponseSuccess(ACTION_LOAD_TALK_LIST);
            }

            @Override
            public void onError(int i, String s) {
                Trace.e("onError:" + s);
                onResponseError(ACTION_LOAD_TALK_LIST, i, s);
            }
        });
    }

    @Override
    public void doPublishTalk(final String text, final ArrayList<String> imagePaths, final User user) {
        if (TextUtil.isValidate(imagePaths)) {
            Bmob.uploadBatch(context, imagePaths.toArray(new String[imagePaths.size()]), new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    // TODO Auto-generated method stub
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的服务器地址
                    if (urls.size() != imagePaths.size()) return;
                    talk = new Talk();
                    talk.setContent(text);
                    talk.setCreateUser(user);
                    talk.setImages((ArrayList<String>) urls);
                    talk.setTimestamp(System.currentTimeMillis());
                    talk.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            onResponseSuccess(ACTION_DO_PUBLISH_TALK);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            onResponseError(ACTION_DO_PUBLISH_TALK, i, s);
                        }
                    });
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    onResponseError(ACTION_DO_PUBLISH_TALK, statuscode, errormsg);
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    // TODO Auto-generated method stub
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）
                }
            });
        } else {
            talk = new Talk();
            talk.setContent(text);
            talk.setCreateUser(user);
            talk.setTimestamp(System.currentTimeMillis());
            talk.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    onResponseSuccess(ACTION_DO_PUBLISH_TALK);
                }

                @Override
                public void onFailure(int i, String s) {
                    onResponseError(ACTION_DO_PUBLISH_TALK, i, s);
                }
            });
        }
    }

    @Override
    public void doTalkLookNumAdd(String id) {
        JSONObject loginParams = new JSONObject();
        try {
            loginParams.put("objectId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        ace.callEndpoint(context, "talkLookNumAdd", loginParams, new CloudCodeListener() {
            @Override
            public void onSuccess(Object object) {
                Trace.d("说说查看:" + object.toString());
            }

            @Override
            public void onFailure(int code, String msg) {
                Trace.d("说说查看:" + msg);
            }
        });
    }
}
