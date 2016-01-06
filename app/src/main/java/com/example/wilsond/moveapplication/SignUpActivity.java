package com.example.wilsond.moveapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Text Watchers
        editTextLabeledSetUp(R.id.name);
        editTextLabeledSetUp(R.id.email_address);
        editTextLabeledSetUp(R.id.phone);
        editTextLabeledSetUp(R.id.password);
        editTextLabeledSetUp(R.id.repeat_password);

        // Alerts
        alertOnDifferentSetUp(R.id.repeat_password, R.id.password, R.id.password_repeat_alert);

        //change to login Activity
        View linkToSignUp = findViewById(R.id.login_link);
        linkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUp = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(signUp);
                finish();
            }
        });
    }

    private void editTextLabeledSetUp(int editTextId) {
        EditText editText;
        editText = (EditText) findViewById(editTextId);
        editText.addTextChangedListener(new GenericTextWatcher(editText));
    }

    private void alertOnDifferentSetUp(int targetId, int comparingId, int alertId) {
        EditText editText = (EditText) findViewById(targetId);
        EditText editTextC = (EditText) findViewById(comparingId);
        TextView textView = (TextView) findViewById(alertId);
        editText.setOnFocusChangeListener(new AlertOnDifferent(editText,editTextC,textView));
    }

    private class AlertOnDifferent implements View.OnFocusChangeListener {
        private EditText target;
        private EditText comparing;
        private TextView alert;

        AlertOnDifferent(EditText target, EditText comparing, TextView alert) {
            this.target = target;
            this.comparing = comparing;
            this.alert = alert;
        }
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            String targetText = String.valueOf(target.getText());
            String otherText = String.valueOf(comparing.getText());
            if (!hasFocus) {
                if (targetText.compareTo(otherText) == 0)
                    alert.setVisibility(View.INVISIBLE);
                else alert.setVisibility(View.VISIBLE);
            } else alert.setVisibility(View.INVISIBLE);
        }
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;

        GenericTextWatcher(View view) {

            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            EditTextLabeledView td = (EditTextLabeledView) view;
            TextView label = (TextView) findViewById(td.getLabel());
            if (text.isEmpty() ){
                label.setVisibility( View.INVISIBLE );
            } else {
                label.setVisibility( View.VISIBLE );
            }

        }
    }

   @Override
    public void onBackPressed(){
        //change to FirstActivity
        Intent activity = new Intent(getApplicationContext(),FirstActivity.class);
        startActivity(activity);
        finish();
    }
}
