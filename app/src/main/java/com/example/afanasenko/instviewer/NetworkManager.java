package com.example.afanasenko.instviewer;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class NetworkManager {
    private static NetworkManager networkManager = new NetworkManager();

    public static NetworkManager getInstance() {
        return networkManager;
    }

    public void getToken(final Context context, final String url, final Callback<String> callback){
        //INVALID URL for getting ACCESS TOKEN. launch https://www.instagram.com/developer/authentication/
        new HttpRequestAsyncTask<String>(context, callback) {
            @Override
            protected String getData(String json) throws JSONException {
                String token = "";
                JSONObject jsonObj = (JSONObject) new JSONTokener(json).nextValue();
                token = jsonObj.getString("access_token");
                return token;
            }
        }.execute(url);

    }

    public void getMedia(final Context context, final String url, final Callback<List<String>> callback){
        new HttpRequestAsyncTask<List<String>>(context, callback) {
            @Override
            protected List<String> getData(String json) throws JSONException {
                List<String> mediaList = new ArrayList<String>();
                JSONObject jsonObj = (JSONObject) new JSONTokener(json).nextValue();
               // JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray jsonArray= jsonObj.getJSONArray("data");
                //String link="";
                String media="";

                for (int i=0; i<jsonArray.length();i++){
                  //  link=jsonArray.getJSONObject(i).getString("link");
                    media=jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution").getString("url");

                    mediaList.add(media);
                    //userLinks.add(link);

                    Log.i("INSTAGRAM API", "Got media: " + media);
                   // Log.i(TAG, "Got link: " +link);
                }

                return mediaList;
            }
        }.execute(url);

    }
    public static interface Callback<T> {
        public void onData(T data);

        public void onFail(FailType failType);
    }

    public static enum FailType {
        JSON, NETWORK
    }
}
