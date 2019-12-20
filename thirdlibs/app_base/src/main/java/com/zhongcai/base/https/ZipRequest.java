package com.zhongcai.base.https;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zc3 on 2018/3/22.
 */

public class ZipRequest{



    class Result<T,R>{

        public Result(T t, R r) {
            this.t = t;
            this.r = r;
        }

        T  t;
        R  r;
    }

    // 2个接口合在一起
    public <T,R> void zip(Observable<BaseModel<T>> s, Observable<BaseModel<R>> r
    , BaseSubscriber<Result<T,R>> req){
        Observable.zip(s,r,new BiFunction<BaseModel<T>, BaseModel<R>,Result<T,R>>(){

            @Override
            public Result<T, R> apply(BaseModel<T> tBaseModel, BaseModel<R> rBaseModel) throws Exception {
                return new Result(tBaseModel.getData().getResult(),rBaseModel.getData().getResult());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(req);
    }

    class Result3<T,R,V>{
        T  t;
        R  r;
        V  v;

        public Result3(T t, R r,V v) {
            this.t = t;
            this.r = r;
            this.v = v;
        }
    }


    // 3个接口合在一起
    public <T,R,V> void zip(Observable<BaseModel<T>> s
            , Observable<BaseModel<R>> r
            , Observable<BaseModel<V>> v
    , BaseSubscriber<Result3<T,R,V>> req){
        Observable.zip(s,r,v,new Function3<BaseModel<T>, BaseModel<R>, BaseModel<V>,Result3<T,R,V>>(){

            @Override
            public Result3<T, R, V> apply(BaseModel<T> tBaseModel, BaseModel<R> rBaseModel, BaseModel<V> vBaseModel) throws Exception {
                return  new Result3(tBaseModel.getData().getResult()
                        ,rBaseModel.getData().getResult()
                        ,vBaseModel.getData().getResult());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(req);
    }


}
