package com.example.unitconverter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditViewUpdate extends MainActivity implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //MainActivity mainActivity = new MainActivity();
        int id = getCurEditText();
        EditText editText1 = mEditText1;
        EditText editText2 = mEditText2;
        if(id == 1) editText2.setText(editText1.getText().toString());
        else{
            editText1.setText(editText2.getText().toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
