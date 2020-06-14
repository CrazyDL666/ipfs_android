package io.ipfs.videoshare.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.gson.Gson;

import java.io.IOException;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
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
    JCVideoPlayerStandard jzvdStd;
    private String inHash="QmeHccaAitY3h6QUyf4VrgKziAdSpwrDuu7ETyxrpax1oZ";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bofangqi, null);
        jzvdStd = (JCVideoPlayerStandard) view.findViewById(R.id.videoplayer);
        JCVideoPlayerStandard.releaseAllVideos();
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
                                Log.e("zzy",body);
                                Gson gson = new Gson();
                                demoBean demoVideos = gson.fromJson(body, demoBean.class);
                                int size = demoVideos.getDemoVideos().size();
                                int playid = (int)(Math.random()*(size));
                                String getway=App.default_getway.replace("/ipfs/:hash",demoVideos.getDemoVideos().get(playid).getUrl());
                                jzvdStd.setUp(getway, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, demoVideos.getDemoVideos().get(playid).getTitle());
                            }
                        }
                    });


                }
            }
        });
        return view;
    }
}
