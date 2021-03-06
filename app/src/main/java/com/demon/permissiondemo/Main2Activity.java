package com.demon.permissiondemo;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demon.permissiondemo.permission.PermissionUtil;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * EasyPermissions.PermissionCallbacks 权限获取状态的监听
 * EasyPermissions.RationaleCallbacks 权限获取失败后弹出的对话框的[取消，确认]按钮监听
 */
public class Main2Activity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    //要申请的权限
    private String[] mPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
    public static final int CODE = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //检查权限，防止重复获取
        mPermissions = PermissionUtil.getDeniedPermissions(this, mPermissions);
        if (mPermissions.length > 0) {
            /**
             * 1.上下文
             * 2.权限失败后弹出对话框的内容
             * 3.requestCode
             * 4.要申请的权限
             */
            EasyPermissions.requestPermissions(this, PermissionUtil.permissionText(mPermissions), CODE, mPermissions);
        }
    }

    //所有的权限申请成功的回调
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //do something
    }

    //权限获取失败的回调
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //存在被永久拒绝(拒绝&不再询问)的权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            mPermissions = PermissionUtil.getDeniedPermissions(this, mPermissions);
            PermissionUtil.PermissionDialog(this, PermissionUtil.permissionText(mPermissions) + "请在应用权限管理进行设置！");
        } else {
            EasyPermissions.requestPermissions(this, PermissionUtil.permissionText(mPermissions), CODE, mPermissions);
        }
    }

    //权限被拒绝后的显示提示对话框，点击确认的回调
    @Override
    public void onRationaleAccepted(int requestCode) {
        //会自动再次获取没有申请成功的权限
        //do something
    }

    //权限被拒绝后的显示提示对话框，点击取消的回调
    @Override
    public void onRationaleDenied(int requestCode) {
        //什么都不会做
        //do something
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将结果传入EasyPermissions中
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
