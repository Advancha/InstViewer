package com.example.afanasenko.instviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Afanasenko on 27.05.2016.
 */
public class InstagramWebViewClient extends WebViewClient {
    Context context;
    InstagramSession instagramSession;
    ProgressDialog mSpinner;

    public InstagramWebViewClient(Context context, InstagramSession instagramSession) {
        super();
        this.context=context;
        this.instagramSession=instagramSession;

        mSpinner = new ProgressDialog(context);
        mSpinner.setMessage("Loading...");
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    boolean authSuccessful = false;
    boolean authComplete = false;
    String authCode;


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("Redirecting URL ", url);
        if (url.startsWith(InstagramSession.callbackUrl))
        {
            return true;
        }
        return false;
    }

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
            authSuccessful=true;

            instagramSession.fetchAuthParams(context, authCode);

            //Invalid link
            //String feedUrl = instagramSession.recieveFeedsUrl();
            //Log.i("", "FEED URL : " + feedUrl);

            Intent intent = new Intent(context, ImageActivity.class);
            intent.putExtra("MEDIA_URL", instagramSession.recieveMediaUrl());
            context.startActivity(intent);

        } else if (url.contains("error=access_denied")) {
            Log.i("", "ACCESS_DENIED_HERE");
            authComplete = true;
        }

    }

}