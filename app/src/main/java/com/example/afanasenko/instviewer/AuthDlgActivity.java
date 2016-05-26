package com.example.afanasenko.instviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

public class AuthDlgActivity extends Activity {

    private ProgressDialog mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.onCreate(null);
        mSpinner = new ProgressDialog(this);
        mSpinner.setMessage("Loading...");
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_auth_dlg);

        String mAuthUrl = InstagramSession.AUTH_URL + "?client_id=" + InstagramSession.clientId + "&redirect_uri="
            + InstagramSession.callbackUrl + "&response_type=code&display=touch&scope=likes+comments+relationships";

        WebView web = (WebView)findViewById(R.id.webv);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(mAuthUrl);
        web.setWebViewClient(new WebViewClient() {
            boolean authComplete = false;
            String authCode;

            Intent intent = new Intent();
            int RESULT_OK=1;
            int RESULT_ERROR=0;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mSpinner.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSpinner.dismiss();

                if (url.contains("?code=") && authComplete != true) {
                    Uri uri = Uri.parse(url);
                    authCode = uri.getQueryParameter("code");
                    Log.i("", "CODE : " + authCode);
                    authComplete = true;

                    intent.putExtra("authCode", authCode.toString());
                    setResult(RESULT_OK,intent);

                    finish();
            } else if (url.contains("error=access_denied")) {
                   Log.i("", "ACCESS_DENIED_HERE");
                    authComplete = true;
                    setResult(RESULT_ERROR);
                    finish();
            }
        }


    });

}

}
