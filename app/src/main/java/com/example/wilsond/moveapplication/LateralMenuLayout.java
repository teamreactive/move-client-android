package com.example.wilsond.moveapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by WILSOND on 12/23/15.
 */
public class LateralMenuLayout {
    View lateralMenuLayout;
    Activity activity;

    LateralMenuLayout(View lateralMenuLayout, final Activity activity) {
        this.lateralMenuLayout = lateralMenuLayout;
        this.activity = activity;
        // Set Buttons
        View myOrders = lateralMenuLayout.findViewById(R.id.lateral_menu_task_my_orders);
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(),MyOrdersActivity.class);
                activity.startActivity(intent);
            }
        });

    }
    void highlightItem(int viewID) {
        lateralMenuLayout.findViewById(viewID).setBackgroundResource(R.color.colorPrimaryDark);
    }

}
