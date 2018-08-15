package com.dalaowangsan.yyvideo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.bumptech.glide.Glide;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dalaowangsan.yyvideo.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {



    public final String vip="http://kx28.com/vip/?url=";


    public DrawerLayout drawerLayout;
    public Button navButton;

    private ImageView bingPicImage;
    private TextView mainPic;
    public SwipeRefreshLayout swipeRefresh;
    public boolean backFlag=false;


    //fu
    public void copyMoney(){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", "J7B9Tq8379");
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void onBackPressed() {
        if(backFlag){
            //退出
            copyMoney();
            super.onBackPressed();
        }else{
            //单击一次提示信息
            Toast.makeText(this, "再按一次退出", 0).show();
            backFlag=true;
            new Thread(){
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //3秒之后，修改flag的状态
                    backFlag=false;
                };
            }.start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21){
            View decorView =getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        copyMoney();
        setContentView(R.layout.activity_main);


        mainPic=(TextView)findViewById(R.id.main_pic);
        swipeRefresh=findViewById(R.id.swipe_refresh);

        ImageButton tecent_button=(ImageButton)findViewById(R.id.tecent);
        ImageButton youku_button=(ImageButton)findViewById(R.id.youku);
        ImageButton iqiyi_button=(ImageButton)findViewById(R.id.iqiyi);
        ImageButton letv_button=(ImageButton)findViewById(R.id.letv);
        ImageButton mango_button=(ImageButton)findViewById(R.id.manggo);
        ImageButton pptv_button=(ImageButton)findViewById(R.id.pptv);

        TextView join =(TextView)findViewById(R.id.join);

        bingPicImage=(ImageView)findViewById(R.id.bing_pic_img);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navButton=(Button)findViewById(R.id.nav_button);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"运行到测试点",Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        String bingPic =prefs.getString("bing_pic",null);
        if(bingPic != null){
            Glide.with(this).load(bingPic).into(bingPicImage);
        } else{
            loadBingPic();
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBingPic();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.mipmap.yyvideologo)//设置标题的图片
                        .setTitle("视视视频")//设置对话框的标题
                        .setMessage("进入视频网站官方主页后，选择想要播放的视频，点击左上角的播放按钮，即可调用手机浏览器直接播放视频（含VIP)。欢迎加群交流反馈，获取更多软件。")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("已经加入", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("现在加群", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"请选择QQ，加入官方QQ群",Toast.LENGTH_LONG).show();
                                String urlQQ ="http://qm.qq.com/cgi-bin/qm/qr?k=YT8TuNFOvmB-rOEa7_vB2a-tK1kwpbeW";
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
                            }
                        }).create();

                dialog.show();
                swipeRefresh.setRefreshing(false);
            }
        });


        //头部点击事件
        mainPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.mipmap.yyvideologo)//设置标题的图片
                        .setTitle("视视视频")//设置对话框的标题
                        .setMessage("进入视频网站官方主页后，选择想要播放的视频，点击左上角的播放按钮，即可调用手机浏览器直接播放视频（含VIP)。欢迎加群交流反馈，获取更多软件。")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("已经加入", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("现在加群", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"请选择QQ，加入官方QQ群",Toast.LENGTH_LONG).show();
                                String urlQQ ="http://qm.qq.com/cgi-bin/qm/qr?k=YT8TuNFOvmB-rOEa7_vB2a-tK1kwpbeW";
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));

                            }
                        }).create();

                dialog.show();
            }
        });




        //腾讯视频点击事件
        tecent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data="http://v.qq.com/";
                Intent intent=new Intent(MainActivity.this,VideoPlay.class);
                intent.putExtra("extra_data",data);
                startActivityForResult(intent,1);
            }

        });

        //优酷点击事件
        youku_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data="https://www.youku.com/";
                Intent intent=new Intent(MainActivity.this,VideoPlay.class);
                intent.putExtra("extra_data",data);
                startActivityForResult(intent,2);
            }
        });

        //爱奇艺点击事件

        iqiyi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data="http://www.iqiyi.com/";
                Intent intent=new Intent(MainActivity.this,VideoPlay.class);
                intent.putExtra("extra_data",data);
                startActivityForResult(intent,3);
            }
        });

        //乐视点击事件
        letv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data="http://www.le.com/";
                Intent intent=new Intent(MainActivity.this,VideoPlay.class);
                intent.putExtra("extra_data",data);
                startActivityForResult(intent,4);
            }
        });

        //芒果视频点击事件
        mango_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data="https://www.mgtv.com/";
                Intent intent=new Intent(MainActivity.this,VideoPlay.class);
                intent.putExtra("extra_data",data);
                startActivityForResult(intent,5);
            }
        });

        //pptv点击事件
        pptv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data="http://www.pptv.com/";
                Intent intent=new Intent(MainActivity.this,VideoPlay.class);
                intent.putExtra("extra_data",data);
                startActivityForResult(intent,6);
            }
        });


        //加入我们
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"请选择QQ，加入官方QQ群",Toast.LENGTH_LONG).show();
                String urlQQ ="http://qm.qq.com/cgi-bin/qm/qr?k=YT8TuNFOvmB-rOEa7_vB2a-tK1kwpbeW";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
            }
        });
    }


    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String bingPic=response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).load(bingPic).into(bingPicImage);
                    }
                });

            }
        });
    }
}
