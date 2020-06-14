package io.ipfs.videoshare.one;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.ipfs.videoshare.App;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.adpter.One_list_Adapter;
import io.ipfs.videoshare.adpter.Type_Adapter;
import io.ipfs.videoshare.bean.UserBean;
import io.ipfs.videoshare.bean.UserHashBean;
import io.ipfs.videoshare.updata_bean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;


public class Type_list extends AppCompatActivity {
    @InjectView(R.id.recycler)
    RecyclerView recyclerview;
    @InjectView(R.id.back)
    ImageView back;

    private String url;
    private Type_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_list2);
        ButterKnife.inject(this);
        url = getIntent().getStringExtra("url");
        get_Type(url);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void get_Type(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //...
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string().trim();
                    Gson gson = new Gson();
                    final UserBean userBean = gson.fromJson(result, UserBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            body(userBean);
                        }
                    });

                }
            }
        });
    }

    private void body(UserBean userBean){
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();
            }
        });

        adapter=new Type_Adapter(Type_list.this, userBean,new Type_Adapter.OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, String hash,String title) {

                String video_list_url=url;
                video_list_url=video_list_url.replace("user",hash);

                Intent intent = new Intent(Type_list.this, Video_list.class);
                intent.putExtra("url", video_list_url);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        recyclerview.setAdapter(adapter);
    }




}
