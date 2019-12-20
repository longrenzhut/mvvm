package com.zhongcai.base.rxbinding

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/9/19.
 * 监听EditTextChange
 */

class TextChangeOnSubscribe(val view: TextView?): ObservableOnSubscribe<Editable> {


    override fun subscribe(e: ObservableEmitter<Editable>) {
        view?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if(!e.isDisposed)
                    e.onNext(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        e.setDisposable(object : Disposable {
            override fun isDisposed(): Boolean {

                return true
            }

            override fun dispose() {
                view?.addTextChangedListener(null)
            }

        })
    }

}
