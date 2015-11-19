package com.example.huynhthanhnha.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huynhthanhnha.myapplication.form.Product;
import com.example.huynhthanhnha.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Thanh Nha on 18-Nov-15.
 */
public class ListProductAdapter extends BaseAdapter {
    List<Product> listProduct = new ArrayList<Product>();
    Activity context;

    public ListProductAdapter(Activity context, List<Product> listProduct) {
        this.listProduct = listProduct;
        this.context = context;
    }
    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product table = listProduct.get(position);
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.list_item_product, null);

        /*get image in database and set to view*/
        /*ImageView imageView = (ImageView) rowView.findViewById(R.id.imageTable);
        imageView.setImageResource(table.getImage());*/
        TextView textView = (TextView) rowView.findViewById(R.id.tvProductName);
        textView.setText(String.valueOf(table.getProductName()));

        return rowView;
    }
}