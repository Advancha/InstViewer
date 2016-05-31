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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog mSpinner;
    private InstagramSession instagramSession=new InstagramSession();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String mAuthUrl = instagramSession.recieveAuthUrl();

        InstagramWebViewClient instagramWebViewClient=new InstagramWebViewClient(this,instagramSession);

        WebView web = (WebView) findViewById(R.id.webv);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(mAuthUrl);
        web.setWebViewClient(instagramWebViewClient);
    }

}

