package com.xwn.practiserecord;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xwn.practiserecord.bean.ArticleContentBean;
import com.xwn.practiserecord.util.DensityUtil;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ArticleActivity extends AppCompatActivity {

    private TextView articleContent;
    private Intent intent;
    private TextView articleName;
    private TextView articleCreateTime;
    private String articleHtmlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        DensityUtil.MIUISetStatusBarLightMode(getWindow(),true);
        intent = getIntent();
        articleName = (TextView) findViewById(R.id.article_name);
        articleCreateTime = (TextView) findViewById(R.id.article_create_time);
        articleContent = (TextView) findViewById(R.id.texttext);
        articleName.setText(intent.getStringExtra("name"));
        String tempDate = intent.getStringExtra("date");
        articleCreateTime.setText(tempDate.substring(0,4)+"年"+tempDate.substring(5,7)+"月"+tempDate.substring(8,10)+"日"+tempDate.substring(10,tempDate.length()));

        BmobQuery<ArticleContentBean> queryOne = new BmobQuery<>();
        queryOne.addWhereEqualTo("articleNumber",intent.getStringExtra("number"));
        queryOne.setLimit(1);
        queryOne.findObjects(new FindListener<ArticleContentBean>() {
            @Override
            public void done(List<ArticleContentBean> list, BmobException e) {
                if (e==null){
                    articleHtmlContent = list.get(0).getArticleContent();
                    RichText.fromMarkdown(articleHtmlContent).into(articleContent);
                    //articleContent.setText(Html.fromHtml(articleHtmlContent));
                }
            }
        });
    }
}
