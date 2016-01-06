package com.example.wilsond.moveapplication;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.wilsond.moveapplication.ScrollNumberView.*;


public class SearchItemActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        //Set header
        GenericHeaderLayout headerLayout = new GenericHeaderLayout(findViewById(R.id.header_layout),this);
        headerLayout.setTitle(R.string.activity_search_item_title);
        headerLayout.setNavigationDrawer((DrawerLayout) findViewById(R.id.drawer_layout), Gravity.LEFT);

        //Set lateral menu
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        LateralMenuLayout lateralMenuLayout = new LateralMenuLayout(findViewById(R.id.lateral_menu),this);
        lateralMenuLayout.highlightItem(R.id.lateral_menu_task_make_order);

        // Set item list view
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("", 1));


        ListView listView = (ListView) findViewById(R.id.item_list_view);
        ArrayItemAdapter adapter = new ArrayItemAdapter(this,items);

        listView.setAdapter(adapter);

        //Set make order button
        Button makeOrderButton = (Button) findViewById(R.id.make_order_button);
        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectPlace = new Intent(getApplicationContext(),SelectPlaceActivity.class);
                startActivity(selectPlace);
                finish();
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
        }
    }


    //Create a new ListItem
    private class CustomOnEditorChange implements TextView.OnEditorActionListener {
        ArrayItemAdapter adapter;
        boolean active;
        private Item item;
        public CustomOnEditorChange(ArrayItemAdapter adapter, Item item){
                this.adapter = adapter;
                activate();
                this.item = item;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (active && (actionId == EditorInfo.IME_ACTION_NEXT || (event != null && event.getAction() == KeyEvent.ACTION_DOWN))) {
                item.setName(v.getText().toString());
                adapter.addNewItem();
                desactive();
                return true;
            }


            return false;
        }

        public void activate(){
            this.active = true;
        }

        public void desactive() {
            this.active = false;
        }
    }

    // Save data in classes
    private class CustomTextWatcher implements View.OnFocusChangeListener{
        Item item;
        CustomTextWatcher(Item item) {
            this.item = item;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                item.setName(((TextView) v).getText().toString());
            }
        }
    }
    // Save data in classes
    private class CustomNumberChangeListener extends ScrollNumberView.OnNumberChangeListener{
        Item item;
        CustomNumberChangeListener(Item item) {
            this.item = item;
        }
        @Override
        public void onNumberChange(int number) {
            item.setAmount(number);
        }
    }

    // add functions
    private class ArrayItemAdapter extends BaseAdapter {
        ArrayList<Item> items;
        Context context;
        ListView listView;

        public ArrayItemAdapter (Context context, ArrayList<Item> items){
            this.context = context;
            this.items = items;
        }
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        public int getPosition(Item item) {
            return items.indexOf(item);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = null;
            Item item = items.get(position);
            this.listView = (ListView) parent;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                row = inflater.inflate(R.layout.item_container_activity_search_item, parent, false);
            } else {
                row = convertView;
            }
            EditText editText = (EditText) row.findViewById(R.id.default_edit_text);
            ScrollNumberView scrollNumber = (ScrollNumberView) row.findViewById(R.id.default_scroll_number);
            editText.setText(item.getName());
            if (position == getCount()-1){
                editText.setOnEditorActionListener(new CustomOnEditorChange(this,item));
            }

            //Set swipe remove
            row.setOnTouchListener(new CustomOnTouchListener( this,items.get(position) ) );

            editText.setOnFocusChangeListener(new CustomTextWatcher(item));
            editText.setOnTouchListener(new CustomOnTouchListener( this,items.get(position) ));
            scrollNumber.setNumber(item.getAmount());
            scrollNumber.setOnNumberChangeListener(new CustomNumberChangeListener(item));
            return row;
        }
        public void deleteItem(int nPosition) {
            final int position = nPosition;
            items.remove(position);
            notifyDataSetChanged();
            listView.post(new Runnable() {
                @Override
                public void run() {
                    int nPos = position;
                    if (position >= listView.getChildCount()) nPos -= 1;
                    listView.setSelection(nPos);
                }
            });
        }

        public void addNewItem(){
            items.add(new Item("", 1));
            notifyDataSetChanged();
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(getCount() - 1);


                    /*final int height = listView.getChildAt(listView.getFirstVisiblePosition()).getHeight();
                    new CountDownTimer(1000, 20) {

                        public void onTick(long millisUntilFinished) {

                            listView.scrollTo(0, (int) ((height - millisUntilFinished*height/1000) - height ));
                        }

                        public void onFinish() {
                        }

                    }.start();*/
                }
            });
        }
    }

    // deletion
    private class CustomOnTouchListener implements OnTouchListener {
        private float initialx;
        private float minx = 60;
        private ArrayItemAdapter array;
        private Item item;
        public CustomOnTouchListener(ArrayItemAdapter array,Item item){
            this.array = array;
            this.item = item;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initialx = event.getRawX();
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float xDelta = event.getRawX() - initialx;
                if (v instanceof EditText) v = (View) v.getParent();
                if (-xDelta > 0 && array.getCount() != 1) v.setTranslationX(xDelta);
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
                float xDelta = initialx - event.getRawX();
                if (xDelta > minx && array.getCount()!=1 ) {
                    if (v instanceof EditText) v = (View) v.getParent();
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sweep_left);
                    v.startAnimation(animation);
                    final View vw = v;
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            array.deleteItem(array.getPosition(item));
                            vw.setTranslationX(0);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                } else {
                    if (v instanceof EditText) v = (View) v.getParent();
                    v.setTranslationX(0);
                    v.findViewById(R.id.default_edit_text).requestFocus();
                }
                return false;
            }
            return false;
        }
    }

}

