package cn.wei.zslq.domain;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class Talk extends BmobObject {
    private String id;
    private String content;
    private User createUser;
    private long timestamp;
    private Integer lookNum;
    private Integer commentNum;

    public Talk() {
        lookNum = 0;
        commentNum = 0;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public ArrayList<Talk> getTalks() {
        ArrayList<Talk> talks = new ArrayList<Talk>();
        Talk talk = new Talk();
        talk.setTimestamp(System.currentTimeMillis());
        talk.setContent("这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐,这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐");
        ArrayList<TalkComment> comments = new ArrayList<TalkComment>();
        comments.add(new TalkComment("小伟", "雨过晴天", "人之初,性本善.知之为知之,不知为不知,是知也!"));
        comments.add(new TalkComment("小明", "小伟", "呵呵,好着呢！"));
        comments.add(new TalkComment("小伟", "小明", "吃饭了没有?"));
        comments.add(new TalkComment("小明", "小伟", "吃了,你呢!"));
        comments.add(new TalkComment("李四", "哈哈!"));
        comments.add(new TalkComment("張三", "你好呀!"));
//        talk.setComments(comments);
        ArrayList<String> imgUris = new ArrayList<String>();
        imgUris.add("imgUris");
        imgUris.add("imgUris");
//        talk.setImages(imgUris);
        talks.add(talk);

        Talk talk1 = new Talk();
        talk1.setTimestamp(System.currentTimeMillis());
        talk1.setContent("这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐,这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐 这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐,这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐");
        ArrayList<TalkComment> comments1 = new ArrayList<TalkComment>();
        comments1.add(new TalkComment("小伟", "雨过晴天", "人之初,性本善.知之为知之,不知为不知,是知也!"));
        comments1.add(new TalkComment("小明", "小伟", "呵呵,好着呢！"));
        comments1.add(new TalkComment("小伟", "小明", "吃饭了没有?"));
        comments1.add(new TalkComment("小明", "小伟", "吃了,你呢!"));
        comments1.add(new TalkComment("李四", "哈哈!"));
//        talk1.setComments(comments1);
        ArrayList<String> imgUris1 = new ArrayList<String>();
        imgUris1.add("imgUris");
        imgUris1.add("imgUris");
//        talk1.setImages(imgUris1);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk);
        talks.add(talk1);
        talks.add(talk);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk1);
        return talks;
    }

    public Integer getLookNum() {
        return lookNum;
    }

    public void setLookNum(Integer lookNum) {
        this.lookNum = lookNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
