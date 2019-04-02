package com.newbet.byt.newapp.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.newbet.byt.newapp.R;
import com.newbet.byt.newapp.activity.account.LoginActivity;
import com.newbet.byt.newapp.bean.GetVerifyImageRes;
import com.newbet.byt.newapp.bean.VerifyCodeRes;
import com.newbet.byt.newapp.globe.Constant;
import com.newbet.byt.newapp.globe.SingleInstance;
import com.newbet.byt.newapp.network.NetWorkUtils;
import com.newbet.byt.newapp.widget.EditTextClear;

public class DialogUtils {
    private static final String           TAG = "DialogUtils";
    private static       AlertDialog      sAlertDialog;
    private static       Observer<String> sObserver;
    private static       Observer<String> sObserverResult;
    private static       String           sKey;

    private static AlertDialog getLoadingDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        sAlertDialog = builder.create();
        sAlertDialog.setCancelable(false);
        return sAlertDialog;
    }

    public static void showLoading(Context context) {
        sAlertDialog = null;
        getLoadingDialog(context);
        sAlertDialog.show();
        sAlertDialog.setContentView(R.layout.layout_loading_dialog);
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window                     window = sAlertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = UIUtils.dpToPx(context, 300);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public static void dismisDialog() {
        if (sAlertDialog != null) {
            sAlertDialog.dismiss();
        }
    }

    // 显示版本更新对话框
    public static void showUpdate(Activity context, String des, String url) {
        sAlertDialog = null;
        getLoadingDialog(context);
        sAlertDialog.show();
        sAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View     inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_update, null, false);
        TextView tvDes   = inflate.findViewById(R.id.update_des);
        tvDes.setText(des);
        inflate.findViewById(R.id.update_btn).setOnClickListener(v -> {
            //      sAlertDialog.dismiss();
            // 判断存储权限
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(context, "打开设置界面", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1101);
                }
            } else {
                DownloadUtils.downloadFiles(url);
            }
        });
        sAlertDialog.setContentView(inflate);
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window                     window = sAlertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = UIUtils.dpToPx(context, 300);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    // 发送短信码对话框
    public static void showVerify(Activity context, String phone) {
        sAlertDialog = null;
        getLoadingDialog(context);
        sAlertDialog.show();
        sAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_verify, null, false);
        inflate.findViewById(R.id.dialog_verify_close).setOnClickListener(v -> {
            cancelVerifyDialog();
        });
        ImageView ivCode = inflate.findViewById(R.id.dialog_verify_code);

        // 加载验证码图片
        sObserver = s -> {
            if (s != null) {
                GetVerifyImageRes getVerifyImageRes = SingleInstance.getGson().fromJson(s, GetVerifyImageRes.class);
                if (getVerifyImageRes.getStatusCode() == 200) {
                    String base64Image = getVerifyImageRes.getData().getBase64Image();
                    sKey = getVerifyImageRes.getData().getKey();
                    Bitmap bitmap = FileUtils.stringToBitmap(base64Image);
                    Glide.with(context).load(bitmap).apply(Constant.noCacheOptions).into(ivCode);
                    LogUtils.e(TAG, "skey value is = " + sKey);
                } else {
                    Toast.makeText(context, getVerifyImageRes.getInfo() + " ,验证码获取异常，请重试...", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // 发送结果
        sObserverResult = s -> {
            if (s != null) {
                LogUtils.e(TAG, "showVerify ---: " + s);
                VerifyCodeRes verifyCodeRes = SingleInstance.getGson().fromJson(s, VerifyCodeRes.class);
                if (verifyCodeRes.getStatusCode() == 200) {
                    SingletonTimer.getInstance().startTime(verifyCodeRes.getData().getNextTime());
                    LiveEventBus.get().with(Constant.CODE_DIALOG_ACTIVITY, Integer.class).setValue(1);
                    cancelVerifyDialog();
                } else {
                    String info = verifyCodeRes.getInfo();
                    if (TextUtils.isEmpty(info)) {
                        Toast.makeText(context, "不能频繁发送，请 " + verifyCodeRes.getData().getNextTime() + "s 后再试", Toast.LENGTH_SHORT).show();
                        SingletonTimer.getInstance().setTime(verifyCodeRes.getData().getNextTime());
                        LiveEventBus.get().with(Constant.CODE_DIALOG_ACTIVITY, Integer.class).setValue(0);
                        cancelVerifyDialog();
                    } else
                        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                }
            }
        };
        LiveEventBus.get().with(Constant.CODE_YANZHENGMA_KEY, String.class).observeStickyForever(sObserver);
        LiveEventBus.get().with(Constant.CODE_YANZHENGMA_RESULT, String.class).observeStickyForever(sObserverResult);

        // 点击更换
        ivCode.setOnClickListener(v ->
                ThreadUtils.runOnSubThread(() -> {
                    String verifyImage = NetWorkUtils.getVerifyImage();
                    SingleInstance.getGson().fromJson(verifyImage, GetVerifyImageRes.class);
                    LiveEventBus.get().with(Constant.CODE_YANZHENGMA_KEY).postValue(verifyImage);
                })
        );

        // 首次打开获取验证码
        ThreadUtils.runOnSubThread(() -> {
            String verifyImage = NetWorkUtils.getVerifyImage();
            LiveEventBus.get().with(Constant.CODE_YANZHENGMA_KEY).postValue(verifyImage);
        });

        EditTextClear editTextClear = inflate.findViewById(R.id.dialog_verify_et);
        inflate.findViewById(R.id.dialog_verify_submit).setOnClickListener(v -> {
            String string = editTextClear.getText().toString();
            if (TextUtils.isEmpty(string)) {
                Toast.makeText(context, "验证码不能为空...", Toast.LENGTH_SHORT).show();
                return;
            }
            ThreadUtils.runOnSubThread(() -> {
                String smsCode = NetWorkUtils.getSMSCode(phone, string, sKey);
                LiveEventBus.get().with(Constant.CODE_YANZHENGMA_RESULT).postValue(smsCode);
            });
        });
        sAlertDialog.setContentView(inflate);
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window                     window = sAlertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = UIUtils.dpToPx(context, 300);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private static void cancelVerifyDialog() {
        sAlertDialog.dismiss();
        LiveEventBus.get().with(Constant.CODE_YANZHENGMA_KEY, String.class).removeObserver(sObserver);
        LiveEventBus.get().with(Constant.CODE_YANZHENGMA_RESULT, String.class).removeObserver(sObserverResult);
    }

    //    网络结果对话框
    public static void createResultDialog(Context context, String des, boolean isSuccess) {
        sAlertDialog = null;
        getLoadingDialog(context);
        sAlertDialog.show();
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_login, null, false);
        sAlertDialog.setContentView(inflate);
        TextView textView = inflate.findViewById(R.id.dialog_tv_des);
        textView.setText(des);
        ImageView imageView = inflate.findViewById(R.id.dialog_iv);
        if (isSuccess) {
            imageView.setBackground(context.getResources().getDrawable(R.drawable.success));
        } else
            imageView.setBackground(context.getResources().getDrawable(R.drawable.wrong));
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window                     window = sAlertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = UIUtils.dpToPx(context, 300);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    // 是否登录对话框
    public static void createLogin(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("是否登录")
                .setMessage("当前功能需要登陆才能使用,是否登录？")
                .setNegativeButton("稍后再说", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("去登录", (dialogInterface, i) -> {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    dialogInterface.dismiss();
                }).setCancelable(false)
                .create();
        sAlertDialog = null;
        sAlertDialog = alertDialog;
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.color_2bb89c));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.color_2bb89c));
    }
}
