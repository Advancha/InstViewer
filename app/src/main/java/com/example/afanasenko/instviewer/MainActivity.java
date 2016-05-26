package com.example.afanasenko.instviewer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends ListActivity {

    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int requestCode = 1;
        Intent i = new Intent(this,AuthDlgActivity.class);
        startActivityForResult(i,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}

        //TODO: Have to get ACCESS_TOKEN and USER_ID (there are constants now)

        String mediaUrl = InstagramSession.API_URL + "/users/" + InstagramSession.user_id + "/media/recent/?access_token=" + InstagramSession.token;
        Log.i("MEDIA URL", mediaUrl);
        NetworkManager.getInstance().getMedia(this, mediaUrl, new NetworkManager.Callback<List<String>>() {
            @Override
            public void onData(List<String> data) {
                Log.i("GOT MEDIA ASYNC", data.toString());
                adapter=new ImageAdapter(MainActivity.this, data);
                setListAdapter(adapter);
            }
            @Override
            public void onFail(NetworkManager.FailType failType) {
                Log.i("GOT MEDIA ASYNC", "FAIL");
            }
        });


    }
}

