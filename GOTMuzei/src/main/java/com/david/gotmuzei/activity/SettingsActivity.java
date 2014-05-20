package com.david.gotmuzei.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.david.gotmuzei.R;

/**
 * Created by davidhodge on 5/20/14.
 */
public class SettingsActivity extends Activity {

    CheckBox showTextBox;
    Spinner updateFreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        showTextBox = (CheckBox) findViewById(R.id.text_checkbox);
        //TODO add update options
        updateFreq = (Spinner) findViewById(R.id.update_int);
    }

}
