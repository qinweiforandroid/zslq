package square.retrofit.learn;

/**
 * Created by qinwei on 2016/3/14 15:20
 * email:qinwei_it@163.com
 */
public class Contentlist {
    private String ct;

    private String text;

    private String title;

    private int type;

    public void setCt(String ct){
        this.ct = ct;
    }
    public String getCt(){
        return this.ct;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }

}
