package com.loopeer.developutils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextLengthWatcher implements TextWatcher {

    private EditText editText;
    private int length;
    private CharSequence toastString;
    private Context context;

    public EditTextLengthWatcher(EditText editText, int length, CharSequence toastString) {
        this.editText = editText;
        this.length = length;
        this.toastString = toastString;
        context = editText.getContext();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String string = s.toString();
        if (string.length() > length) {
            editText.setText(string.substring(0, length));
            editText.setSelection(length);
            Toast.makeText(context,
                    toastString,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
