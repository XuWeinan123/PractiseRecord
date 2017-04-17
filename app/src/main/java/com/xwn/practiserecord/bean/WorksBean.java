package com.xwn.practiserecord.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by xwn on 2017/1/31.
 */

public class WorksBean extends BmobObject {
    private String worksNumber;
    private String worksName;
    private Date worksCreateTime;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private int worksType;// 1：图片 2：文档 3：视频 4：压缩包 5：其他文件

    public WorksBean(String worksNumber, String worksName, Date worksCreateTime, int worksType,String url) {
        this.worksNumber = worksNumber;
        this.worksName = worksName;
        this.worksCreateTime = worksCreateTime;
        this.worksType = worksType;
        this.url = url;
    }

    public String getWorksNumber() {

        return worksNumber;
    }

    public void setWorksNumber(String worksNumber) {
        this.worksNumber = worksNumber;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }

    public Date getWorksCreateTime() {
        return worksCreateTime;
    }

    public void setWorksCreateTime(Date worksCreateTime) {
        this.worksCreateTime = worksCreateTime;
    }

    public int getWorksType() {
        return worksType;
    }

    public void setWorksType(int worksType) {
        this.worksType = worksType;
    }

    public WorksBean() {
    }
}
