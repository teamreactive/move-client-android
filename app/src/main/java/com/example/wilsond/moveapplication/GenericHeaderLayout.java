package com.example.wilsond.moveapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by WILSOND on 12/23/15.
 */
public class GenericHeaderLayout {
    View genericHeader;
    GenericHeaderLayout(View genericHeader, Activity activity){
        this.genericHeader = genericHeader;
        TextView title = (TextView) genericHeader.findViewById(R.id.activity_title);
        Typeface font = Typeface.createFromAsset(activity.getResources().getAssets(), "fonts/Roboto-Thin.ttf");
        title.setTypeface(font);
    }
    void setTitle(int stringId) {
        ((TextView)genericHeader.findViewById(R.id.activity_title)).setText(stringId);
    }
    void setNavigationDrawer(final DrawerLayout drawerLayout,final int gravity) {
        ImageButton button = (ImageButton) genericHeader.findViewById(R.id.menu_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(gravity);
            }
        });
    }
}
