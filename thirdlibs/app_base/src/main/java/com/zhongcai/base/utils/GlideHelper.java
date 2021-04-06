package com.zhongcai.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.EmptySignature;
import com.zhongcai.base.R;
import com.zhongcai.base.base.activity.AbsActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by zc3 on 2018/10/22.
 */

public class GlideHelper  {

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



    public void loadGif(final ImageView iv, String url){
        Glide.with(iv.getContext()).asGif().load(url).into(iv);
    }

    public void load(final ImageView imageView, String url,int placeholder){

        Glide.with(imageView.getContext())
                .load(url)
                .override(200, 200)
                .centerCrop()
                .apply(new RequestOptions().placeholder(placeholder)
                .error(placeholder))
                .into(imageView);

    }

    public void loadplaceholder(final ImageView iv, String url,int placeholder){

        RequestOptions opt = new RequestOptions();
        opt.error(placeholder);
        opt.placeholder(placeholder);
        opt.dontAnimate();
        Glide.with(iv.getContext()).load(url).apply(opt).into(iv);

    }

    public void save(final ImageView iv, String url,int placeholder){


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

    public interface onResourceFileListener{
        void onResourceReady(File file);
    }


    public void loadasBitmap(Context ctx, String url, final onResourceReadyListener listener){
        Glide.with(ctx).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                listener.onResourceReady(resource);
            }
        });

    }

    public void loadasFile(Context ctx, String url, final onResourceFileListener listener){
        Glide.with(ctx).asFile().load(url).into(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                listener.onResourceReady(file);
            }
        });

    }


    public void loadasBitmap(Context ctx, String url,ImageView imageView ,int placeholder,final onResourceReadyListener listener){

        Glide.with(ctx).asBitmap().load(url)
                .override(180, 180)
                .centerCrop()
                .sizeMultiplier(0.5f)
                .apply(new RequestOptions().placeholder(placeholder))
                .into(new BitmapImageViewTarget(imageView) {
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
        if(BaseUtils.isGif(url))
            Glide.with(iv.getContext()).asGif().load(url).into(iv);
        else
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
                load(iv, R.drawable.tt_default_portrait);
            }
            return;
        }
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        requestOptions.error(R.drawable.tt_default_portrait);
        requestOptions.placeholder(R.drawable.tt_default_portrait);
        Glide.with(iv.getContext()).load(url).apply(requestOptions).
                into(iv);
    }

    public void loadRoundedCorners(ImageView iv, String url,int radius){
        if(StringUtils.isEmpty(url) || null == iv) {
            if(null != iv){
                load(iv, R.drawable.tt_default_portrait);
            }
            return;
        }


        RoundedCorners roundedCorners = new RoundedCorners(radius);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(roundedCorners);
        Glide.with(iv.getContext()).load(url).apply(requestOptions).
                into(iv);
    }


    //4.0
    public File getCacheFile2(AbsActivity act,String url) {
        DataCacheKey dataCacheKey = new DataCacheKey(new GlideUrl(url), EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(dataCacheKey);
        try {
            int cacheSize = 100 * 1000 * 1000;
            DiskLruCache diskLruCache = DiskLruCache.open(new File(act.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, cacheSize);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



//    @Override
//    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
////        Glide.with(activity).load(path).into(imageView);
//        GlideHelper.instance().load(imageView,path);
//    }
//
//    @Override
//    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
////        Glide.with(activity).load(path).into(imageView);
//        GlideHelper.instance().load(imageView,path);
//    }
//
//    @Override
//    public void clearMemoryCache() {
//
//    }

}
