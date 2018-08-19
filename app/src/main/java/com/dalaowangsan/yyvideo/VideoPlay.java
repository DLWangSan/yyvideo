package com.dalaowangsan.yyvideo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

public class VideoPlay extends AppCompatActivity {


    X5WebView webView;
    Button play;
    Intent intent;

    public final String vip = "http://kx28.com/vip/?url=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initHardwareAccelerate();


        play = (Button) findViewById(R.id.play);

        intent = getIntent();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                play.setVisibility(View.INVISIBLE);
                String text = webView.getUrl();
                String jjj = vip + text;
                webView.loadUrl(jjj);

                Toast.makeText(getBaseContext(),"如遇长时间没有反应，请点击右上角换线路",Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(),"转动手机切换全屏，无效则在手机设置中打开自动旋转！",Toast.LENGTH_LONG).show();

//                AlertDialog dialog = new AlertDialog.Builder(getBaseContext())
//                        .setIcon(R.mipmap.yyvideologo)//设置标题的图片
//                        .setTitle("视视视频")//设置对话框的标题
//                        .setMessage("如遇长时间没有反应。请点击右上角线路切换。（建议使用“无3”和“有一”)" +
//                                "全屏请转动手机，无效请在手机设置中打开自动旋转")//设置对话框的内容
//                        //设置对话框的按钮
//                        .setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).create();
//
//                dialog.show();
                }
        });

    }

    private void init() {
        webView = new X5WebView(this, null);

        webView.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsConfirm(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            // /////////////////////////////////////////////////////////
            //
            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = webView;
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });
    }
    //硬件加速
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }


    public void initView() {
        webView = findViewById(R.id.view_web);
        webView.loadUrl(intent.getStringExtra("extra_data"));
    }


//    public void loadUrl(String url) {
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//
//        webView.loadUrl(url);
//        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onReceivedError(com.tencent.smtt.sdk.WebView var1, int var2, String var3, String var4) {
//                Toast.makeText(getApplicationContext(),"网页加载失败",Toast.LENGTH_SHORT).show();
//                Log.i("打印日志","网页加载失败");
//            }
//        });
//        //进度条
//
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    Toast.makeText(getApplicationContext(),"网页加载完成",Toast.LENGTH_SHORT).show();
//                    Log.i("打印日志","加载完成");
//                }
//            }
//        });
//    }


//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        WebView webView=(WebView)findViewById(R.id.view_web);
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack();// 返回前一个页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                play.setVisibility(View.VISIBLE);
                webView.goBack();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}