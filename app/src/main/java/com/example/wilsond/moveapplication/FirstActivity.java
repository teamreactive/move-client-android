package com.example.wilsond.moveapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //change to SignUp Activity
        View linkToSignUp = findViewById(R.id.sign_up_button);
        linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUp);
                finish();
            }
        });

        //change to login Activity
        View linkToLogin = findViewById(R.id.login_button);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}
