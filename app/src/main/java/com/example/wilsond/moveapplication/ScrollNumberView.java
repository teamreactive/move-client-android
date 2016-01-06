package com.example.wilsond.moveapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by WILSOND on 12/16/15.
 */
public class ScrollNumberView extends RelativeLayout {
    private int currentNumber;
    private int maxNumber;
    private int minNumber;
    private int arrowUp;
    private int arrowDown;
    private OnNumberChangeListener numberChangeListener = null;
    private ScrollOnTouchListener touchListener;

    public void setOnNumberChangeListener(OnNumberChangeListener numberChangeListener){
        this.numberChangeListener = numberChangeListener;
    }

    // this view adds three views in it, representing numbers, one below the other,
    // it starts with the middle view centered at the container, one above and one below
    // they all move the same distance
    // when moving... and the lower view get to the position at the center of the container
    // all the views increment its number in one, and the initial position is set.
    public ScrollNumberView(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ScrollNumberView);
        if (currentNumber == 0) this.currentNumber = a.getInteger(R.styleable.ScrollNumberView_defaultNumber, 0);
        this.minNumber = a.getInteger(R.styleable.ScrollNumberView_minNumber, this.currentNumber);
        this.maxNumber = a.getInteger(R.styleable.ScrollNumberView_maxNumber, this.currentNumber);
        this.arrowUp = a.getResourceId(R.styleable.ScrollNumberView_arrowUp, 0);
        this.arrowDown = a.getResourceId(R.styleable.ScrollNumberView_arrowDown,0);
        a.recycle();

        //init
        TextView prevItem = new TextView(getContext());
        prevItem.setText(String.valueOf(currentNumber - 1));

        TextView currentItem = new TextView(getContext());
        currentItem.setText(String.valueOf(currentNumber));

        TextView afterItem = new TextView(getContext());
        afterItem.setText(String.valueOf(currentNumber + 1));

        this.addView(prevItem);
        this.addView(currentItem);
        this.addView(afterItem);

        for (int i = 0; i < this.getChildCount(); i++){
            TextView textView = (TextView) this.getChildAt(i);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.scroll_number_text_size));
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            textView.setLayoutParams(layoutParams);
        }
        ScrollOnTouchListener onTouchListener = new ScrollOnTouchListener(this);
        this.setOnTouchListener(onTouchListener);
        this.touchListener = onTouchListener;


        prevItem.setTranslationY(-R.dimen.main_text_size);
        afterItem.setTranslationY(R.dimen.main_text_size);


    }
    static public class OnNumberChangeListener {
        public void onNumberChange(int number) {

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private class ScrollOnTouchListener implements View.OnTouchListener {
        private float initial_y;
        private ViewGroup viewGroup; //ScrollView
        private float space; //space between the numbers
        private int currentNumber;
        private int minNumber = ScrollNumberView.this.minNumber; // minimum number posible
        private int maxNumber = ScrollNumberView.this.maxNumber; // maximum number posible
        private View arrowUp;
        private View arrowDown;
        private boolean set_up = false;


        public ScrollOnTouchListener(ViewGroup v){
            this.viewGroup = v;
            this.space = getResources().getDimension(R.dimen.scroll_number_box_size);
            this.currentNumber = ScrollNumberView.this.currentNumber;
        }

        // fixes when that can be done only when the view has already been created
        public void setUp(){
            if (!set_up) {
                //Set arrows
                arrowUp = ((ViewGroup) viewGroup.getParent()).findViewById(ScrollNumberView.this.arrowUp);
                float xFix = viewGroup.getWidth()/2 - arrowUp.getWidth()/2;
                arrowUp.setTranslationX(xFix);
                arrowDown = ((ViewGroup) viewGroup.getParent()).findViewById(ScrollNumberView.this.arrowDown);
                xFix = viewGroup.getWidth()/2 - arrowDown.getWidth()/2;
                arrowDown.setTranslationX(xFix);
                float yFix = 0 - arrowUp.getTranslationY() - viewGroup.getHeight() + arrowUp.getHeight();
                arrowDown.setTranslationY(yFix);
            }
            set_up = true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            setUp();
            v.getParent().requestDisallowInterceptTouchEvent(true);
            if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                initial_y = event.getRawY();
                //Display arrows
                arrowUp.setVisibility(View.VISIBLE);
                arrowDown.setVisibility(View.VISIBLE);

                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //Display one above another and translate
                int n = viewGroup.getChildCount();
                if ((event.getRawY()-initial_y) > space && currentNumber != minNumber) {
                    // Display previous numbers when scrolling up
                    for (int i = 0; i < n; i++){
                        TextView vItem = (TextView) viewGroup.getChildAt(i);
                        vItem.setText(String.valueOf(i+currentNumber-2));
                        initial_y += (event.getRawY()-initial_y);
                    }
                    currentNumber-=1;
                    // Notify number change
                    OnNumberChangeListener numberChangeListener = ScrollNumberView.this.numberChangeListener;
                    if (numberChangeListener != null) numberChangeListener.onNumberChange(currentNumber);

                } else if ((event.getRawY()-initial_y) < -space && currentNumber != maxNumber) {
                    // Display later numbers when scrolling down
                    for (int i = 0; i < n; i++){
                        TextView vItem = (TextView) viewGroup.getChildAt(i);
                        vItem.setText(String.valueOf(i+currentNumber));
                        initial_y += (event.getRawY()-initial_y);
                    }

                    currentNumber+=1;

                    // Notify number change
                    OnNumberChangeListener numberChangeListener = ScrollNumberView.this.numberChangeListener;
                    if (numberChangeListener != null) numberChangeListener.onNumberChange(currentNumber);
                }
                // Render actual position
                for (int i = 0; i < n; i++){
                    // Limit movement when max number
                    if (currentNumber == maxNumber && ((event.getRawY()-initial_y) < 0) ) {
                        View vItem = viewGroup.getChildAt(i);
                        float translation = (i - n / 2) * space;
                        vItem.setTranslationY(translation);
                    } //limit movement when min number
                    else if (currentNumber == minNumber && ((event.getRawY()-initial_y) > 0)) {
                        View vItem = viewGroup.getChildAt(i);
                        float translation = (i - n / 2) * space;
                        vItem.setTranslationY(translation);
                    } else {
                        View vItem = viewGroup.getChildAt(i);
                        float translation = (event.getRawY() - initial_y) + (i - n / 2) * space;
                        vItem.setTranslationY(translation);
                    }
                }
                return true;
            }
            // set position for the current number
            if (event.getAction() == MotionEvent.ACTION_UP) {
                for (int i = 0; i < viewGroup.getChildCount(); i++){
                    View vItem = viewGroup.getChildAt(i);
                    float translation = (i - viewGroup.getChildCount()/2)*space;
                    vItem.setTranslationY(translation);
                }
                //Hide arrows
                arrowUp.setVisibility(View.INVISIBLE);
                arrowDown.setVisibility(View.INVISIBLE);
                return true;
            }

            return false;
        }
        void setNumber(int number) {
            this.currentNumber = number;
        }
        int getNumber(){
            return currentNumber;
        }
    }
    void setNumber(int number) {
        this.currentNumber = number;
        touchListener.setNumber(number);
        ViewGroup viewGroup = this;
        for (int i = 0; i < viewGroup.getChildCount(); i++){
            TextView vItem = (TextView) viewGroup.getChildAt(i);
            vItem.setText(String.valueOf(i+currentNumber-1));
        }
    }
    int getNumber(){
        return currentNumber;
    }
}

