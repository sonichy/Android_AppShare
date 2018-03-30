package com.hty.appshare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ListView listView;
    AppInfoAdapter adapter;
    EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        PackageManager PM = getPackageManager();
        List<PackageInfo> packages = PM.getInstalledPackages(0);
        final List<AppInfo> listAppInfo = new ArrayList<AppInfo>();
        for (PackageInfo packageInfo : packages) {
            Drawable icon = packageInfo.applicationInfo.loadIcon(PM);   // 获得应用的图标
            String appLabel = (String) packageInfo.applicationInfo.loadLabel(PM);   // 获得应用的Label
            String pkgName = packageInfo.packageName;   // 获得应用的包名
            String sourceDir = packageInfo.applicationInfo.sourceDir; // 获得应用的路径
            File file = new File(sourceDir);    //获取到应用安装包大小
            String pkgSize = Formatter.formatFileSize(this,file.length());
            AppInfo appInfo = new AppInfo();
            appInfo.setAppIcon(icon);
            appInfo.setAppLabel(appLabel);
            appInfo.setPkgName(pkgName);
            appInfo.setSourceDir(sourceDir);
            appInfo.setPkgSize(pkgSize);
            listAppInfo.add(appInfo);
        }

        adapter = new AppInfoAdapter(this, listAppInfo);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);    // 开启过滤

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String pkgName = ((TextView)view.findViewById(R.id.textViewPkgName)).getText().toString();
                Intent intent = new Intent();
                intent = getPackageManager().getLaunchIntentForPackage(pkgName);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView adapter,View view,int position,long id) {
                String appLabel = ((TextView)view.findViewById(R.id.textViewAppLabel)).getText().toString();
                String sourceDir = ((TextView)view.findViewById(R.id.textViewSourceDir)).getText().toString();
                Log.e("souceDir", sourceDir);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(sourceDir)));
                intent.setType("*/*");
                startActivity(Intent.createChooser(intent, "分享 " + appLabel));
                return true;
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                //listView.setFocusable(true);
                //listView.setFocusableInTouchMode(true);
                listView.requestFocus();
                InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                IMM.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
                return false;
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (TextUtils.isEmpty(charSequence.toString().trim()))
                    listView.clearTextFilter(); //搜索文本为空时，清除ListView的过滤
                else
                    //listView.setFilterText(charSequence.toString().trim()); //设置过滤关键字，有浮窗且不消失，改用下面的方法
                    adapter.getFilter().filter(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "退出");
        menu.add(0, 1, 1, "关于");
        menu.add(0, 2, 2, "更新日志");
        //menu.add(0, 3, 3, "列表");
        //menu.add(0, 4, 4, "折线图");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id) {
            case 0:
                finish();
                break;
            case 1:
                new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("海天鹰应用分享 V1.2")
                        .setMessage("获取应用列表，分享发送安装包。\n作者：黄颖\nQQ: 84429027\n\n参考：\n获取应用信息：https://www.cnblogs.com/jiuyi/p/5983304.html，http://blog.csdn.net/lishuangling21/article/details/50789715\nlistView过滤：http://blog.csdn.net/zml_2015/article/details/52082174")
                        .setPositiveButton("确定", null).show();
                break;
            case 2:
                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("更新日志")
                        .setMessage(
                                "V1.2 (2018-01-23)\n修复过滤后分享APP路径不对的问题。\n改动：点击启动应用，长按分享应用。\n\nV1.1 (2018-01-09)\n增加过滤功能。\n\nV1.0 (2018-01-01)\n获取应用列表，分享发送安装包。")
                        .setPositiveButton("确定", null).show();
                break;
            case 3:
                //startActivity(new Intent(MainActivity.this, BatteryRecord.class));
                break;
            case 4:
//                Intent intent = new Intent(MainActivity.this, BatteryCanvas.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                break;
        }
        return true;
    }

}
