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
                //String token = "";
                JSONObject jsonObj = (JSONObject) new JSONTokener(json).nextValue();
                String token = jsonObj.getString("access_token");
                return token;
            }
        }.execute(url);

    }

    public void getMediaImageData(final Context context, final String url, final Callback<List<ImageData>> callback){
        new HttpRequestAsyncTask<List<ImageData>>(context, callback) {
            @Override
            protected List<ImageData> getData(String json) throws JSONException {
                List<ImageData> imageDataList = new ArrayList<ImageData>();
                JSONObject jsonObj = (JSONObject) new JSONTokener(json).nextValue();
                JSONArray jsonArray= jsonObj.getJSONArray("data");
                for (int i=0; i<jsonArray.length();i++){

                    String link=jsonArray.getJSONObject(i).getString("link");
                    String media=jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                    imageDataList.add(new ImageData(media,link));

                    Log.i("INSTAGRAM API", "Got media: " + media);
                    Log.i("INSTAGRAM API", "Got link: " +link);
                }

                return imageDataList;
            }
        }.execute(url);

    }
    public static interface Callback<T> {
        public void onData(T data);

        public void onFail(FailType failType);
    }

    public enum FailType {
        JSON, NETWORK
    }
}
