package io.ipfs.videoshare;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2020/3/26.
 */

public class erweima extends Activity {
    @InjectView(R.id.image)
    ImageView image;
    String aa;
    String down_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erweima);
        ButterKnife.inject(this);
        down_url = getIntent().getStringExtra("down_url");
        aa= MainActivity._main.head+down_url;
        Bitmap mBitmap = CodeUtils.createImage(aa, 400, 400, null);
        image.setImageBitmap(mBitmap);

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
}
