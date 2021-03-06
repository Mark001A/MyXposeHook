package com.example.administrator.xpose_hook6;

import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/20/020.
 */

public class XposeUtil {
    public static String FILE_PATH_XPOSR = "/.xpose/";
    public static String FileRecordPackageName = "FileRecordPackageName";
    public static String FileRecordPackageNameSwitch = "FileRecordPackageNameSwitch";
    public static String m_deviceId = "deviceId";//设备id
    public static String m_androidId = "androidId";//android_id
    public static String m_phoneNum = "phoneNum";//电话号码
    public static String m_simSerialNumber = "simSerialNumber";//手机卡序列号
    public static String m_subscriberId = "subscriberId";//IMSI
    public static String m_simOperator = "simOperator";//运营商
    public static String m_networkOperatorName = "networkOperatorName";//网络类型名
    public static String m_networkType = "networkType";//网络类型
    public static String m_phoneType = "phoneType";//手机类型
    public static String m_simState = "simState";//手机卡状态
    public static String m_macAddress = "macAddress";//mac地址
    public static String m_SSID = "SSID";//无线路由名
    public static String m_BSSID = "BSSID";//无线路由地址
    public static String m_firmwareversion = "firmwareversion";//固件版本
    public static String m_bluetoothaddress = "bluetoothaddress";//蓝牙地址
    public static String m_screenSize = "screenSize";//蓝牙地址

    public static String m_RELEASE = "RELEASE";//系统版本
    public static String m_SDK = "SDK";//系统版本值
    public static String m_framework = "framework";//系统架构
    public static String m_brand = "brand";//手机品牌
    public static String m_model = "model";//手机型号
    public static String m_product = "product";//产品名
    public static String m_manufacture = "manufacture";//制造商
    public static String m_hardware = "hardware";//硬件
    public static String m_fingerprint = "fingerprint";//指纹
    public static String m_serial = "serial";//序列号

    public static String pkg1 = "de.robv.android.xpose.installer";
    public static String pkg2 = "pro.burgerz.wsm.manager";
    public static String pkg3 = "com.example.administrator.xpose_hook6";

    public static JSONObject configMap = new JSONObject();

    /**
     * 将map信息写入供ｘｐｏｓｅ调用
     */
    public static void saveConfigMap(){

        new Thread(){
            @Override
            public void run() {
                saveFileData("xposeDevice.txt",configMap.toString());
            }
        }.start();
    }
//    /**
//     * 读取文本数据到configMap
//     */
//    public static void initConfigMap(){
//        try {
//            configMap = new JSONObject(getFileData("xposeDevice.txt"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private static void saveFileData(String fileName,String value){
        File localFile = new File(Environment.getExternalStorageDirectory()+FILE_PATH_XPOSR);
        if(!localFile.exists()){
            localFile.mkdir();
        }
        localFile = new File(localFile,fileName);
        if(!localFile.exists()){
            try {
                localFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        L.debug("写入文本"+value);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(localFile);
            fos.write(value.getBytes("UTF-8"));
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

//    private static String getFileData(String fileName){
//
//        File localFile = new File(Environment.getExternalStorageDirectory()+FILE_PATH_XPOSR,fileName);
//        if(!localFile.exists()){
//            try {
//                localFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        BufferedReader reader = null;
//        String result = "";
//        try {
//            reader = new BufferedReader(new FileReader(localFile));
//            result = reader.readLine();
//            if(result == null)
//                return "";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if(reader != null)
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//        }
//
//        return result;
//    }
//    public static  String  PhoneData(int index){
////        String FilePath="/storage/sdcard0/nowphonedata.txt";
//        String FilePath ="SM-J7108,samsung,23,6.0.1,357212074848144,82a13111a639c2a,1080*1920,480,b3:n3:m3:j4:k2:p2,b3:n3:m3:j4:k2:p2,XM,12345678911,as,hu,b3:n3:m3:j4:k2:p2,15,1,1,1,xiao,xiao,12345678912,18712312312";
//        Log.d("PhoneData", "PhoneData: getData(FilePath)=====>"+getData(FilePath));
////        String[] data= getData(FilePath).split(",");
//        String[] data= FilePath.split(",");
//        L.debug("data数据====>"+data.toString());
//        return data[index];
//    }
    /**
     * 读取文件
     * @param FilePath
     */
    public static String getData(String FilePath){
        String PhoneData="";
        try{
            File file = new File(FilePath);//创建一个文件对象
            if(file.exists()){
                FileInputStream inStream = new FileInputStream(file);//读文件
                byte[] buffer = new byte[3600];//缓存
                int len = 0;
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                while( (len = inStream.read(buffer))!= -1){//直到读到文件结束
                    outStream.write(buffer, 0, len);
                }
                byte[] data = outStream.toByteArray();//得到文件的二进制数据
                Log.d("PhoneData", "getData:new String(data) =====>"+new String(data));
                PhoneData=new String(data);
                Log.d("PhoneData", "getData:PhoneData =====>"+PhoneData);
                outStream.close();
                inStream.close();
            }else{
                PhoneData="";
                Log.d("PhoneData", "getData:文件不存在 ");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("PhoneData", "getData: e======>"+e.getMessage());
            PhoneData="";
        }
        return PhoneData;
    }
}
