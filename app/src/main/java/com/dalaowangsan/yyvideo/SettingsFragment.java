package com.dalaowangsan.yyvideo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsFragment extends Fragment {

    private TextView developer;
    private TextView dlws;
    private TextView alibaba;
    private TextView wechat;
    private TextView qqdanate;

    private TextView joinqq;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        developer=(TextView)view.findViewById(R.id.developer);
        dlws=(TextView)view.findViewById(R.id.dlws);
        alibaba=(TextView)view.findViewById(R.id.alibaba);
        wechat=(TextView)view.findViewById(R.id.wechat);
        qqdanate=(TextView)view.findViewById(R.id.qqdonate);
        joinqq=(TextView)view.findViewById(R.id.joinqq);


        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"测试",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("http://github.com/DLWangSan/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        dlws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"测试",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("http://github.com/DLWangSan/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        alibaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                String payUrl = "https://qr.alipay.com/fkx02636lqbsu6eawsps14";
//                intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + payUrl));
//
//                    startActivity(intent);

                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    //实现payUrl
                    String payUrl = "HTTPS://QR.ALIPAY.COM/FKX02636LQBSU6EAWSPS14";
                    intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + payUrl));
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(getContext(),"打开失败，请检查是否安装了支付宝！",Toast.LENGTH_SHORT).show();
                }



            }
        });

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //利用Intent打开微信
                    Uri uri = Uri.parse("weixin://");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    Toast.makeText(getContext(),"能力有限，没写出来，只能跳转到这里。欢迎大神指点",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "无法跳转到微信，请检查您是否安装了微信！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        qqdanate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setIcon(R.mipmap.yyvideologo)//设置标题的图片
                        .setTitle("QQ捐赠")//设置对话框的标题
                        .setMessage("加入QQ群即可捐赠，谢谢！")//设置对话框的内容
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
                                Toast.makeText(getContext(),"请选择QQ，加入官方QQ群",Toast.LENGTH_LONG).show();
                                String urlQQ ="http://qm.qq.com/cgi-bin/qm/qr?k=YT8TuNFOvmB-rOEa7_vB2a-tK1kwpbeW";
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));

                            }
                        }).create();

                dialog.show();
            }
        });

        joinqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"请选择QQ，加入官方QQ群",Toast.LENGTH_LONG).show();
                String urlQQ ="http://qm.qq.com/cgi-bin/qm/qr?k=YT8TuNFOvmB-rOEa7_vB2a-tK1kwpbeW";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
            }
        });

        return view;

    }




}
