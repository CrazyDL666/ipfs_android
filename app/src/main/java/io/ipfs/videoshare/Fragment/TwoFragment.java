package io.ipfs.videoshare.Fragment;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.gson.Gson;

import java.io.IOException;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.ipfs.videoshare.App;
import io.ipfs.videoshare.Bean.demoBean;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.video_adpter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * Created by Administrator on 2020/3/30.
 */

public class TwoFragment extends Fragment {
    JZVideoPlayerStandard jzvdStd;
    TextView tishi;
    demoBean demoVideos;
    Handler thread;
    float mPosX,mPosY,mCurPosX,mCurPosY;
    private String inHash="QmeHccaAitY3h6QUyf4VrgKziAdSpwrDuu7ETyxrpax1oZ";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bofangqi0, null);
        jzvdStd = (JZVideoPlayerStandard) view.findViewById(R.id.videoplayer);
        tishi=view.findViewById(R.id.tishi);
        tishi.setText("未连接 ETH, 请下载浏览器插件\uD83E\uDD8AMetaMask，并切换到Ropsten测试网络，访问水龙头以获取测试用的以太币。");
        jzvdStd.thumbImageView.setImageResource(R.drawable.back);  // 背景图（不会充满屏幕）
        JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
        jzvdStd.TOOL_BAR_EXIST = false;
        OkHttpClient client = new OkHttpClient();
        String getway=App.default_getway.replace("/ipfs/:hash","/ipns/"+inHash+"/demo.json");
        Request request = new Request.Builder().url(getway).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "请求失败: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String body = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (body != null) {
                                Gson gson = new Gson();
                                demoVideos = gson.fromJson(body, demoBean.class);

                                int size = demoVideos.getDemoVideos().size();
                                int playid = (int)(Math.random()*(size));
                                String getway=App.default_getway.replace("/ipfs/:hash",demoVideos.getDemoVideos().get(playid).getUrl());
                                jzvdStd.setUp(getway, JZVideoPlayerStandard.SCROLL_AXIS_HORIZONTAL, demoVideos.getDemoVideos().get(playid).getTitle());
                                //jzvdStd.startVideo();

                                thread = new Handler();
                                thread.post(runnable);
                            }
                        }
                    });


                }
            }
        });
        return view;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (demoVideos.getDemoVideos().size() > 0 && null != jzvdStd) {
                if (jzvdStd.currentState == JZVideoPlayer.CURRENT_STATE_AUTO_COMPLETE ||
                        jzvdStd.currentState == JZVideoPlayer.CURRENT_STATE_ERROR) {
                    int size = demoVideos.getDemoVideos().size();
                    int playid = (int)(Math.random()*(size));
                    String getway=App.default_getway.replace("/ipfs/:hash",demoVideos.getDemoVideos().get(playid).getUrl());
                    jzvdStd.setUp(getway, JZVideoPlayerStandard.SCROLL_AXIS_HORIZONTAL, demoVideos.getDemoVideos().get(playid).getTitle());
                    jzvdStd.startVideo();
                }
            }
            thread.postDelayed(this, 1000);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }
}
