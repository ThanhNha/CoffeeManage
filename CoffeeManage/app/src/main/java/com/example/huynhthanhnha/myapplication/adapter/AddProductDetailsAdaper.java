package com.example.huynhthanhnha.myapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhthanhnha.myapplication.R;
import com.example.huynhthanhnha.myapplication.activity.ListTableDetails;
import com.example.huynhthanhnha.myapplication.form.DatabaseConnection;
import com.example.huynhthanhnha.myapplication.form.Product;
import com.example.huynhthanhnha.myapplication.form.ProductDetails;
import com.example.huynhthanhnha.myapplication.form.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NguyenThanh on 22/11/2015.
 */
public class AddProductDetailsAdaper extends BaseAdapter {
    List<Product> listProductGroup = new ArrayList<Product>();
    DatabaseConnection conn = new DatabaseConnection();

    int TableID = 1;
    EditText unitSales;
    Activity context;
    RelativeLayout relativeDetails;
    RelativeLayout relativeAdd;

    public AddProductDetailsAdaper(Activity context, List<Product> listProductGroup, int TableID, RelativeLayout relativeDetails, RelativeLayout relativeAdd) {
        this.listProductGroup = listProductGroup;
        this.context = context;
        this.TableID = TableID;
        this.relativeAdd = relativeAdd;
        this.relativeDetails = relativeDetails;
    }

    @Override
    public int getCount() {
        return listProductGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return listProductGroup.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = listProductGroup.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.add_product_item, null);

        TextView productName = (TextView) rowView.findViewById(R.id.tvProductName);
        productName.setText(String.valueOf(product.getProductName()));

        //When user click each item
        ImageView imgAddProduct = (ImageView) rowView.findViewById(R.id.imgAddProduct);
        imgAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conn.Open();
                unitSales = (EditText) rowView.findViewById(R.id.editSoluong);
                if (unitSales.getText().length() != 0 && Integer.valueOf(unitSales.getText().toString()) != 0) {
                    conn.InsertProductForBill(product, Integer.valueOf(unitSales.getText().toString()), TableID);
                }
                conn.Close();

                relativeAdd.setVisibility(View.GONE);
                relativeDetails.setVisibility(View.VISIBLE);
               //createListProduct();
            }
        });

        return rowView;
    }
}