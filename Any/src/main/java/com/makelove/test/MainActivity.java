package com.makelove.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.makelove.test.bean.Dex;
import com.makelove.test.utils.DexHander;
import com.makelove.test.utils.DexUtilis;
import com.makelove.test.utils.PermissionCallBack;
import com.makelove.test.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity implements PermissionCallBack {

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

    /**
     * 对Dex进行处理修改
     */
    private void DexProcessing() {
        String path = DexUtilis.getSDPath() + "/" + "File2bytes" ;

        byte[] srcByte = DexUtilis.File2bytes(path+ "/" + "classes.dex");

        DexHander dexHander = new DexHander();

        Dex dex = dexHander.SetDex(srcByte);

        byte[] bytes = dex.setDebugInfoZero();

        DexUtilis.byte2File(bytes,path,"SucessClasses.dex");
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
                    DexProcessing();
                }else {
                    //没有全部拿到
//                    ToastUtils.showToast(this,getString(R.string.asdfasd));
//                    System.exit(0);
                }
                //初始化推送
                break;

        }
    }
    @Override
    public void getPermission(boolean isGet) {
        if(isGet){
            DexProcessing();
        }
    }

}
