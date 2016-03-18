package square.retrofit.learn;

import cn.wei.zslq.utils.UrlHelpper;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by qinwei on 2016/3/14 11:35
 * email:qinwei_it@163.com
 */
public interface BaiduHomeService {
    @Headers("apikey:" + UrlHelpper.BAIDU_APIKEY)
    @GET("/showapi_open_bus/showapi_joke/joke_text")
    Call<Root> findJokeList(@Query("page") int page);
}
