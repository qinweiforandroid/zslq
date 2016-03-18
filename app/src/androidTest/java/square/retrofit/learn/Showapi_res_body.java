package square.retrofit.learn;

import java.util.List;

/**
 * Created by qinwei on 2016/3/14 15:20
 * email:qinwei_it@163.com
 */
public class Showapi_res_body {
    private int allNum;

    private int allPages;

    private List<Contentlist> contentlist ;

    public void setAllNum(int allNum){
        this.allNum = allNum;
    }
    public int getAllNum(){
        return this.allNum;
    }
    public void setAllPages(int allPages){
        this.allPages = allPages;
    }
    public int getAllPages(){
        return this.allPages;
    }
    public void setContentlist(List<Contentlist> contentlist){
        this.contentlist = contentlist;
    }
    public List<Contentlist> getContentlist(){
        return this.contentlist;
    }
}
