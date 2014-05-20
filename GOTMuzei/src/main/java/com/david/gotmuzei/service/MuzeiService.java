package com.david.gotmuzei.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.david.gotmuzei.R;
import com.david.gotmuzei.models.Photo;
import com.david.gotmuzei.utils.Constants;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by davidhodge on 5/20/14.
 */
public class MuzeiService extends RemoteMuzeiArtSource {

    private static final int ROTATE_TIME_MILLIS = 3 * 60 * 60 * 1000; // rotate every 3 hours
    private static final String SOURCE_NAME = "GOTMuzeiArtSource";

    SharedPreferences sharedPreferences;

    public MuzeiService() {
        super(SOURCE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onTryUpdate(int reason) throws RetryException {
        //TODO allow user to select between text or no text images
        if(sharedPreferences.getBoolean("text", true) == true){
            getPicturesWithText();
        }else{
            getPicturesWithNoText();
        }

    }

    public void getPicturesWithText(){
        try {
            InputStream is = getResources().openRawResource(R.raw.photos_text);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }

            String jsonString = writer.toString();
            Gson gson = new Gson();

            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(jsonString).getAsJsonArray();

            ArrayList<Photo> lcs = new ArrayList<Photo>();

            for(JsonElement obj : jArray ){
                Photo cse = gson.fromJson( obj , Photo.class);
                lcs.add(cse);
            }

            String token;
            token = "";

            Random r = new Random();
            int i = r.nextInt(lcs.size());

            publishArtwork(new Artwork.Builder()
                    .title(lcs.get(i).house)
                    .byline(Constants.IMAGE_CREATOR)
                    .imageUri(Uri.parse(lcs.get(i).photoLink))
                    .token(token)
                    .build());

            scheduleUpdate(System.currentTimeMillis() + ROTATE_TIME_MILLIS);

        }catch (Exception e){
            Log.e("gotmuzei", e.toString());
        }
    }

    public void getPicturesWithNoText(){
        try {
            InputStream is = getResources().openRawResource(R.raw.photo_no_text);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }

            String jsonString = writer.toString();
            Gson gson = new Gson();

            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(jsonString).getAsJsonArray();

            ArrayList<Photo> lcs = new ArrayList<Photo>();

            for(JsonElement obj : jArray ){
                Photo cse = gson.fromJson( obj , Photo.class);
                lcs.add(cse);
            }

            String token;
            token = "";

            Random r = new Random();
            int i = r.nextInt(lcs.size());

            publishArtwork(new Artwork.Builder()
                    .title(lcs.get(i).house)
                    .byline(Constants.IMAGE_CREATOR)
                    .imageUri(Uri.parse(lcs.get(i).photoLink))
                    .token(token)
                    .build());

            scheduleUpdate(System.currentTimeMillis() + ROTATE_TIME_MILLIS);

        }catch (Exception e){
            Log.e("gotmuzei", e.toString());
        }
    }
}
