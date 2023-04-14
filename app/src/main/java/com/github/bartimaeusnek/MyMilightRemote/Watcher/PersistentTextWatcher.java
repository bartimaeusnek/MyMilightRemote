package com.github.bartimaeusnek.MyMilightRemote.Watcher;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;

public class PersistentTextWatcher implements TextWatcher {

    public PersistentTextWatcher(SharedPreferences sharedPref, String name) {
        this.sharedPref = sharedPref;
        this.name = name;
    }

    private SharedPreferences sharedPref;
    private String name;

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, s.toString());
        editor.apply();
    }
}