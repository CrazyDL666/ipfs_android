package io.ipfs.videoshare.ipfs_util;

import android.app.Activity;

import com.google.gson.internal.bind.JsonTreeReader;

import java.util.Map;

public interface _Ipfs {
    /**
     * startipfs
     * @return
     */
    Boolean startipfs(Activity activity);
    Boolean startipfs(Activity activity, String url);
    String get_id();
    Map get_netstat();
}
