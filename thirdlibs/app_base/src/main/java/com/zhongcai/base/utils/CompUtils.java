package com.zhongcai.base.utils;

import com.zhongcai.base.base.activity.AbsActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class CompUtils {


    public static void compress(AbsActivity act, final String url,int size,
     final onCompressOneLisenter lisenter){
        Luban.with(act)
                .load(url)
                .ignoreBy(size)
                .setFocusAlpha(false)
//                .setTargetDir(getPath())
        .filter(new CompressionPredicate(){

            @Override
            public boolean apply(String path) {
                return !(StringUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener(){
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                lisenter.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {
            }
        }).launch();
    }

    public static void compress(AbsActivity act,  String url,
                                final onCompressOneLisenter lisenter){
        compress(act,url,100,lisenter);
    }


    public static void compress(AbsActivity act, final List<String> urls,int size,
     final onCompressLisenter lisenter){
        Luban.with(act)
                .load(urls)
                .ignoreBy(size)
                .setFocusAlpha(false)
//                .setTargetDir(getPath())
        .filter(new CompressionPredicate(){

            @Override
            public boolean apply(String path) {
                return !(StringUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener(){
            int len = 0;
            List<File> files =  new ArrayList<File>();
            @Override
            public void onStart() {
                len = 0;
            }

            @Override
            public void onSuccess(File file) {
                len ++;
                files.add(file);
                if(len == urls.size()){
                    lisenter.onSuccess(files);
                }
            }

            @Override
            public void onError(Throwable e) {
                len ++;
                if(len == urls.size()){
                    lisenter.onSuccess(files);
                }
            }
        }).launch();
    }


    public static void compress(AbsActivity act, final List<String> urls,
                                final onCompressLisenter lisenter){
        compress(act,urls,100,lisenter);
    }

    public interface onCompressLisenter{
        void onSuccess(List<File> list);
    }

    public interface onCompressOneLisenter{
        void onSuccess(File file);
    }




//    private fun getPath(): String {
//        val path = Config.path +  "cache/pic/"
//        val file = File(path)
//        return if (file.mkdirs()) {
//            path
//        }
//        else path
//    }



}
