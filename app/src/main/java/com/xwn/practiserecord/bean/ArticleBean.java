package com.xwn.practiserecord.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by xwn on 2017/1/29.
 */

public class ArticleBean extends BmobObject {
    private String articleNumber;
    private String articleName;
    private Date articleCreateTime;
    private int articleCharacter;

    public ArticleBean(){

    }
    public ArticleBean(String articleNumber, String articleName, Date articleCreateTime, int articleCharacter) {
        this.articleNumber = articleNumber;
        this.articleName = articleName;
        this.articleCreateTime = articleCreateTime;
        this.articleCharacter = articleCharacter;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Date getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(Date articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    public int getArticleCharacter() {
        return articleCharacter;
    }

    public void setArticleCharacter(int articleCharacter) {
        this.articleCharacter = articleCharacter;
    }
}
