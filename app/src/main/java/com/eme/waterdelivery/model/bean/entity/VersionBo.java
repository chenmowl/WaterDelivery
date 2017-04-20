package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 版本xml对应实体
 *
 * Created by dijiaoliang on 17/4/19.
 */
public class VersionBo {
    private String id;
    private String title;
    private String version;
    private String description;
    private String url;
    private String md5;
    private List<String> refreshVersionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public List<String> getRefreshVersionList() {
        return refreshVersionList;
    }

    public void setRefreshVersionList(List<String> refreshVersionList) {
        this.refreshVersionList = refreshVersionList;
    }
}
