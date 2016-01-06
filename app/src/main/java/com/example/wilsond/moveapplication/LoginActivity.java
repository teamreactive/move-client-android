package com.example.wilsond.moveapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //change to SignUp Activity
        View linkToSignUp = findViewById(R.id.sign_up_link);
        linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUp);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        //change to FirstActivity
        Intent activity = new Intent(getApplicationContext(),FirstActivity.class);
        startActivity(activity);
        finish();
    }

}
