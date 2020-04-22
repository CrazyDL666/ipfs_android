package io.ipfs.videoshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.ipfs.videoshare.ipfs_util.Util;
import ipfs.gomobile.android.IPFS;

public class Loading extends AppCompatActivity {
    public static Util util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        new Thread(){
            @Override
            public void run() {
                    util=new Util();
                    if(util.startipfs(Loading.this)){
                        Intent intent=new Intent(Loading.this,MainActivity.class);
                        startActivity(intent);
                    }
            }
        }.start();

    }
}
