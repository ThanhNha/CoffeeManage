package com.example.huynhthanhnha.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhthanhnha.myapplication.Login;
import com.example.huynhthanhnha.myapplication.R;
import com.example.huynhthanhnha.myapplication.adapter.ListGroupAdapter;
import com.example.huynhthanhnha.myapplication.adapter.ListPriceAdapter;
import com.example.huynhthanhnha.myapplication.form.DatabaseConnection;
import com.example.huynhthanhnha.myapplication.form.GroupProduct;
import com.example.huynhthanhnha.myapplication.form.ListPrice;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Huynh Thanh Nha on 05-Dec-15.
 */
public class ShowPriceActivity extends Activity {
    ListView lvPrice;
    EditText etName;
    EditText addName;
    TextView tvUserName;
    TextView tvAuth;
    DatabaseConnection conn = new DatabaseConnection();
    List<ListPrice> listPrices = new ArrayList<ListPrice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_price);

        lvPrice = (ListView) findViewById(R.id.lvPrice);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvAuth = (TextView) findViewById(R.id.tvAuth);
        tvUserName.setText("Tên: " + Login.getUser().getName().toString());
        tvAuth.setText("Cấp: " + Login.getUser().getPer().getDetails().toString());

        conn.Open();
        listPrices = conn.getAllListPrice();
        conn.Close();

        final ListPriceAdapter priceAdapter = new ListPriceAdapter(ShowPriceActivity.this, listPrices);
        lvPrice.setAdapter(priceAdapter);
        lvPrice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ListPrice price = (ListPrice) parent.getItemAtPosition(position); // get Item in position

                // Dialog show list edit, delete,...
//                PopupMenu popupMenu = new PopupMenu(ShowPriceActivity.this, view);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.itemAddGroup:
//                                // Dialog update price
//                                final AlertDialog.Builder builder = new AlertDialog.Builder(ShowPriceActivity.this);
//                                // Get the layout inflater
//                                LayoutInflater inflater = ShowPriceActivity.this.getLayoutInflater();
//
//                                // Inflate and set the layout for the dialog
//                                // Pass null as the parent view because its going in the dialog layout
//                                final View dialogView = inflater.inflate(R.layout.dialog_add_group, null);
//                                addName = (EditText) dialogView.findViewById(R.id.addNameGoup);
//                                builder.setView(dialogView)
//                                        .setTitle("Thêm nhóm thức uống")
//                                                // Add action buttons
//                                        .setPositiveButton("Lưu lại", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                String nameGroup = addName.getText().toString();
//                                                if (nameGroup.equals("")) {
//                                                    Toast.makeText(ShowPriceActivity.this, "Trường tên không được rỗng!!", Toast.LENGTH_SHORT).show();
//                                                } else {
//                                                    conn.Open();
//                                                    conn.insertGroup(nameGroup);
//                                                    conn.Close();
//                                                    finish();
//                                                    startActivity(getIntent());
//                                                }
//                                            }
//                                        })
//                                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                //LoginDialogFragment.this.getDialog().cancel();
//                                            }
//                                        });
//                                builder.create();
//                                builder.show();
//                                return true;
//                            case R.id.itemDeleteGroup:
//
//                                return true;
//                            case R.id.itemEditGroup:
//                                // Dialog edit name
//                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowPriceActivity.this);
//                                // Get the layout inflater
//                                LayoutInflater inflater1 = ShowPriceActivity.this.getLayoutInflater();
//
//                                // Inflate and set the layout for the dialog
//                                // Pass null as the parent view because its going in the dialog layout
//                                final View dialogView1 = inflater1.inflate(R.layout.dialog_edit_group, null);
//                                builder1.setView(dialogView1)
//                                        .setTitle("Cập nhật tên sản phẩm")
//                                                // Add action buttons
//                                        .setPositiveButton("Lưu lại", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int id) {
//
//                                                etName = (EditText) dialogView1.findViewById(R.id.editNameGroup);
//                                                String newName = etName.getText().toString();
//
//                                                conn.Open();
//                                                //conn.UpdateNameGroup(price.get(), newName);
//                                                conn.Close();
//                                                finish();
//                                                startActivity(getIntent());
//                                            }
//                                        })
//                                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                //LoginDialogFragment.this.getDialog().cancel();
//                                            }
//                                        });
//                                builder1.create();
//                                builder1.show();
//                                return true;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.inflate(R.menu.popup_menu_price);
//                popupMenu.show();
            }
        });
    }
}