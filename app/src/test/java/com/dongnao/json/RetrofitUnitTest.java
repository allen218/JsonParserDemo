package com.dongnao.json;


import com.dongnao.json.test.JsonRootBean;
import com.google.gson.Gson;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Example local unit test.json, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RetrofitUnitTest {


    interface IGitHub {
        // get 请求
        @GET("search/repositories")
        retrofit2.Call<JsonRootBean> search(@Query("q") String q11);

        @GET("search/repositories")
        Observable<JsonRootBean> search1(@Query("q") String q111);


//        @FormUrlEncoded
//        @POST("search/repositories")
//        Observable<JsonRootBean> search2(@Field("q") String q,@Body Object object);
    }

    @Test
    public void retrofit() throws Exception {

//        https://api.github.com/search/repositories?q=topic
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github" +
                ".com/")
                .addConverterFactory(new FastJsonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        IGitHub iGitHub = retrofit.create(IGitHub.class);


        Call<JsonRootBean> topic1 = iGitHub.search("topic");
        System.out.println(topic1);

        Response<JsonRootBean> topic = iGitHub.search("topic").execute();

        System.out.println(topic.isSuccessful());
        //String byte[]
        JsonRootBean body = topic.body();
        System.out.println(body);

//        iGitHub.search1("topic").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

//        iGitHub.search1("topic").subscribe(new Consumer<JsonRootBean>() {
//            @Override
//            public void accept(JsonRootBean jsonRootBean) throws Exception {
//                System.out.println(jsonRootBean);
//            }
//        });

    }



    @Test
    public void proxy() throws Exception {
//      动态代理

        IGitHub iGitHub = (IGitHub) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{IGitHub.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());
                GET get = method.getAnnotation(GET.class);
                Class<?>[] parameterTypes = method.getParameterTypes();
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                System.out.println(get.value());

                System.out.println(Arrays.toString(args));
                return null;
            }
        });

        iGitHub.search("1");


//        new Request.Builder().url("").build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request;
//        okHttpClient.newCall();
    }


}