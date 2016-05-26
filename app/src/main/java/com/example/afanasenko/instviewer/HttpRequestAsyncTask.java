package com.example.afanasenko.instviewer;

import android.content.Context;
import android.os.AsyncTask;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;

public abstract class HttpRequestAsyncTask<T> extends AsyncTask<String, String, T> {

    private Context context;
    private NetworkManager.Callback<T> callback;
    private NetworkManager.FailType failType;

    public HttpRequestAsyncTask(Context context, NetworkManager.Callback<T> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected T doInBackground(String... params) {
        String url = params[0];
        AjaxCallback<String> cb = new AjaxCallback<>();
        cb.url(url).type(String.class);
        AQuery aq = new AQuery(context);
        aq.sync(cb);

        String result = cb.getResult();
        AjaxStatus status = cb.getStatus();

        T data = null;
        if (status.getCode() == 200) {
            try {
                data = getData(result);
            } catch (JSONException e) {
                failType = NetworkManager.FailType.JSON;
            }
        } else {
            failType = NetworkManager.FailType.NETWORK;
        }
        return data;
    }

    @Override
    protected void onPostExecute(T data) {
        super.onPostExecute(data);

        if (failType != null)
            callback.onFail(failType);
        else
            callback.onData(data);
    }

    protected abstract T getData(String json) throws JSONException;
}
