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
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.ipfs.videoshare.App;
import io.ipfs.videoshare.Bofangqi;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.Updata.OkGoUpdateHttpUtil;
import io.ipfs.videoshare.Updata_list;
import io.ipfs.videoshare.erweima;
import io.ipfs.videoshare.ipfs_util.StartIPFS;
import io.ipfs.videoshare.updata_bean;
import ipfs.gomobile.android.IPFS;

/**
 * Created by Administrator on 2020/3/30.
 */

public class FirstFragment extends Fragment {
    @InjectView(R.id.join)
    Button click1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);
        ButterKnife.inject(this, view);
        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Bofangqi.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
