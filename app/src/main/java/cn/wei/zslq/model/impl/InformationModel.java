package cn.wei.zslq.model.impl;

import android.content.Context;

import java.util.ArrayList;

import cn.wei.zslq.domain.InformationBean;
import cn.wei.zslq.domain.InformationExtraInfoBean;
import cn.wei.zslq.domain.InformationRoot;
import cn.wei.zslq.model.IInformationModel;
import cn.wei.zslq.model.ViewModel;
import cn.wei.zslq.utils.UrlHelpper;
import http.AppException;
import http.JsonCallback;
import http.Request;
import http.RequestManager;

/**
 * Created by qinwei on 2015/12/30 11:43
 * email:qinwei_it@163.com
 */
public class InformationModel extends ViewModel implements IInformationModel {
    public static final String ACTION_FIRST_LOAD = "action_first_load";
    public static final String ACTION_REFRESH_LOAD = "action_refresh_load";
    public static final String ACTION_MORE_LOAD = "action_more_load";
    public ArrayList<InformationBean> informationBeans;
    public String currentRequestTag;
    public InformationExtraInfoBean extraInfo;

    public InformationModel(Context context) {
        super(context);
        informationBeans = new ArrayList<>();
    }

    public void loadInformationRefresh(String lastCount) {
        currentRequestTag = ACTION_REFRESH_LOAD;
        loadInformation(null, lastCount);
    }

    public void loadInformationFirst() {
        currentRequestTag = ACTION_FIRST_LOAD;
        loadInformation(null, null);
    }

    public void loadInformationMore(String lastID, String lastCount) {
        currentRequestTag = ACTION_MORE_LOAD;
        loadInformation(lastID, lastCount);
    }

    @Override
    public void loadInformation(String lastID, String lastCount) {
        Request request = new Request(UrlHelpper.loadInformationList(), Request.RequestMethod.GET);
        if (lastID != null && currentRequestTag.equals(ACTION_MORE_LOAD)) {
            request.put("lastID", lastID);
        }
        if (lastCount != null) {
            request.put("lastCount", lastCount);
        }
        request.setCallback(new JsonCallback<InformationRoot>() {

            @Override
            public InformationRoot preRequest(String url) {
                return super.preRequest(url);
            }

            @Override
            public void onSuccess(InformationRoot result) {
                if (result.getCode() == 0) {
                    informationBeans = result.getResults();
                    extraInfo =  result.getExtraInfo();
                    onResponseSuccess(currentRequestTag);
                } else {
                    onResponseError(currentRequestTag, result.getCode(), result.getError());
                }
            }

            @Override
            public void onFailure(AppException exception) {
                onResponseError(currentRequestTag, exception.responseCode, exception.responseMessage);
            }
        });
        RequestManager.getInstance().execute(currentRequestTag, request);
    }
}
