package com.zhongcai.base.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.zhongcai.base.Config;
import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.base.application.BaseApplication;
import com.zhongcai.base.theme.statusbar.StatusBarKitkatImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by zhutao on 2018/3/7.
 */

public class BaseUtils {


    public static Resources getResource() {
        return BaseApplication.app.getResources();
    }

    public static int getDimen(int dimen) {
        return getResource().getDimensionPixelOffset(dimen);
    }

    public static int getColor(int color) {
        return getResource().getColor(color);
    }

    public static String getString(int id) {
        return getResource().getString(id);
    }

    public static Drawable getDrawable(int drawable) {
        return getResource().getDrawable(drawable);
    }


    public static String findItemByIndex(List<String> value,String index){
        if(null == value)
            return "";
        if(null == index)
            return "";

        int newIndex = ((int)Double.parseDouble(index)) - 1;
        if(newIndex < value.size())
            return value.get(newIndex);
        return "";
    }

    public static String findItemByIndex(String[] value,String index){
        if(null == value)
            return "";
        if(null == index)
            return "";

        int newIndex = ((int)Double.parseDouble(index)) - 1;
        if(newIndex < value.length)
            return value[newIndex];
        return "";
    }



    public static void setTvColor(TextView tv, int color) {
        if(null == tv)
            return;
        tv.setTextColor(getColor(color));
    }


    public static void setTvSize(TextView tv, int dimen) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getDimen(dimen));
    }

    public static void setBgColor(View view, int color) {
        if(view == null)
            return;
        view.setBackgroundColor(getColor(color));
    }

    public static void setBold(TextView view, boolean fakeBoldText) {
        Paint paint = view.getPaint();
        paint.setFakeBoldText(fakeBoldText);
    }

    public static int strToInt(String value){
        double i = Double.parseDouble(value);
        return (int)i;
    }


    public static void showSoftinput(EditText et) {
        if (null == et)
            return;
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View et) {
        if (null == et)
            return;
//        et.setFocusable(false);
        InputMethodManager manager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(et.getWindowToken(),0);
    }




    public static String[] getStringArray(int id){

        return  getResource().getStringArray(id);
    }


    public static boolean isGif(String url){
        String pathName = url.toLowerCase();
        if(TextUtils.isEmpty(pathName))
            return false;
        if(url.endsWith(".gif") || url.contains(".gif"))
            return true;
        return false;
    }

    public static <T> boolean listisNotEmpty(List<T> list){
        if(null == list || list.size() == 0)
            return false;
        return  true;
    }

    public static  boolean arrayisNotEmpty(String[] strs){
        if(null == strs || strs.length == 0)
            return false;
        return  true;
    }



    /**
     * @param view    1 显示 占位 0 不显示 占位  1 不显示 不占位
     * @param visible
     */
    public static void setVisible(View view, int visible) {
        if (null == view)
            return;
        switch (visible) {
            case 1:
                view.setVisibility(View.VISIBLE);
                break;
            case 0:
                view.setVisibility(View.INVISIBLE);
                break;
            case -1:
                view.setVisibility(View.GONE);
                break;
        }
    }

    public static boolean isVisible(View view) {
        if (null == view)
            return false;
        if(view.getVisibility() == View.VISIBLE)
            return true;
        return false;
    }


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarh() {

        return StatusBarKitkatImpl.getStatusBarHeight(BaseApplication.app);
    }

    public static Gson gson = new Gson();

    //    val type = object: TypeToken<MutableList<DepartModel>>(){}.type
    public static <T> T fromJson(String json, Class<T> classOfT){
        if(TextUtils.isEmpty(json))
            return null;
        return gson.fromJson(json,classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT){
        if(TextUtils.isEmpty(json)|| "null" == json)
            return null;
        return gson.fromJson(json,typeOfT);
    }

    public static  <T> String toJson(T t){
        if(null == t)
            return "";
        return gson.toJson(t);
    }

    public static boolean isFileExists(String fileName){

        File file = new File(Config.path + fileName);
        if(file.exists()){
            if(file.length() == 0){
                file.delete();
                return false;
            }
            else
                return true;
        }
        return false;

    }

    public static boolean isFileExists(File file){

        if(file.exists()){
            if(file.length() == 0){
                file.delete();
                return false;
            }
            else
                return true;
        }
        return false;

    }

    public static boolean isFilePathExists(String path){
        if(TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        if(file.exists()){
            if(file.length() == 0){
                file.delete();
                return false;
            }
            else
                return true;
        }
        return false;
    }

    public static boolean isDownOver(File file,int size){
        if(file.exists()){
            if(file.length() == size){
                return true;
            }
            else
                return false;
        }
        return false;
    }


    public static boolean isRealPath(String path){
        if (path.startsWith("content") || path.startsWith("file")) {
            return false;
        }
        return true;
    }


    /**
     * 这个是把文件变成二进制流
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static byte[] readStream(String path) throws Exception {
        FileInputStream fs = new FileInputStream(path);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    public static byte[] readStream(Uri fileUri) {
        return readStream(fileUri,50);
    }

    public static long getSizebyUri(Uri fileUri) {
        InputStream inputStream = null;
        ParcelFileDescriptor pfd = null;
        try {
            pfd = BaseApplication.app.getContentResolver().openFileDescriptor(fileUri, "r");
            if (pfd != null) {
                return pfd.getStatSize();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    public static long getSizebyFile(File file) {

        return getSizebyUri(Uri.fromFile(file));
    }

    public static byte[] readStream(Uri fileUri,int mb) {
        InputStream inputStream = null;
        ParcelFileDescriptor pfd = null;
        try {
            pfd = BaseApplication.app.getContentResolver().openFileDescriptor(fileUri, "r");
            if (pfd != null) {
                inputStream = new FileInputStream(pfd.getFileDescriptor());
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while (-1 != (len = inputStream.read(buffer))) {
                    outStream.write(buffer, 0, len);

                    if(outStream.size() > mb*1024*1024){
                        outStream.close();
                        pfd.close();
                        inputStream.close();
                        return outStream.toByteArray();
                    }
                }
                outStream.close();
                pfd.close();
                inputStream.close();
                return outStream.toByteArray();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ContentResolver resolver = BaseApplication.app.getContentResolver();
//        ContentProviderClient providerClient = resolver.acquireContentProviderClient(fileUri);
//         pfd = providerClient.openFile(fileUri, "r");

        return null;
    }



    public static synchronized byte[] getBlock(String path,long offset,  int blockSize) {
        if(TextUtils.isEmpty(path))
            return null;
        byte[] result = new byte[blockSize];
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(path);
            fs.skip(offset);
            int readSize = fs.read(result);
            if (readSize == -1) {
                return null;
            } else if (readSize == blockSize) {
                return result;
            } else {
                byte[] tmpByte = new byte[readSize];
                System.arraycopy(result, 0, tmpByte, 0, readSize);
                return tmpByte;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public static synchronized byte[] getBlock(Uri fileUri,long offset,  int blockSize) {
        InputStream inputStream = null;
        ParcelFileDescriptor pfd = null;
        try {
            pfd = BaseApplication.app.getContentResolver().openFileDescriptor(fileUri, "r");
            if (pfd != null) {
                byte[] result = new byte[blockSize];
                inputStream = new FileInputStream(pfd.getFileDescriptor());
                inputStream.skip(offset);
                int readSize = inputStream.read(result);
                if (readSize == -1) {
                    return null;
                } else if (readSize == blockSize) {
                    return result;
                } else {
                    byte[] tmpByte = new byte[readSize];
                    System.arraycopy(result, 0, tmpByte, 0, readSize);
                    return tmpByte;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null && null != inputStream) {
                try {
                    inputStream.close();
                    pfd.close();
                } catch (IOException e1) {
                }
            }
        }

        return null;
    }


    public static synchronized List<byte[]> getBlockList(String path, int blockSize) {
        if(TextUtils.isEmpty(path))
            return null;
        FileInputStream fs = null;
        try {

            List<byte[]> list = new ArrayList<>();

            long offset = 0;
            fs = new FileInputStream(path);

            int chunks = 0;
            long total = fs.available();

            if(total % blockSize == 0)
                chunks = (int)total/blockSize;
            else
                chunks = (int)total/blockSize + 1;


            for(int i = 0;i < chunks;i ++){
                byte[] result = new byte[blockSize];
                fs.skip(offset);
                int readSize = fs.read(result);
                if (readSize == -1) {
                } else if (readSize == blockSize) {
                    list.add(result);
                } else {
                    byte[] tmpByte = new byte[readSize];
                    System.arraycopy(result, 0, tmpByte, 0, readSize);
                    list.add(tmpByte);
                }

            }

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public static synchronized List<byte[]> getBlockList(Uri fileUri,  int blockSize) {
        InputStream inputStream = null;
        ParcelFileDescriptor pfd = null;
        try {
            pfd = BaseApplication.app.getContentResolver().openFileDescriptor(fileUri, "r");
            if (pfd != null) {
                inputStream = new FileInputStream(pfd.getFileDescriptor());


                int offset = 0;

                List<byte[]> list = new ArrayList<>();
                int chunks = 0;
                long total = inputStream.available();

                if(total % blockSize == 0)
                    chunks = (int)total/blockSize;
                else
                    chunks = (int)total/blockSize + 1;

                for(int i = 0;i < chunks;i ++){
                    byte[] result = new byte[blockSize];
                    inputStream.skip(offset);
                    int readSize = inputStream.read(result);
                    if (readSize == -1) {
                    } else if (readSize == blockSize) {
                        list.add(result);
                    } else {
                        byte[] tmpByte = new byte[readSize];
                        System.arraycopy(result, 0, tmpByte, 0, readSize);
                        list.add(tmpByte);
                    }

                }

                return list;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null && null != inputStream) {
                try {
                    inputStream.close();
                    pfd.close();
                } catch (IOException e1) {
                }
            }
        }

        return null;
    }


    public static void copyText(String text){
        if(TextUtils.isEmpty(text))
            return;
        ClipboardManager manager =  (ClipboardManager)BaseApplication.app.getSystemService(Context.CLIPBOARD_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipData data = ClipData.newPlainText("data", text);
            manager.setPrimaryClip(data);
        }
        else{
            manager.setText(text);
        }
    }


    public static void setSelection(EditText editText){
        if(null == editText)
            return;
        Editable text = editText.getText();
        Selection.setSelection(text, text.length());
    }


    public static String getUniqueId(){
        String time = String.valueOf(System.currentTimeMillis());
        Random random = new Random(100);
        return time + random.nextInt(100);
    }
}
