package com.zhongcai.base.rxbinding

import android.annotation.SuppressLint
import android.text.Editable
import android.view.View
import android.widget.TextView
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.FragmentEvent
import com.zhongcai.base.base.activity.AbsActivity
import com.zhongcai.base.base.fragment.AbsFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object RxClick {


    @SuppressLint("CheckResult")
    fun click(activity: AbsActivity, view: View?,onclick: (view: View)-> Unit){
        Observable.create(ViewClickOnSubscribe(view))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe {
                if(null != view)
                    onclick.invoke(view)
            }

    }

    @SuppressLint("CheckResult")
    fun click(fragment: AbsFragment, view: View?,onclick: (view: View)-> Unit){
        Observable.create(ViewClickOnSubscribe(view))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe {
                if(null != view)
                    onclick.invoke(view)
            }

    }

    @SuppressLint("CheckResult")
    fun click(view: View?,onclick: (view: View)-> Unit){
        Observable.create(ViewClickOnSubscribe(view))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe {
                if(null != view)
                    onclick.invoke(view)
            }

    }

    @SuppressLint("CheckResult")
    fun searchEdit(activity: AbsActivity,view: TextView?,afterTextChanged: (s: Editable)-> Unit){
        Observable.create(TextChangeOnSubscribe(view))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                afterTextChanged(it)
            }
    }

    @SuppressLint("CheckResult")
    fun searchEdit(fragment: AbsFragment,view: TextView?,afterTextChanged: (s: Editable)-> Unit){
        Observable.create(TextChangeOnSubscribe(view))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                afterTextChanged(it)
            }
    }

    @SuppressLint("CheckResult")
    fun textChange(view: TextView?,afterTextChanged: (s: Editable)-> Unit){
        Observable.create(TextChangeOnSubscribe(view))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                afterTextChanged(it)
            }
    }






}