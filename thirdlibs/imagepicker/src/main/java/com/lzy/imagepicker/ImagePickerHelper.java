package com.lzy.imagepicker;

import android.app.Activity;
import android.content.Intent;

import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;

public class ImagePickerHelper {

    //type 0 表示相机  1表示打开相册
    public static void start(Activity ctx, int type, int selectLimit){

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new imageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(false);
        imagePicker.setSelectLimit(selectLimit);
        imagePicker.setCrop(false);     //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);//是否按矩形区域保存

        if(type == 0){
            Intent intent = new Intent(ctx, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            ctx.startActivityForResult(intent, 1000);
        }
        else{
            Intent intent = new Intent(ctx, ImageGridActivity.class);
            ctx.startActivityForResult(intent, 1000);
        }
    }


    public static ArrayList<ImageItem> onActivityResult(int requestCode,int resultCode, Intent data){
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1000) {
                return (ArrayList<ImageItem>)data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            }
        }
        return  null;
    }

}
