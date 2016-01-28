package cn.wei.zslq.model.impl;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.wei.zslq.domain.SellerDomain;
import cn.wei.zslq.model.ISellerModel;
import cn.wei.zslq.model.ViewModel;

/**
 * Created by qinwei on 2016/1/26 10:56
 * email:qinwei_it@163.com
 */
public class SellerModel extends ViewModel implements ISellerModel {
    public ArrayList<SellerDomain> sellers;

    public SellerModel(Context context) {
        super(context);
        sellers = new ArrayList<>();
    }

    @Override
    public void loadSellerInfoList() {
        BmobQuery<SellerDomain> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(context, new FindListener<SellerDomain>() {
            @Override
            public void onSuccess(List<SellerDomain> list) {
                sellers = (ArrayList<SellerDomain>) list;
                onResponseSuccess(ACTION_LOAD_SELLER_INFO_LIST);
            }

            @Override
            public void onError(int i, String s) {
                onResponseError(ACTION_LOAD_SELLER_INFO_LIST, i, s);
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
