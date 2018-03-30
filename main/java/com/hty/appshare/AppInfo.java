package com.hty.appshare;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private Drawable appIcon ;  //应用程序图像
    private String appLabel;    //应用程序标签
    private String pkgName ;    //应用程序所对应的包名
    private String sourceDir;   //安装包路径
    private String pkgSize;        //安装包大小

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

}
