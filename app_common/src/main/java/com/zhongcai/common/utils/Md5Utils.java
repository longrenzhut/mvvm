package com.zhongcai.common.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    /**
     *  使用md5方式进行加密
     *  @return
     */
    public static String md5Digest(String content){
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(content.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *  使用sha-1方式进行加密
     *  @return
     */
    public static String shaDigest(String content){
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("SHA-1");
            msgDigest.update(content.getBytes());
            byte[] digests = msgDigest.digest();
            //将每个字节转为16进制
            for (int i=0;i<digests.length;i++){
                builder.append(Integer.toHexString(digests[i] & 0xff +8));//+8为加盐操作
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  builder.toString();
    }

}
