package io.ipfs.videoshare;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ipfs.gomobile.android.IPFS;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2020/3/26.
 */

public class erweima extends Activity {
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.verson)
    TextView verson2;
    @InjectView(R.id.down)
    Button down;
    String aa;//下载链接
    String down_url;
    String name;
    String verson;
    String fileName="VidioShare.apk";
    boolean is_doswn=false;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erweima);
        ButterKnife.inject(this);
        down_url = getIntent().getStringExtra("down_url");//获取接口data
        try{
            name = getIntent().getStringExtra("name");
            verson = getIntent().getStringExtra("verson");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(name!=null && verson!=null){
            verson2.setText(verson);
            title.setText(name);
        }
        String getway=App.default_getway;
        getway=getway.replace("/ipfs/:hash","/ipns/"+App.updata_hash);
        aa= getway+"/"+down_url;
        Bitmap mBitmap = CodeUtils.createImage(aa, 400, 400, null);
        image.setImageBitmap(mBitmap);


        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyStoragePermissions(erweima.this);

                if(is_doswn){
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(aa).build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i(TAG, "请求失败: " + e.getLocalizedMessage());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    ResponseBody body = response.body();
                                    if (body != null) {
                                        writeFile(body);
                                    }
                                }
                            }
                        });

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(aa);
                Toast.makeText(erweima.this,"复制成功："+aa,Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void writeFile(ResponseBody body) {
        InputStream is = null;  //网络输入流
        FileOutputStream fos = null;  //文件输出流

        is = body.byteStream();

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
        Log.e("xxxx",filePath);
        File file = new File(filePath);
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            long totalSize = body.contentLength();  //文件总大小
            long sum = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / totalSize * 100);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.arg1 = progress;
                handler.sendMessage(msg);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Toast.makeText(erweima.this,"下载成功 路径："+filePath,Toast.LENGTH_SHORT).show();
    }

    private  void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }else{
                is_doswn=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int progress = msg.arg1;
                    Log.e("ccc",progress+"");
                   // mProgressBar.setProgress(progress);
                    break;

                default:
                    break;
            }
        }
    };

}
