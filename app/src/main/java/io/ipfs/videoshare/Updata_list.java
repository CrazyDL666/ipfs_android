package io.ipfs.videoshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/26.
 */

public class Updata_list extends Activity {
    String json;
    updata_adpter updata_adpter;
    @InjectView(R.id.list_guanzhu)
    ListView listGuanzhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updata_list);
        ButterKnife.inject(this);
        json = getIntent().getStringExtra("json");
        if (json.equals("")) {
            if (MainActivity._main != null) {
                getAsyn(App.updata_url);
            } else {
                getAsyn(App.updata_url);
            }
        } else {
            Gson gson = new Gson();
            updata_bean upbean = gson.fromJson(json, updata_bean.class);
            list(upbean);
        }
    }

    public void getAsyn(String url) {
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
                    final updata_bean upbean = gson.fromJson(result, updata_bean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list(upbean);
                        }
                    });
                }
            }
        });
    }

    private void list(updata_bean updata_bean) {
        updata_adpter = new updata_adpter(Updata_list.this, updata_bean, new updata_adpter.OnWtglItemListener() {
            @Override
            public void OnWtglItemCliek(String down_url) {
                Intent intent = new Intent(Updata_list.this, erweima.class);
                intent.putExtra("down_url", down_url);
                startActivity(intent);
            }
        });
        listGuanzhu.setAdapter(updata_adpter);
    }


}
