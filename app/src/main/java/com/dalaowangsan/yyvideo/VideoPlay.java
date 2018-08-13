package com.dalaowangsan.yyvideo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class VideoPlay extends AppCompatActivity {

    WebView webView;
    Button play;
    public final String vip="http://kx28.com/vip/?url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent=getIntent();

        webView=(WebView)findViewById(R.id.view_web);
        play=(Button)findViewById(R.id.play);

        webView.loadUrl(intent.getStringExtra("extra_data"));//获取视频首页地址

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;   //返回true， 立即跳转，返回false,打开网页有延时
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = webView.getUrl();
                Toast.makeText(VideoPlay.this,"选择你喜欢的浏览器打开，即可播放",Toast.LENGTH_SHORT).show();
                String jjj=vip+text;

                //调用系统浏览器打开解析后的网址
                Uri uri = Uri.parse(jjj);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webView=(WebView)findViewById(R.id.view_web);
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
