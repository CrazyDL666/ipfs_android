package io.ipfs.videoshare.one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.ipfs.videoshare.Bofangqi;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.adpter.Type_Adapter;
import io.ipfs.videoshare.adpter.Video_list_Adapter;
import io.ipfs.videoshare.bean.UserBean;
import io.ipfs.videoshare.bean.VideoListBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Video_list extends AppCompatActivity {
    @InjectView(R.id.recycler)
    RecyclerView recyclerview;
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.title)
    TextView title2;
    private String url,title;
    private Video_list_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);
        ButterKnife.inject(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        get_Type(url);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title2.setText(title);
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
                    result="{\"data\":"+result+"}";
                    Gson gson = new Gson();
                    final VideoListBean userBean = gson.fromJson(result, VideoListBean.class);
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

    private void body(VideoListBean userBean){
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

        adapter=new Video_list_Adapter(Video_list.this, userBean,new Video_list_Adapter.OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, String hash) {
                Intent intent = new Intent(Video_list.this, Bofangqi.class);
                intent.putExtra("url", hash);
                startActivity(intent);
            }
        });
        recyclerview.setAdapter(adapter);
    }




}
