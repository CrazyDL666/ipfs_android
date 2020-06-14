package io.ipfs.videoshare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.ipfs.videoshare.bean.VideoBean;
import io.ipfs.videoshare.ipfs_util.Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Bofangqi extends AppCompatActivity {
    JZVideoPlayerStandard jzvdStd;
    ListView list;
    video_adpter adpter;
    private String hash;
    private ReadMoreTextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bofangqi);
        jzvdStd = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        text=findViewById(R.id.text);
        list=findViewById(R.id.list);

        hash = getIntent().getStringExtra("url");

        Util util = new Util();
//        String ipfs = util.resolve_by_gateway(hash, App.default_getway);

        String result = util.get_content_by_gateway(hash+"/files.json", App.default_getway).trim();

        Gson gson=new Gson();
        VideoBean bean = gson.fromJson(result, VideoBean.class);
        text.setText(bean.getDescription());
        if(bean.getFiles().size()>0){
            String getway=App.default_getway.replace("/ipfs/:hash",bean.getFiles().get(0).getUrl());
                jzvdStd.setUp(getway, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, bean.getFiles().get(0).getTitle());
        }
        adpter = new video_adpter(Bofangqi.this, bean, new video_adpter.OnWtglItemListener() {
            @Override
            public void OnWtglItemCliek(String down_url,String title) {
                String getway=App.default_getway.replace("/ipfs/:hash",down_url);
                jzvdStd.setUp(getway, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, title);
            }
        });
        list.setAdapter(adpter);

    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }
}
