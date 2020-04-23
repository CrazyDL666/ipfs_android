package io.ipfs.videoshare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.ipfs.videoshare.ipfs_util.DatabaseHelper;
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

                        SharedPreferences sharedPreferences = getSharedPreferences("ipfs",MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        boolean isFirst = sharedPreferences.getBoolean("isExist",true);
                        if (isFirst){
                            DatabaseHelper dbHelper = new DatabaseHelper(Loading.this, "ipfs", null, 1);
                            edit.putBoolean("isExist",false);
                            edit.commit();
                        }else{
                            Toast.makeText(Loading.this, sharedPreferences.getString("name","不存在"), Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        }.start();

    }
}
