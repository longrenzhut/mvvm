package com.zhongcai.base.rxbinding

import android.annotation.SuppressLint
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.android.FragmentEvent
import com.zhongcai.base.base.activity.AbsActivity
import com.zhongcai.base.base.fragment.AbsFragment
import com.zhongcai.base.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


object RxClick {


    private var mLastClickTime = 0L

    val TIME_INTERVAL = 500L

    @SuppressLint("CheckResult")
    fun click(activity: AbsActivity, view: View?, onclick: (view: View)-> Unit){

        view?.setOnClickListener {
            val nowTime = System.currentTimeMillis()
            if (nowTime - mLastClickTime > TIME_INTERVAL) {
                mLastClickTime = nowTime
                onclick.invoke(it)
            }
        }

    }

    @SuppressLint("CheckResult")
    fun click(fragment: AbsFragment, view: View?,onclick: (view: View)-> Unit){

        view?.setOnClickListener {
            val nowTime = System.currentTimeMillis()
            if (nowTime - mLastClickTime > TIME_INTERVAL) {
                mLastClickTime = nowTime
                onclick.invoke(it)
            }
        }

    }

    @SuppressLint("CheckResult")
    fun click(view: View?,onclick: (view: View)-> Unit){
        view?.setOnClickListener {
            val nowTime = System.currentTimeMillis()
            if (nowTime - mLastClickTime > TIME_INTERVAL) {
                Logger.debug((nowTime - mLastClickTime).toString())
                mLastClickTime = nowTime


                onclick.invoke(it)
            }
        }
//        view?.setOnClickListener {
//            onclick.invoke(it)
//        }

    }

//    @SuppressLint("CheckResult")
//    fun searchEdit(activity: AbsActivity,view: TextView?,afterTextChanged: (s: Editable)-> Unit){
//    Observable.create(TextChangeOnSubscribe(view))
//    .subscribeOn(Schedulers.io())
//    .observeOn(AndroidSchedulers.mainThread())
//    .compose(activity.bindUntilEvent<Editable>(ActivityEvent.DESTROY))
//    .throttleFirst(1, TimeUnit.SECONDS)
//    .subscribe {
//        afterTextChanged(it)
//    }
//    }

    @SuppressLint("CheckResult")
    fun searchEdit(fragment: AbsFragment,view: TextView?,afterTextChanged: (s: Editable)-> Unit){
        Observable.create(TextChangeOnSubscribe(view))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
            .throttleFirst(1, TimeUnit.SECONDS)
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

    @SuppressLint("CheckResult")
    fun addOnPageChange(view: ViewPager?,onPageSelected: (position: Int)-> Unit){
        view?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                onPageSelected.invoke(position)
            }

        })
    }






}