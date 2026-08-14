package com.example.woodland.AdapterClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woodland.POJOClass.getAllProductPOJOClass;
import com.example.woodland.R;

import java.util.List;

public class getAllProductAdapterClass extends BaseAdapter
{

    List<getAllProductPOJOClass> getAllProductPOJOClass;

    Activity activity;

    public getAllProductAdapterClass(List<getAllProductPOJOClass> getAllProductPOJOClass, Activity activity)
    {
        this.getAllProductPOJOClass = getAllProductPOJOClass;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return getAllProductPOJOClass.size();
    }

    @Override
    public Object getItem(int position) {
        return getAllProductPOJOClass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
        {
            holder =new ViewHolder();
            convertView = inflater.inflate(R.layout.product_list,null);

            holder.ivProductImage = convertView.findViewById(R.id.ivProductImage);
            holder.tvProductListName = convertView.findViewById(R.id.tvProductListName);
            holder.tvProductListCategory = convertView.findViewById(R.id.tvProductListCategory);
            holder.tvProductListPrise = convertView.findViewById(R.id.tvProductListPrise);

            convertView.setTag(holder);
        }
        else
        {
            holder =(ViewHolder) convertView.getTag();
        }

        final getAllProductPOJOClass obj = getAllProductPOJOClass.get(position);
        holder.tvProductListName.setText(obj.getProduct_name());
        holder.tvProductListCategory.setText(obj.getProduct_category());
        holder.tvProductListPrise.setText(obj.getProduct_price());

        return convertView;
    }
    class ViewHolder
    {
        ImageView ivProductImage;

        TextView tvProductListPrise,tvProductListCategory,tvProductListName;

    }
}
