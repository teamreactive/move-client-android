package com.example.wilsond.moveapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

//TO DO
//** limit the place character number
//** fix select one item when created
public class MyOrdersActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private ArrayList<Place> places;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_orders);

        // Set header
        GenericHeaderLayout headerLayout = new GenericHeaderLayout(findViewById(R.id.header_layout),this);
        headerLayout.setTitle(R.string.activity_my_orders);
        headerLayout.setNavigationDrawer((DrawerLayout) findViewById(R.id.drawer_layout), Gravity.LEFT);

        // Set lateral menu
        LateralMenuLayout lateralMenuLayout = new LateralMenuLayout(findViewById(R.id.lateral_menu),this);
        lateralMenuLayout.highlightItem(R.id.lateral_menu_task_my_orders);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // No orders
        if (0 == 1) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup mainContent = (ViewGroup) findViewById(R.id.main_content);
            View row  = inflater.inflate(R.layout.activity_my_orders_no_orders,mainContent, false);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.BELOW,R.id.header_layout);
            row.setLayoutParams(lp);
            mainContent.addView(row);
        } // Yes orders
        else {
            // Set View
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup mainContent = (ViewGroup) findViewById(R.id.main_content);
            View row  = inflater.inflate(R.layout.activity_my_orders_content, mainContent, false);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.BELOW,R.id.header_layout);
            row.setLayoutParams(lp);
            mainContent.addView(row);

            // Array Workflow
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(new Order(new Date(),new Place("Calle Mentira :("), 1200));

            ArrayOrdersAdapter adapter = new ArrayOrdersAdapter(this, orders);
            ListView listView  = (ListView) row;
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Order order = (Order) parent.getItemAtPosition(position);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MyOrdersActivity.this);
                    dialog.setMessage(order.place.toString());
                    dialog.show();
                }
            });


        }



        //final ListView listView = (ListView) findViewById(R.id.recent_places_container);
        //final PlaceArrayAdapter adapter = new PlaceArrayAdapter(this,places);
        //this.adapter = adapter;
        //listView.setAdapter(adapter);

        //listView.setOnItemClickListener(new CustomOnItemCLickListener((TextView) findViewById(R.id.selected_place), (TextView) listView.getAdapter().getView(0, null, null), adapter));



    }

    private class ArrayOrdersAdapter extends BaseAdapter {
        ArrayList<Order> orders;
        Context context;
        public ArrayOrdersAdapter(Context context, ArrayList<Order> orders){
            this.context = context;
            this.orders = orders;

        }
        @Override
        public int getCount() {
            return orders.size();
        }

        @Override
        public Object getItem(int position) {
            return orders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Order order = orders.get(position);
            View row = null;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                row = inflater.inflate(R.layout.order_list_item_layout, parent, false);
            } else {
                row = convertView;
            }
            ((TextView) row.findViewById(R.id.date)).setText(order.dateToString());
            ((TextView) row.findViewById(R.id.items_number)).setText(order.itemsContToString());
            return row;
        }
    }

    //Close lateral menu
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            finish();
            Intent searchItemAct = new Intent(this,SearchItemActivity.class);
            startActivity(searchItemAct);

        }
    }






}
