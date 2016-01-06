package com.example.wilsond.moveapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dalvik.system.PathClassLoader;

//TO DO
//** limit the place character number
//** fix select one item when created
public class SelectPlaceActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private ArrayList<Place> places;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_place);

        // Set header
        GenericHeaderLayout headerLayout = new GenericHeaderLayout(findViewById(R.id.header_layout),this);
        headerLayout.setTitle(R.string.activity_select_place_title);
        headerLayout.setNavigationDrawer((DrawerLayout) findViewById(R.id.drawer_layout), Gravity.LEFT);

        // Set lateral menu
        LateralMenuLayout lateralMenuLayout = new LateralMenuLayout(findViewById(R.id.lateral_menu),this);
        lateralMenuLayout.highlightItem(R.id.lateral_menu_task_make_order);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        // Set listview workflow
        final ArrayList<Place> places = new ArrayList<>();
        this.places = places;

        places.add(new Place("Calle 34 S # 45 - 98 apto 301 int 1"));
        places.add(new Place("Avenida 7A # 303 - 45 int 4 apto 405"));
        places.add(new Place("Cra 6a # 56 - 89 int 23 apto 209"));
        places.add(new Place("Cra 6a # 56 - 89 int 23 apto 209"));
        places.add(new Place("Cra 6a # 56 - 89 int 23 apto 209"));
        places.add(new Place("Cra 6a # 56 - 89 int 23 apto 209"));
        places.add(new Place("Cra 6a # 56 - 89 int 23 apto 209"));

        final ListView listView = (ListView) findViewById(R.id.recent_places_container);
        final PlaceArrayAdapter adapter = new PlaceArrayAdapter(this,places);
        this.adapter = adapter;
        listView.setAdapter(adapter);

        TextView selectedPlace = (TextView) findViewById(R.id.selected_place);

        listView.setOnItemClickListener(new CustomOnItemCLickListener(selectedPlace, (TextView) listView.getAdapter().getView(0, null, null), adapter));



        // Set button create another address
        Button newPlaceButton = (Button) findViewById(R.id.new_place_button);
        newPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
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

    // Create the dialog to new place
    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingresa la direccion de envio");
        final EditText editText = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        builder.setView(editText);
        builder.setCancelable(true);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                places.add(new Place(editText.getText().toString()));
                adapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private class PlaceArrayAdapter extends BaseAdapter {
        private ArrayList<Place> places;
        private Context context;
        private int selected;

        public PlaceArrayAdapter(Context context, ArrayList<Place> places) {
            this.context = context;
            this.places = places;
            this.selected = -1;
        }

        @Override
        public int getCount() {
            return places.size();
        }

        @Override
        public Object getItem(int position) {
            return places.get(position);
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
                row = inflater.inflate(R.layout.selectable_place_layout, parent, false);
            } else {
                row = convertView;
            }
            if (position == selected) {
                row.setBackgroundResource(R.color.white);
            } else {
                row.setBackgroundResource(R.drawable.underline);
            }
            TextView textView = (TextView) row;
            textView.setText(places.get(position).toString());

            return row;
        }
        public void setSelected(int pos){
            this.selected = pos;
            View selectedView = getView(pos,null,null);
            selectedView.setBackgroundResource(R.color.white);
        }
    }

    private class CustomOnItemCLickListener implements AdapterView.OnItemClickListener {
        TextView selected;
        TextView bigDisplay;
        PlaceArrayAdapter adapter;


        public CustomOnItemCLickListener(TextView bigDisplay, TextView selected, PlaceArrayAdapter adapter){
            this.bigDisplay = bigDisplay;
            this.selected = selected;
            if (selected != null) {
                bigDisplay.setText(selected.getText());
            }
            this.adapter = adapter;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView nSelected = (TextView) view;
            if (selected != null) selected.setBackgroundResource(R.drawable.underline);
            bigDisplay.setText(nSelected.getText());
            nSelected.setBackgroundResource(R.color.white);
            selected = nSelected;
            adapter.setSelected(position);
        }

    }

}
