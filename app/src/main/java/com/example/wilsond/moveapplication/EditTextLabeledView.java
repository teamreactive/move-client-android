package com.example.wilsond.moveapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EditTextLabeledView extends EditText {
    private int mLabel;
    public EditTextLabeledView(Context context, AttributeSet attrs) {
        super(context,attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextLabeledView);
        mLabel = a.getResourceId(R.styleable.EditTextLabeledView_label,0);
        a.recycle();
        this.addTextChangedListener(new EditTextLabeledTextWatcher((Activity) this.getContext(),this));
    }

    public int getLabel() {
        return mLabel;
    }

    private class EditTextLabeledTextWatcher implements TextWatcher {

        private View view;
        private Activity activity;

        EditTextLabeledTextWatcher(Activity activity, View view) {

            this.view = view;
            this.activity = activity;

        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            EditTextLabeledView td = (EditTextLabeledView) view;
            TextView label = (TextView) activity.findViewById(td.getLabel());
            if (text.isEmpty() ){
                label.setVisibility( View.INVISIBLE );
            } else {
                label.setVisibility( View.VISIBLE );
            }

        }
    }

}
