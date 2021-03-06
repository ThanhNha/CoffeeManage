package com.example.huynhthanhnha.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huynhthanhnha.myapplication.Login;
import com.example.huynhthanhnha.myapplication.adapter.ListGroupAdapter;
import com.example.huynhthanhnha.myapplication.form.DatabaseConnection;
import com.example.huynhthanhnha.myapplication.form.GroupProduct;
import com.example.huynhthanhnha.myapplication.form.ListPrice;
import com.example.huynhthanhnha.myapplication.form.Product;
import com.example.huynhthanhnha.myapplication.R;
import com.example.huynhthanhnha.myapplication.fragments.ListProductGroupFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Huynh Thanh Nha on 18-Nov-15.
 */
public class ListProductActivity extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    Fragment fragment = null;
    ListView mainlist;
    TextView tvUserName;
    TextView tvAuth;
    ArrayList<String> formain = new ArrayList<String>();
    DatabaseConnection conn = new DatabaseConnection();
    List<GroupProduct> listGroupProduct = new ArrayList<GroupProduct>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvAuth = (TextView) findViewById(R.id.tvAuth);
        tvUserName.setText("Tên: " + Login.getUser().getName().toString());
        tvAuth.setText("Cấp: " + Login.getUser().getPer().getDetails().toString());

        conn.Open();
        listGroupProduct = conn.getListGroupProduct();
        conn.Close();

        mainlist = (ListView) findViewById(R.id.left_drawer_child);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerListener(mDrawerListener);

        ListGroupAdapter adapter = new ListGroupAdapter(ListProductActivity.this, listGroupProduct);
        mainlist.setAdapter(adapter);
        mDrawerLayout.openDrawer(mainlist);
        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String getGroup = listGroupProduct.get(arg2).getGroupProductName();
                // Set param into bundle
                Bundle bundle = new Bundle();
                bundle.putString("groupActivity", getGroup);

                //System.out.println("GROUP IN getGROUP: " + getGroup);
                fragment = new ListProductGroupFragment();
                // set param into fragment
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                mDrawerLayout.closeDrawer(mainlist);
            }
        });

    }

    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerStateChanged(int status) {

        }

        @Override
        public void onDrawerSlide(View view, float slideArg) {

        }

        @Override
        public void onDrawerOpened(View view) {
        }

        @Override
        public void onDrawerClosed(View view) {
        }
    };

}
