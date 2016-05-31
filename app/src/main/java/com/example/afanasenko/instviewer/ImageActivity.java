package com.example.afanasenko.instviewer;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
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

        NetworkManager.getInstance().getMediaImageData(this, mediaUrl, new NetworkManager.Callback<List<ImageData>>() {
            @Override
            public void onData(List<ImageData> data) {
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getItem(position).getLink()));
        startActivity(browserIntent);
    }
}
