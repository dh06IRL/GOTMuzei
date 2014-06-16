package com.david.gotmuzei.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.david.gotmuzei.R;
import com.david.gotmuzei.utils.Constants;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by davidhodge on 5/20/14.
 */
public class SettingsActivity extends Activity {

    CheckBox showTextBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView dhodgeText;
    TextView twitterText;
    TextView appNetText;
    TextView emailText;
    TextView plusText;
    TextView shareText;
    TextView otherAppsText;
    TextView iconText;

    Context mContext;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        mContext = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(getResources().getColor(android.R.color.transparent));
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintColor(getResources().getColor(android.R.color.transparent));

        actionBar = getActionBar();
        actionBar.hide();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        showTextBox = (CheckBox) findViewById(R.id.text_checkbox);
        //TODO add update options?

        if(sharedPreferences.getBoolean("text", true) == true){
            showTextBox.setChecked(true);
        }else{
            showTextBox.setChecked(false);
        }

        showTextBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("text", true);
                    editor.commit();
                }else{
                    editor.putBoolean("text", false);
                    editor.commit();
                }
            }
        });

        dhodgeText = (TextView) findViewById(R.id.dhodge_text);
        iconText = (TextView) findViewById(R.id.icon_text);
        twitterText = (TextView) findViewById(R.id.twitter_text);
        appNetText = (TextView) findViewById(R.id.app_net_text);
        emailText = (TextView) findViewById(R.id.email_text);
        plusText = (TextView) findViewById(R.id.plus_text);
        shareText = (TextView) findViewById(R.id.share_text);
        otherAppsText = (TextView) findViewById(R.id.other_apps_text);

        dhodgeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_CREATOR_LINK)));
            }
        });

        iconText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.IMAGE_CREATOR_LINK)));
            }
        });

        twitterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TWITTER_URL)));
            }
        });

        appNetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_NET_URL)));
            }
        });

        emailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", Constants.EMAIL_URL, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "GOT Muzei " + " v1.0.5");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        plusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PLUS_URL)));
            }
        });

        shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_RATE_URL)));
            }
        });

        otherAppsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.OTHER_APPS_URL)));
            }
        });
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        final int bits1 = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
            winParams.flags |= bits1;
        } else {
            winParams.flags &= ~bits;
            winParams.flags &= ~bits1;
        }
        win.setAttributes(winParams);
    }
}
