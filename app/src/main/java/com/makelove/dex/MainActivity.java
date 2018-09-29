package com.makelove.dex;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.makelove.dex.utils.DexUtilis;
import com.makelove.dex.utils.LogUtils;
import com.makelove.dex.utils.ParseDexUtils;
import com.makelove.dex.utils.PermissionCallBack;
import com.makelove.dex.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity implements PermissionCallBack{

    private static String[] PERMISSIONS={
                        Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.initPermission(this,PERMISSIONS,this);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int count=0;
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.
                                shouldShowRequestPermissionRationale(this, permissions[i]);
                        if (showRequestPermission) {
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(), "权限",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        //拿到 权限
                        count++;
                    }

                }
                if(count==grantResults.length){
                    //全部拿到
                    Test();
                }else {
                    //没有全部拿到
//                    ToastUtils.showToast(this,getString(R.string.asdfasd));
//                    System.exit(0);
                }
                //初始化推送
                break;

        }
    }





    private void Test() {
        String path = DexUtilis.getSDPath() + "/" + "Dex" + "/" + "classes.dex";

        byte[] srcByte = DexUtilis.Dex(path);

        LogUtils.e("ByteSize",srcByte.length+"");



        LogUtils.e("ParseHeader:");
        ParseDexUtils.praseDexHeader(srcByte);
        LogUtils.e("ParseHeader","++++++++++++++++++++++++++++++++++++++++");




        LogUtils.e("Parse StringList:");
        ParseDexUtils.parseStringIds(srcByte);
        LogUtils.e("Parse StringIds:","++++++++++++++++++++++++++++++++++++++++");








        LogUtils.e("Parse TypeIds:");
        ParseDexUtils.parseTypeIds(srcByte);
        LogUtils.e("Parse TypeIds:","++++++++++++++++++++++++++++++++++++++++");
//
//
//
//
        LogUtils.e("Parse ProtoIds:");
        ParseDexUtils.parseProtoIds(srcByte);
        LogUtils.e("Parse ProtoIds:","++++++++++++++++++++++++++++++++++++++++");
//
//
//
//
////
        LogUtils.e("Parse FieldIds:");
        ParseDexUtils.parseFieldIds(srcByte);
        LogUtils.e("Parse FieldIds:","++++++++++++++++++++++++++++++++++++++++");
//
//
////
        LogUtils.e("Parse MethodIds:");
        ParseDexUtils.parseMethodIds(srcByte);
        LogUtils.e("Parse MethodIds:","++++++++++++++++++++++++++++++++++++++++");




        LogUtils.e("Parse ClassDefIds:");
        ParseDexUtils.parseClassDefIds(srcByte);
        LogUtils.e("Parse ClassDefIds:","++++++++++++++++++++++++++++++++++++++++");
//
//
//
//
        LogUtils.e("Parse MapList:");
        ParseDexUtils.parseMapItemList(srcByte);
        LogUtils.e("Parse MapList:","++++++++++++++++++++++++++++++++++++++++");

        LogUtils.e("Parse Class Data:");
        ParseDexUtils.parseClassData(srcByte);
        LogUtils.e("Parse Class Data:","++++++++++++++++++++++++++++++++++++++++");




//        LogUtils.e("Parse Code Content:");
//        ParseDexUtils.parseCode(srcByte);
//        LogUtils.e("Parse Code Content:","++++++++++++++++++++++++++++++++++++++++");

    }


    @Override
    public void getPermission(boolean isGet) {
        if(isGet){
            Test();
        }
    }
}
