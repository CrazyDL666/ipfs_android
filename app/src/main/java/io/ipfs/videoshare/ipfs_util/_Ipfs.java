package io.ipfs.videoshare.ipfs_util;

import android.app.Activity;
import android.content.Context;

import com.google.gson.internal.bind.JsonTreeReader;

import org.json.JSONException;

import java.util.Map;

import ipfs.gomobile.android.IPFS;
import ipfs.gomobile.android.RequestBuilder;

public interface _Ipfs {
    /**
     * startipfs
     * @return
     */
    Boolean startipfs(Activity activity);
    Boolean startipfs(Activity activity, String url);
    String get_id();
    Map get_netstat();
//    String resolvecache(String ipns, Context context, String lable, String ipfsgw) throws IPFS.ShellRequestException, JSONException, RequestBuilder.RequestBuilderException;
    String resolve(String ipns) throws IPFS.ShellRequestException, RequestBuilder.RequestBuilderException, JSONException;
    String get_json(String ipfspath) throws IPFS.ShellRequestException, RequestBuilder.RequestBuilderException, JSONException;

//    String get_updatejson(Context context) throws IPFS.ShellRequestException, RequestBuilder.RequestBuilderException, JSONException;
    public String resolve_by_gateway(String ipns, String gateway);
    public String get_content_by_gateway(String ipfspath, String gateway);
}
