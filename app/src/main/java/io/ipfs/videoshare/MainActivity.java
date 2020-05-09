package io.ipfs.videoshare;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.google.gson.Gson;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.utils.ColorUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.ipfs.videoshare.Fragment.FirstFragment;
import io.ipfs.videoshare.Fragment.ForeFragment;
import io.ipfs.videoshare.Fragment.ThreeFragment;
import io.ipfs.videoshare.Fragment.TwoFragment;
import io.ipfs.videoshare.Updata.OkGoUpdateHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    protected static int messageQueueIndexId = 0;
    @InjectView(R.id.navigationBar)
    EasyNavigationBar navigationBar;
    private String hash = "bafybeifx7yeb55armcsxwwitkymga5xf53dxiarykms3ygqic223w5sk3m";
    List<String> okurl = new ArrayList<>();
    Button button, update;
    public String head = App.updata_url_head;
    public String urlurl = App.updata_url;
    private String json_all;
    public static MainActivity _main;

    private String[] tabText = {"", "", "", "设置"};
    //未选中icon
    private int[] normalIcon = {R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.shezhi};
    //选中时icon
    private int[] selectIcon = {R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.shezhi};

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        _main = this;


        fragments.add(new FirstFragment());
        fragments.add(new FirstFragment());
        fragments.add(new FirstFragment());
        fragments.add(new ForeFragment());

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .normalTextColor(Color.parseColor("#666666"))
                .selectTextColor(Color.parseColor("#333333"))
                .canScroll(true)
                .anim(Anim.BounceIn)
                .build();

        updata(App.default_getway,App.updata_hash);
        button = findViewById(R.id.button);
        update = findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, okurl.size() + "", Toast.LENGTH_SHORT).show();
            }
        });

        Gson gson = new Gson();
        String[] server_string = gson.fromJson(App.serverAdd, String[].class);
//        for (int i = 0; i < server_string.length; i++) {
//            getAsyn(server_string[i].replace(":hash", hash));
//        }

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
                    Log.e("xxx", result.length() + "              " + response.request().url());
                    if (result.length() == 31) {
                        Log.e("----------------", "");
                        okurl.add(response.request().url().toString().replace(hash, ":hash"));
                    } else {
                        Log.e("xxxxxxxxxxxxxxxxxx", "");
                    }
                }
            }
        });
    }

    private void updata(String getway,String hash) {
        if(getway.indexOf("/ipfs/:hash") == -1)
        {
            getway=App.default_getway;
        }
        getway=getway.replace("/ipfs/:hash","/ipns/"+hash);
        getway=getway+"/update.json";

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Map<String, String> params = new HashMap<String, String>();
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(MainActivity.this)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(getway)
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
                            if (big > getVersionCode(MainActivity.this)) {
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
