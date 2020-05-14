package io.ipfs.videoshare.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.utils.ColorUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.ipfs.videoshare.App;
import io.ipfs.videoshare.Bofangqi;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.Updata.OkGoUpdateHttpUtil;
import io.ipfs.videoshare.Updata_list;
import io.ipfs.videoshare.adpter.One_list_Adapter;
import io.ipfs.videoshare.bean.UserHashBean;
import io.ipfs.videoshare.erweima;
import io.ipfs.videoshare.ipfs_util.StartIPFS;
import io.ipfs.videoshare.one.Type_list;
import io.ipfs.videoshare.updata_bean;
import ipfs.gomobile.android.IPFS;

/**
 * Created by Administrator on 2020/3/30.
 */

public class FirstFragment extends Fragment {
    @InjectView(R.id.recycler)
    RecyclerView recyclerview;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private One_list_Adapter adapter;
    private List<UserHashBean> userHashBeanList=new ArrayList<>();
    private String inHash="QmXidpbD1osmHXWN4gJc3NHry3kzTnicnp9Utrpxk6s4Du";//扫进来的hash
    private String inHash2="QmUJf5PCMFdcKtKhEH5Pa5zugJaM9WUxBrrR6EZeWnbWdd";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);
        ButterKnife.inject(this, view);

        //库里数据
        UserHashBean userHashBean=new UserHashBean();
        userHashBean.setUser("imba[电影]");
        userHashBean.setHash(inHash);
        userHashBeanList.add(userHashBean);

        UserHashBean userHashBean2=new UserHashBean();
        userHashBean2.setUser("imba[剧集]");
        userHashBean2.setHash(inHash2);
        userHashBeanList.add(userHashBean2);

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

        adapter=new One_list_Adapter(getActivity(), userHashBeanList,new One_list_Adapter.OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, String hash) {
                String getway=App.default_getway;
                getway=getway.replace("/ipfs/:hash","/ipns/"+hash);
                getway=getway+"/user.json";
                Intent intent = new Intent(getActivity(), Type_list.class);
                intent.putExtra("url", getway);
                startActivity(intent);


            }
        });

        recyclerview.setAdapter(adapter);
        return view;
    }
}
