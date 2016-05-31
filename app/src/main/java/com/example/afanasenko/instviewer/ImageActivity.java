package com.example.afanasenko.instviewer;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends ListActivity {
      ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        Intent i = getIntent();
        String mediaUrl=i.getStringExtra("MEDIA_URL");

        NetworkManager.getInstance().getMedia(this, mediaUrl, new NetworkManager.Callback<List<String>>() {
            @Override
            public void onData(List<String> data) {
                Log.i("GOT MEDIA ASYNC", data.toString());
                adapter=new ImageAdapter(ImageActivity.this, data);
                setListAdapter(adapter);
            }
            @Override
            public void onFail(NetworkManager.FailType failType) {
                Log.i("GOT MEDIA ASYNC", "FAIL");
                Toast.makeText(ImageActivity.this, "Getting media failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
