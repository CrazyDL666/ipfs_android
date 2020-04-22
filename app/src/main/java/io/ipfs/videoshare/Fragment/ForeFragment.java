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
import io.ipfs.videoshare.Loading;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.Updata.OkGoUpdateHttpUtil;
import io.ipfs.videoshare.Updata_list;
import io.ipfs.videoshare.erweima;
import io.ipfs.videoshare.ipfs_util.StartIPFS;
import io.ipfs.videoshare.ipfs_util.Util;
import io.ipfs.videoshare.updata_bean;
import ipfs.gomobile.android.IPFS;

/**
 * Created by Administrator on 2020/3/30.
 */

public class ForeFragment extends Fragment {
    private IPFS ipfs;

    @InjectView(R.id.click1)
    RelativeLayout click1;
    @InjectView(R.id.click2)
    RelativeLayout click2;
    @InjectView(R.id.click3)
    RelativeLayout click3;
    @InjectView(R.id.banben)
    TextView banben;
    @InjectView(R.id.id)
    TextView id;
    public String head = App.updata_url_head;
    public String urlurl = App.updata_url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, null);
        ButterKnife.inject(this, view);


        //new StartIPFS(this).execute();

        id.setText("My ID:  "+ Loading.util.get_id());


        banben.setText(getVersionName(getContext()));
        click2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (App.updata_date.equals("")) {
                    Intent intent = new Intent(getActivity(), Updata_list.class);
                    intent.putExtra("json", "");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), Updata_list.class);
                    intent.putExtra("json", App.updata_date);
                    startActivity(intent);
                }
                return false;
            }
        });


        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updata();
            }
        });

        click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (App.updata_date.equals("")) {}else{

                    String title="",verson="",down_url="";
                    Gson gson=new Gson();
                    updata_bean upbean = gson.fromJson(App.updata_date, updata_bean.class);

                    int b_build=getVersionCode(getContext());
                    for(int i=0;i<upbean.getData().size();i++){
                        if(Integer.parseInt(upbean.getData().get(i).getBuild())==b_build){
                            title=upbean.getData().get(i).getTitle();
                            verson=upbean.getData().get(i).getVersion();
                            down_url=upbean.getData().get(i).getApk_file();
                        }
                    }
                    Intent intent = new Intent(getActivity(), erweima.class);
                    intent.putExtra("down_url", down_url);
                    intent.putExtra("name", title);
                    intent.putExtra("verson", verson);
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

    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    private void updata() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        Map<String, String> params = new HashMap<String, String>();
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(getActivity())
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(urlurl)
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(false)
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
                .hideDialogOnDownloading()
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
                .setTopPic(R.drawable.updata)
                //为按钮，进度条设置颜色，默认从顶部图片自动识别。
                .setThemeColor(ColorUtil.getRandomColor())
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
                //.setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                //不显示通知栏进度条
                .dismissNotificationProgress()
                //是否忽略版本
                //.showIgnoreVersion()

                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        App.updata_date = json;
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String updata = "Yes";
                            boolean constraint = false;

                            Gson gson = new Gson();
                            updata_bean upbean = gson.fromJson(json, updata_bean.class);
                            int big = 0;
                            int postion = 0;

                            for (int i = 0; i < upbean.getData().size(); i++) {
                                if (Integer.parseInt(upbean.getData().get(i).getBuild()) > big) {
                                    big = Integer.parseInt(upbean.getData().get(i).getBuild());
                                    postion = i;
                                }
                            }
                            if (big > getVersionCode(getContext())) {
                                updata = "Yes";
                            } else {
                                updata = "No";
                            }

                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(updata)
                                    //（必须）新版本号，
                                    .setNewVersion(upbean.getData().get(postion).getVersion())
                                    //（必须）下载地址
                                    .setApkFileUrl(head + upbean.getData().get(postion).getApk_file())
                                    //（必须）更新内容
                                    .setUpdateLog(upbean.getData().get(postion).getLog());
                            //大小，不设置不显示大小，可以不设置
//                                    .setTargetSize(jsonObject.optString("target_size"))
                            //是否强制更新，可以不设置
//                                    .setConstraint(constraint);
                            //设置md5，可以不设置
//                                    .setNewMd5(jsonObject.optString("new_md51"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }


//                    @Override
//                    public void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
//                        //自定义对话框
//                        showDiyDialog(updateApp, updateAppManager);
//                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        //CProgressDialogUtils.showProgressDialog(MainActivity.this);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        //CProgressDialogUtils.cancelProgressDialog(MainActivity.this);
                    }

                    /**
                     * 没有新版本
                     */
                    public void noNewApp() {
                        // Toast.makeText(getActivity(), "没有新版本", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
