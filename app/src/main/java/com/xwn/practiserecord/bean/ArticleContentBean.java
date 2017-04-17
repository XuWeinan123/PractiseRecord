package com.xwn.practiserecord.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xwn on 2017/2/5.
 */

public class ArticleContentBean extends BmobObject {
    private String articleNumber;
    private String articleContent;

    public ArticleContentBean(){
    }
    public ArticleContentBean(String articleNumber, String articleContent) {
        this.articleNumber = articleNumber;
        this.articleContent = articleContent;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}
