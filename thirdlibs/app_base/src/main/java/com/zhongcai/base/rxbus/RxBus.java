package com.zhongcai.base.rxbus;

import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.base.fragment.AbsFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 *
 * 通信
 */

public class RxBus {


    private static RxBus instance;

    public static RxBus instance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }



    public <T> void registerRxBus(AbsActivity context, int code, final OnRxBusListener<T> listener){
        Disposable subscribe = RxBus.instance().toFlowable(code)
                .compose(context.<Message>bindUntilEvent(ActivityEvent.DESTROY))
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {

                    @Override
                    public void accept(Message t) throws Exception {
                        if (null != listener)
                            listener.OnRxBus((T) t.getValue());
                    }
                });
    }

    public <T> Disposable registerRxBus(int code, final OnRxBusListener<T> listener){
        Disposable disposable = RxBus.instance().toFlowable(code)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>() {

                    @Override
                    public void accept(Message t) throws Exception {
                        if (null != listener)
                            listener.OnRxBus((T) t.getValue());
                    }
                });
        return  disposable;
    }


 /*   public <T> void registerRxBus(int code, final OnRxBusListener<T> listener){
        RxBus.instance().toFlowable(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>(){

                    @Override
                    public void accept(Message t) throws Exception {
                        if(null != listener)
                            listener.OnRxBus((T)t.getValue());
                    }
                });
    }*/


    public <T> void registerRxBus(AbsFragment fra, int code, final OnRxBusListener<T> listener){
        RxBus.instance().toFlowable(code)
                .onBackpressureBuffer()
               .compose(fra.<Message>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Message>(){

                    @Override
                    public void accept(Message t) throws Exception {
                        if(null != listener)
                            listener.OnRxBus((T)t.getValue());
                    }
                });

    }


    public interface OnRxBusListener<T>{
        void OnRxBus(T data);
    }



    private FlowableProcessor mRxBus;

    private RxBus(){
        mRxBus = PublishProcessor.create().toSerialized();
    }

    public  <T> void post(int code,T msg){
        mRxBus.onNext(new Message(code,msg));
    }

    public <T> Flowable<Message<T>> toFlowable(final int code){
        return  mRxBus.ofType(Message.class)
                .onBackpressureBuffer()
                .filter(new Predicate<Message<T>>(){
                    @Override
                    public boolean test(Message<T> message) {
                        return message.getCode() == code;
                    }
                });
    }



    public  <T> void post(T t){
        mRxBus.onNext(t);
    }

    public <T >Flowable<T>  toFlowable(Class<T> tClass) {
        return mRxBus.ofType(tClass)
               .onBackpressureBuffer();

    }

}
