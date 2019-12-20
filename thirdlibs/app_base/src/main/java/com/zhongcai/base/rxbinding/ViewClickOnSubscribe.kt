package com.zhongcai.base.rxbinding

import android.view.View
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/8/24.
 */
class ViewClickOnSubscribe(val view: View?): ObservableOnSubscribe<Int> {
    override fun subscribe(e: ObservableEmitter<Int>) {
        view?.let{
            view.setOnClickListener{
                if(!e.isDisposed){
                    e.onNext(view.id)
                }
            }
        }

        e.setDisposable(object : Disposable{
            override fun isDisposed(): Boolean {

                return true
            }

            override fun dispose() {
                view?.setOnClickListener(null)
            }

        })
    }

}