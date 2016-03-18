package cn.wei.zslq;

import android.test.AndroidTestCase;

import cn.wei.library.utils.Trace;
import cn.wei.zslq.utils.UrlHelpper;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import square.retrofit.learn.BaiduHomeService;
import square.retrofit.learn.Root;

/**
 * Created by qinwei on 2016/3/14 11:27
 * email:qinwei_it@163.com
 */
public class RetrofitUnitTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testHttpRequest() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlHelpper.getApiBaiduDomain())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaiduHomeService service = retrofit.create(BaiduHomeService.class);
        Call<Root> call = service.findJokeList(1);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Response<Root> response, Retrofit retrofit) {
                Trace.e("code:" + response.message());
                if (response.code() == 200) {
                    Trace.e(response.body().getShowapi_res_body().getContentlist().get(0).getText());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Trace.e(t.getMessage());
            }
        });
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
