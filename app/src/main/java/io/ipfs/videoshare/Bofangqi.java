package io.ipfs.videoshare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.ipfs.videoshare.bean.VideoBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Bofangqi extends AppCompatActivity {
    JCVideoPlayerStandard jzvdStd;
    ListView list;
    video_adpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bofangqi);
        jzvdStd = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        list=findViewById(R.id.list);
        getAsyn(App.head+"/ipfs/QmPh5RHFtxrKBx7b8YDr3y72cycjmhFtHiryafz7jeeEws/files.json");



    }
    public void getAsyn(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string().trim();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson=new Gson();
                            VideoBean bean = gson.fromJson(result, VideoBean.class);
                            adpter = new video_adpter(Bofangqi.this, bean, new video_adpter.OnWtglItemListener() {
                                @Override
                                public void OnWtglItemCliek(String down_url,String title) {
                                    jzvdStd.setUp(App.head+down_url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
                                }
                            });
                            list.setAdapter(adpter);



                        }
                    });

                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayerStandard.releaseAllVideos();
    }
}
