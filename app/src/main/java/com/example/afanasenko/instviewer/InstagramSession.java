package com.example.afanasenko.instviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Afanasenko on 20.05.2016.
 */
public class InstagramSession {

    public static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/";
    public static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    public static final String API_URL = "https://api.instagram.com/v1";

    public static final String clientId="d5a248424dfa4a2f9377a3fe5e959a46";
    public static final String clientSecret="17ff9cfe502546ef837a1cbf406ea09a";
    public static final String callbackUrl ="http://localhost";

    //public static  final String token="3250066581.d5a2484.d9ebdda7f6ce486ca7529f6a0af20c1c";
    //public static  final String user_id="3250066581";

    private String token;
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void fetchAuthParams(Context context,String authCode){
        setToken("3250066581.d5a2484.d9ebdda7f6ce486ca7529f6a0af20c1c");
        setUser_id("3250066581");

        String tokenUrl=recieveTokenUrl(authCode);
        Log.i("TOKEN URL", tokenUrl);
        NetworkManager.getInstance().getToken(context, tokenUrl, new NetworkManager.Callback<String>() {
            @Override
            public void onData(String data) {
                Log.i("GOT TOKEN", data.toString());
                Log.i("SET TOKEN", "3250066581.d5a2484.d9ebdda7f6ce486ca7529f6a0af20c1c");
                setToken(data.toString());
            }

            @Override
            public void onFail(NetworkManager.FailType failType) {
                Log.i("GOT TOKEN", "FAIL");

            }
        });

    }

    public String recieveTokenUrl(String authCode){
        return  TOKEN_URL + "/?client_id="+clientId+
                "&client_secret="+clientSecret+
                "&grant_type=authorization_code" +
                "&redirect_uri="+callbackUrl+
                "&code="+authCode;
    }

    public String recieveAuthUrl(){
        return AUTH_URL + "?client_id=" +clientId + "&redirect_uri=" + callbackUrl + "&response_type=code&display=touch&scope=likes+comments+relationships";
    }

    public String recieveMediaUrl(){
        return API_URL + "/users/" + user_id + "/media/recent/?access_token=" + token;
    }

    public String recieveFeedsUrl(){
        //https://api.instagram.com/v1/users/self/feed?access_token=ACCESS-TOKEN
        return API_URL + "/users/self/feed?access_token=" + token;
    }

}