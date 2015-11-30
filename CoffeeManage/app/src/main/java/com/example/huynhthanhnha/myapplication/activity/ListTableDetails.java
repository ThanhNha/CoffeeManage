package com.example.huynhthanhnha.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.huynhthanhnha.myapplication.R;
//import com.example.huynhthanhnha.myapplication.adapter.AddProductDetailsAdaper;
import com.example.huynhthanhnha.myapplication.adapter.AddProductDetailsAdaper;
import com.example.huynhthanhnha.myapplication.form.DatabaseConnection;
import com.example.huynhthanhnha.myapplication.form.Product;
import com.example.huynhthanhnha.myapplication.form.ProductDetails;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by NguyenThanh on 19/11/2015.
 */
public class ListTableDetails extends Activity {
    DatabaseConnection conn = new DatabaseConnection();
    ListView listView;
    List<ProductDetails> listProductDetails = new ArrayList<ProductDetails>();
    List<Product> listProductGroup = new ArrayList<Product>();
    RelativeLayout relativeDetails;
    RelativeLayout relativeAdd;
    EditText inputSearch;
    int IdTable;
    Button btnMonmoi;
    Button btnThanhToan;
    long Total;
    ListProductDetailsAdapter adapter;
    AddProductDetailsAdaper adapterAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_details);
        IdTable = getIntent().getExtras().getInt("IdTable");

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        TextView numTable = (TextView) findViewById(R.id.tableNumber);
        numTable.setText(String.valueOf(IdTable));

        TextView numTable1 = (TextView) findViewById(R.id.tableNumber1);
        numTable1.setText("Bàn số " + String.valueOf(IdTable));

        listView = (ListView) findViewById(R.id.listProductDetails);
        relativeDetails = (RelativeLayout) findViewById(R.id.relativeProductDetails);
        relativeAdd = (RelativeLayout) findViewById(R.id.relativeAddProduct);

        createListProductGroup();
        createListProduct();

        btnMonmoi = (Button) findViewById(R.id.btnMonmoi);
        btnMonmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeAdd.setVisibility(View.VISIBLE);
                relativeDetails.setVisibility(View.GONE);
            }
        });


        btnThanhToan = (Button) findViewById(R.id.btnThanhtoan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListTableDetails.this);
                // Get the layout inflater
                LayoutInflater inflater = ListTableDetails.this.getLayoutInflater();
                TextView title = new TextView(ListTableDetails.this);
                title.setText("Bạn muốn thanh toán?");
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(10, 10, 10, 10);
                title.setHeight(60);
                title.setTextSize(15);
                title.setTextColor(Color.BLUE);

                builder.setView(inflater.inflate(R.layout.dialog_confirm, null))
                        .setCustomTitle(title)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                conn.Open();
                                conn.updateStateForBill(IdTable);
                                conn.Close();
                                createListProduct();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        ////////////////////////////////////////////
        //Show popup menu for each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Creating the instance of PopupMenu
                final ProductDetails productDetails = (ProductDetails) parent.getItemAtPosition(position);
                PopupMenu popup = new PopupMenu(ListTableDetails.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_table_details, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(ListTableDetails.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.popupDelete:
                                conn.Open();
                                //conn.getProductDetails();
                                conn.deleteProductDetail(productDetails);
                                conn.Close();
                                break;
                            case R.id.popupEdit:
                                Toast.makeText(ListTableDetails.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        createListProduct();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createListProductGroup();
                if (!s.toString().equals("")) {
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchItem(String textToSearch) {
        Iterator<Product> it = listProductGroup.iterator();
        while (it.hasNext()) {
            Product p = it.next();
            if(!p.getProductName().contains(textToSearch)) {
                it.remove();
            }
        }
        adapterAdd.notifyDataSetChanged();
    }

    public void createListProduct() {
        conn.Open();
        listProductDetails = conn.getListProductOfTable(IdTable);
        Total = conn.getPriceTotalOfBill(listProductDetails);
        conn.Close();
        adapter = new ListProductDetailsAdapter(this, listProductDetails);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        TextView countProduct = (TextView) findViewById(R.id.textTongsomon);
        countProduct.setText("Tổng số món: " + String.valueOf(listProductDetails.size()));
        //Get price total of bill
        TextView totalPrice = (TextView) findViewById(R.id.tvPriceTotal);
        totalPrice.setText(String.valueOf(Total) + "đ");
    }

    public void createListProductGroup() {
        ListView listProductDetailsForAdd = (ListView) findViewById(R.id.listProductDetailsForAdd);
        conn.Open();
        listProductGroup = conn.getListProduct();
        conn.Close();

        adapterAdd = new AddProductDetailsAdaper(this, listProductGroup, IdTable, relativeDetails, relativeAdd);
        listProductDetailsForAdd.setAdapter(adapterAdd);
        adapterAdd.notifyDataSetChanged();
    }

    public class ListProductDetailsAdapter extends BaseAdapter{
        List<ProductDetails> listProductDetail = new ArrayList<ProductDetails>();
        Activity context;

        public ListProductDetailsAdapter(Activity context, List<ProductDetails> listProductDetail) {
            this.listProductDetail = listProductDetail;
            this.context = context;
        }
        @Override
        public int getCount() {
            return listProductDetail.size();
        }

        @Override
        public Object getItem(int position) {
            return listProductDetail.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductDetails productDetails = listProductDetail.get(position);
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.table_item_details, null);

            TextView productName = (TextView) rowView.findViewById(R.id.tvProductName);
            productName.setText(String.valueOf(position + 1) + ". " + String.valueOf(productDetails.getProduct().getProductName()));

            TextView unitSales = (TextView) rowView.findViewById(R.id.tvUnitSales);
            unitSales.setText(String.valueOf(productDetails.getUnitSales()));

            TextView numberSales = (TextView) rowView.findViewById(R.id.tvDonviban);
            numberSales.setText(productDetails.getProduct().getUnit());

            TextView price = (TextView) rowView.findViewById(R.id.tvPriceProduct);
            conn.Open();
            long pricet = conn.getPriceOfProduct(productDetails.getProduct().getProductId());
            price.setText(String.valueOf(pricet) + "đ X");
            conn.Close();

            return rowView;
        }
    }


}
