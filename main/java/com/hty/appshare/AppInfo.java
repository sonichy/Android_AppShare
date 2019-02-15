package com.hty.appshare;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private Drawable appIcon;       //应用图像
    private String appLabel;        //应用标签
    private String pkgName ;        //应用包名
    private String sourceDir;       //包路径
    private String pkgSize;         //包大小
    private long lastUpdateTime;  //应用更新时间

    public AppInfo() {

    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName ;
    }

    public String getPkgName(){
        return pkgName ;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setPkgSize(String pkgSize) {
        this.pkgSize = pkgSize;
    }

    public String getPkgSize() {
        return pkgSize;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

}