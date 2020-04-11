package io.ipfs.videoshare.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.ipfs.videoshare.App;
import io.ipfs.videoshare.MainActivity;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.Updata_list;

/**
 * Created by Administrator on 2020/3/30.
 */

public class ForeFragment extends Fragment {


    @InjectView(R.id.click1)
    RelativeLayout click1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, null);
        ButterKnife.inject(this, view);
        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (App.updata_date.equals("")) {
                    Intent intent = new Intent(getActivity(), Updata_list.class);
                    intent.putExtra("json", "");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), Updata_list.class);
                    intent.putExtra("json", App.updata_date);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
