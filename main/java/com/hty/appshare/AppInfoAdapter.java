package com.hty.appshare;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AppInfoAdapter extends BaseAdapter implements Filterable{

    Context context;
    List<AppInfo> data,data0;
    MyFilter mFilter;

    public AppInfoAdapter(Context context, List<AppInfo> data) {
        this.context = context;
        this.data = data;
        data0 = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertview == null || convertview.getTag() == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_app, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            view = convertview ;
            holder = (ViewHolder) convertview.getTag() ;
        }
        AppInfo appInfo = (AppInfo) getItem(position);
        holder.appIcon.setImageDrawable(appInfo.getAppIcon());
        holder.textViewAppLabel.setText(appInfo.getAppLabel());
        holder.textViewPkgName.setText(appInfo.getPkgName());
        holder.textViewPkgSize.setText(appInfo.getPkgSize());
        holder.textViewSourceDir.setText(appInfo.getSourceDir());
        return view;
    }

    class ViewHolder {
        ImageView appIcon;
        TextView textViewAppLabel;
        TextView textViewPkgName;
        TextView textViewPkgSize;
        TextView textViewSourceDir;

        public ViewHolder(View view) {
            this.appIcon = (ImageView) view.findViewById(R.id.imageView);
            this.textViewAppLabel = (TextView) view.findViewById(R.id.textViewAppLabel);
            this.textViewPkgName = (TextView) view.findViewById(R.id.textViewPkgName);
            this.textViewPkgSize = (TextView) view.findViewById(R.id.textViewPkgSize);
            this.textViewSourceDir = (TextView) view.findViewById(R.id.textViewSourceDir);
        }
    }

    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    //我们需要定义一个过滤器的类来定义过滤规则
    class MyFilter extends Filter{
        // 在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            //Log.e("Filter:", charSequence.toString());
            FilterResults result = new FilterResults();
            List<AppInfo> list;
            if (TextUtils.isEmpty(charSequence)){   //当过滤的关键字为空的时候，我们则显示所有的数据
                list  = data0;
            }else { //否则把符合条件的数据对象添加到集合中
                list = new ArrayList<AppInfo>();
                for (AppInfo appInfo:data0){
                    if (appInfo.getPkgName().contains(charSequence) || appInfo.getAppLabel().contains(charSequence)){
                        //Log.e("Filter:", appInfo.getPkgName());
                        list.add(appInfo);
                    }
                }
            }
            result.values = list;
            result.count = list.size();
            return result;
        }

        // 在publishResults方法中通知适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            data = (List<AppInfo>)filterResults.values;
            Log.e("publishResults:", filterResults.count + "");
            if (filterResults.count>0) {
                notifyDataSetChanged(); // 通知数据发生了改变
                Log.e("publishResults", "notifyDataSetChanged");
            }else {
                notifyDataSetInvalidated(); // 通知数据失效
                Log.e("publishResults", "notifyDataSetInvalidated");
            }
        }
    }

}