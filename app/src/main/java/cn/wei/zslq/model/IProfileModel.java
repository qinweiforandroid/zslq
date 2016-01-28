package cn.wei.zslq.model;

/**
 * Created by qinwei on 2016/1/28 10:50
 * email:qinwei_it@163.com
 */
public interface IProfileModel {
    String ACTION_UPDATE_NICK = "action_update_nick";
    String ACTION_UPDATE_SIGNATURE="action_update_signature";
    /**
     * 修改昵称
     *
     * @param nick 昵称名称
     */
    void doUpdateNick(String nick);

    /**
     * 修改个性签名
     * @param signature 个性签名内容
     */
    void doUpdateSignature(String signature);
}
