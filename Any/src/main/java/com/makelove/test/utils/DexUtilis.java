package com.makelove.test.utils;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lyh on 2018/9/18.
 */

public class DexUtilis {


    /**
     * Dex2Byte[]
     * @param path
     * @return
     */
    public static byte[] File2bytes(String path) {


//        byte[] srcByte = null;
//        FileInputStream fis = null;
//        ByteArrayOutputStream bos = null;
//        try{
//            fis = new FileInputStream(path);
//            bos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while((len=fis.read(buffer)) != -1){
//                bos.write(buffer, 0, len);
//            }
//            srcByte = bos.toByteArray();
//        }catch(Exception e){
//            LogUtils.e("read res file error:"+e.toString());
//        }finally{
//            try{
//                fis.close();
//                bos.close();
//            }catch(Exception e){
//                LogUtils.e("close file error:"+e.toString());
//            }
//        }
//
//        if(srcByte == null){
//            LogUtils.e("get src error...");
//            return null;
//        }
//        return srcByte;


        byte[] srcByte = null;


        File file = new File(path);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            fis.read(b);
            fos = new FileOutputStream(file);

            fos.write(b);

            srcByte=b;

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(srcByte == null){
            LogUtils.e("get src error");
            return null;
        }

        return srcByte;
    }


    /**
     * @param buf
     * @param filePath
     * @param fileName
     */
    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *获取sdCard路径
     * @return
     */
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

}
