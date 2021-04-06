package com.zhongcai.base.https;


import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.zhongcai.base.Config;
import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.base.application.BaseApplication;
import com.zhongcai.base.base.fragment.AbsFragment;
import com.zhongcai.base.https.jsonfactory.JsonConverterFactory;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/7.
 */

public class HttpProvider {

    private final static int CONNECT_TIMEOUT = 120;


    public static String getJavaUrl(){
        if(Config.DEVELOP)
            return Config.JAVA_URL_DEV;
        if(Config.TEST)
            return Config.JAVA_URL_TEST;
        if(Config.PRE)
            return Config.JAVA_URL_PRE;
        return Config.JAVA_URL;
    }

    public static String getBuyJavaUrl(){
        if(Config.DEVELOP)
            return Config.BUY_JAVA_URL_DEV;
        if(Config.TEST)
            return Config.BUY_JAVA_URL_TEST;
        if(Config.PRE)
            return Config.BUY_JAVA_URL_PRE;
        return Config.BUY_JAVA_URL;
    }

    public static String getCUrl(){
        if(Config.DEVELOP)
            return Config.C_URL_DEV;
        if(Config.TEST)
            return Config.C_URL_TEST;
        if(Config.PRE)
            return Config.C_URL_PRE;
        return Config.C_URL;
    }



    public static String getPhpUrl(){
        if(Config.DEVELOP)
            return  Config.PHP_URL_DEV;
        if(Config.TEST)
            return Config.PHP_URL_TEST;
        if(Config.PRE)
            return Config.PHP_URL_PRE;
        return Config.PHP_URL;
    }

    public static String getLoadingUrl(){
        if(Config.DEVELOP)
            return  Config.DEV_UPLOAD;
        if(Config.TEST)
            return Config.TEST_UPLOAD;
        if(Config.PRE)
            return Config.UPLOAD_PRE;
        return Config.UPLOAD;
    }

    private OkHttpClient.Builder createOkBuilder(){
        OkHttpClient.Builder builder =  new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new  HttpLoggingInterceptor();
        //打印请求的数据的logo
        if (Config.DEVELOP || Config.TEST || Config.PRE)
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        builder.addInterceptor(loggingInterceptor)
//                .addInterceptor(new Interceptor(){
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Request.Builder builder = request.newBuilder();
//
//
//                        Request newRequest = builder.build();
//                        Response response = chain.proceed(newRequest);

//                        Response response = chain.proceed(request);
//                        Request newRequest;
//                        int code = response.code();
//                        if (code == 307) {
//                            //获取重定向的地址
//                            String location = response.headers().get("Location");
//                            //重新构建请求
//                            newRequest = builder.url(location).build();
//                        }else {
//                            newRequest = builder.build();
//                        }
//                        response = chain.proceed(newRequest);
//
//                        return response;
//                    }})
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
//        builder.sslSocketFactory(SSLHelper.getSSLCertifcation(BaseApplication.app));

//        builder.addInterceptor(headerInterceptor)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {

                // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                final X509TrustManager trustAllCert =
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        };
                final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                builder.sslSocketFactory(sslSocketFactory, trustAllCert);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return builder;
    }

    private Retrofit.Builder builder;
    private OkHttpClient.Builder requestBuilder;

    private HttpProvider(){
        requestBuilder = createOkBuilder();

        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());// 添加 RxJava 适配器

    }

    /**
     * 普通的数据请求 动态设置 基地址
     * @param url
     * @return
     */
    public Retrofit buildRetrofit(String url){
//        if (!(Config.DEVELOP || Config.TEST )){
//            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(
//                    new InputStream[]{BaseApplication.app.getAssentsInputStream("zhongcai.cer")},
//                    BaseApplication.app.getAssentsInputStream("zhongcai.cer"), null);
//            requestBuilder.socketFactory(sslParams.sSLSocketFactory);
//        }
        return builder.client(requestBuilder.build()).baseUrl(url).build();
    }


    public static HttpProvider httpProvider;

    public static void init() {
        httpProvider = new HttpProvider();
    }


    public static HttpProvider getHttp() {
        return httpProvider;
    }


    public  BaseService createPService(){
        return getHttp().buildRetrofit(getPhpUrl()).create(BaseService.class);
    }


    public  BaseService createJService(){
        return getHttp().buildRetrofit(getJavaUrl()).create(BaseService.class);
    }

    public  BaseService createJBuyService(){
        return getHttp().buildRetrofit(getBuyJavaUrl()).create(BaseService.class);
    }

    public  BaseService createCService(){
        return getHttp().buildRetrofit(getCUrl()).create(BaseService.class);
    }

    private final static int CONNECT_TIMEOUT_FILE = 60;

    //---------------下载图片 文件-------
    private OkHttpClient.Builder upload;
    public Retrofit create(String url){
        if(null == upload) {
            upload = new OkHttpClient.Builder();

//            upload.retryOnConnectionFailure(true);
            //打印请求的数据的logo
//            HttpLoggingInterceptor loggingInterceptor = new  HttpLoggingInterceptor();
//            if (Config.DEVELOP || Config.TEST || Config.PRE)
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            else
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//            upload.addInterceptor(loggingInterceptor);
            upload.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
//                    originalResponse.request().url()
                    return originalResponse
                            .newBuilder()
//                            .removeHeader("User-Agent")
//                            .addHeader("User-Agent", WebSettings.getDefaultUserAgent(BaseApplication.app))
//                            .addHeader("Content-Type","Content-Type: application/json;charset=UTF-8")
                            .body(new FileResponseBody(originalResponse))
                            .build();
                }
            });
        }
        return builder.client(upload.build()).baseUrl(url).build();
    }



    public BaseFileService createDownloadService(){
        return create(getLoadingUrl()).create(BaseFileService.class);
    }


    public BaseFileService createDownloadService(String url){
        return create(url).create(BaseFileService.class);
    }




    //----------------------- 上传文件 如 图片 文件等 ---------------------------

    private OkHttpClient.Builder upFileBuilder;
    public Retrofit createUpFileRetrofit(String url){
        if(null == upFileBuilder) {
            upFileBuilder = new OkHttpClient.Builder();
            upFileBuilder.connectTimeout(CONNECT_TIMEOUT_FILE, TimeUnit.SECONDS)
                    .readTimeout(CONNECT_TIMEOUT_FILE,TimeUnit.SECONDS)
                    .writeTimeout(CONNECT_TIMEOUT_FILE, TimeUnit.SECONDS);
            upFileBuilder.retryOnConnectionFailure(false);

        }
        return builder.client(upFileBuilder.build()).baseUrl(url).build();
    }


    public  BaseFileService createUploadService(){

        return createUpFileRetrofit(getLoadingUrl()).create(BaseFileService.class);
    }



    public <T> Disposable upFile(AbsActivity abs, String url, UpFileParam params, ReqCallBack<T> callBack){

        return createUploadService().upFile(url, params.getMap())
                .compose(abs.bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqSubscriber(callBack));

    }



    public  Disposable downFile(AbsActivity abs, String baseUrl, String url, DisposableObserver<ResponseBody> callback){

        return createDownloadService(baseUrl).loadFile(url)
                .compose(abs.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(callback);

    }


    public  Disposable downFile(AbsActivity abs, String baseUrl, String url,Map<String, Object> map,DisposableObserver<ResponseBody> callback){

        return createDownloadService(baseUrl).loadFile(url,map)
                .compose(abs.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(callback);

    }

    public static String getBasUrl(String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }


    public Disposable downFile(AbsActivity abs,String url,DownFileSubscriber callBack){

        String baseUrl = getBasUrl(url);
        String newUrl = url.replace(baseUrl,"");
        return createDownloadService(baseUrl).loadFile(newUrl)
                .compose(abs.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(callBack);

    }

    public Disposable downFile(AbsFragment fra, String url, DownFileSubscriber callBack){

        String baseUrl = getBasUrl(url);
        String newUrl = url.replace(baseUrl,"");
        return createDownloadService(baseUrl).loadFile(newUrl)
                .compose(fra.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(callBack);

    }

    public <T> Disposable upFile(String url, UpFileParam params, ReqCallBack<T> callBack){

        return createUploadService().upFile(url, params.getMap())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqSubscriber<>(callBack));

    }

//    public <T> Disposable loadFile(String url, UpFileParam params, ReqCallBack<T> callBack){
//
//        return createDownloadService().loadFile(url, params.getMap())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(new ReqSubscriber<>(callBack));
//
//    }

    public <T> Disposable postP(String url, Params params, ReqCallBack<T> callBack){
        return  createPService().post(url,params.getBody())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqSubscriber<>(callBack));

    }

    public <T> Disposable postJ(String url, Params params, ReqCallBack<T> callBack){
        return  createJService().post(url,params.getBody())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqSubscriber<>(callBack));

    }

    public <T> Disposable postJBuy(String url, Params params, ReqCallBack<T> callBack){
        return  createJBuyService().post(url,params.getBody())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqSubscriber<>(callBack));

    }

    public <T> Disposable postC(String url, Params params, ReqCallBack<T> callBack){
        return  createCService().post(url,params.getBody())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqSubscriber<>(callBack));

    }



}
