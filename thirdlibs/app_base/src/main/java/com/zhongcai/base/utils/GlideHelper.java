package com.zhongcai.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by zc3 on 2018/10/22.
 */

public class GlideHelper{

    private static GlideHelper instance;

    public static GlideHelper instance() {
        if (instance == null) {
            synchronized (GlideHelper.class) {
                if (instance == null) {
                    instance = new GlideHelper();
                }
            }
        }
        return instance;
    }

    public void loadScale(final ImageView iv, String url, int width){
        if(StringUtils.isEmpty(url) || null == iv)
            return;
        RequestOptions opt = new RequestOptions();
        opt.fitCenter().dontAnimate().override(width,-2);

        if(url.endsWith(".gif"))
            Glide.with(iv.getContext()).asGif().load(url).into(iv);
//            Glide.with(iv.getContext()).asGif().load(url)
//                    .apply(opt).into(iv);
        else
            Glide.with(iv.getContext()).asBitmap().load(url)
                    .apply(opt)
                    .into(new BitmapImageViewTarget(iv));
    }

    public void loadScale(final ImageView iv, int resource, int width){
        if(null == iv)
            return;
        RequestOptions opt = new RequestOptions();
        opt.fitCenter().dontAnimate().override(width,-2);

        Glide.with(iv.getContext()).asBitmap().load(resource)
                .apply(opt)
                .into(new BitmapImageViewTarget(iv));
    }



    public String changeUrl(String url, int width, int height){
        if(!url.contains("http"))
            return url;
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        return url + "_" + width +"x" + height + "_." + suffix;
    }

    public interface onResourceReadyListener{
        void onResourceReady(Bitmap bitmap);
    }

    public interface onResourceDrawableListener{
        void onResourceReady(Drawable drawable);
    }


    public void loadasBitmap(Context ctx, String url, final onResourceReadyListener listener){
        Glide.with(ctx).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                listener.onResourceReady(resource);
            }
        });
    }

    public void loadasDrawable(Context ctx, String url, final onResourceDrawableListener listener){
        Glide.with(ctx).asDrawable().load(url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                listener.onResourceReady(resource);
            }
        });
    }


    public void load(ImageView iv, int resource){
        RequestOptions opt = new RequestOptions();
        opt.dontAnimate();
        Glide.with(iv.getContext()).load(resource).apply(opt).into(iv);
    }

    public void load(ImageView iv, String url){
        if(StringUtils.isEmpty(url) || null == iv)
            return;
        RequestOptions opt = new RequestOptions();
        opt.dontAnimate();
        Glide.with(iv.getContext()).load(url).apply(opt).into(iv);
    }

    public void loadOss(ImageView iv, String url, int width, int height){
        String newUrl = changeUrl(url,width,height);
        load(iv,newUrl);
    }

    public void loadHeader(ImageView iv, String url){
        RequestOptions opt = new RequestOptions();
        opt.dontAnimate();
//        opt.error(R.drawable.head_defalut);
//        opt.placeholder(R.drawable.head_defalut);
        Glide.with(iv.getContext()).load(url).apply(opt).into(iv);
    }

    public void loadCircle(ImageView iv, String url){
        if(StringUtils.isEmpty(url) || null == iv) {
            if(null != iv){
//                load(iv, R.drawable.head_defalut);
            }
            return;
        }
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
//        requestOptions.error(R.drawable.head_defalut);
//        requestOptions.placeholder(R.drawable.head_defalut);
        Glide.with(iv.getContext()).load(url).apply(requestOptions).
                into(iv);
    }




}
