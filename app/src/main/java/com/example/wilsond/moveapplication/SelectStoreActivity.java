package com.example.wilsond.moveapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.TextView;

import java.util.ArrayList;

public class SelectStoreActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_store);

        // Set header
        GenericHeaderLayout headerLayout = new GenericHeaderLayout(findViewById(R.id.header_layout),this);
        headerLayout.setTitle(R.string.activity_select_store_title);
        headerLayout.setNavigationDrawer((DrawerLayout) findViewById(R.id.drawer_layout), Gravity.LEFT);

        // Set lateral menu
        LateralMenuLayout lateralMenuLayout = new LateralMenuLayout(findViewById(R.id.lateral_menu),this);
        lateralMenuLayout.highlightItem(R.id.lateral_menu_task_make_order);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        // Set listview workflow

        final ArrayList<Offer> offers = new ArrayList<>();

        offers.add(new Offer(2200,30));


        final ListView listView = (ListView) findViewById(R.id.store_list_container);
        final ArrayOffersAdapter adapter = new ArrayOffersAdapter(offers,this);
        listView.setAdapter(adapter);

        //listView.setOnItemClickListener(new CustomOnItemCLickListener((TextView) findViewById(R.id.selected_place), (TextView) listView.getAdapter().getView(0, null, null)));
        /*ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buscando Tiendas Cercanas...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();*/
    }

    //Close lateral menu
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            finish();
        }
    }



    private class ArrayOffersAdapter extends BaseAdapter {
        private ArrayList<Offer> offers;
        private Context context;

        ArrayOffersAdapter(ArrayList<Offer> offers, Context context) {
            this.offers = offers;
            this.context = context;
        }

        @Override
        public int getCount() {
            return offers.size();
        }

        @Override
        public Object getItem(int position) {
            return offers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = null;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                row = inflater.inflate(R.layout.selectable_store_layout, parent, false);
            } else {
                row = convertView;
            }
            TextView priceTextView = (TextView) row.findViewById(R.id.price);
            priceTextView.setText(offers.get(position).priceToString());
            TextView timeTextView = (TextView) row.findViewById(R.id.time);
            timeTextView.setText(offers.get(position).timeToString());

            return row;
        }
    }
}
