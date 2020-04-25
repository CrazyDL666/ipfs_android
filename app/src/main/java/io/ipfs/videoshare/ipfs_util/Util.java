package io.ipfs.videoshare.ipfs_util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.ipfs.videoshare.App;
import io.ipfs.videoshare.Bean.NamecacheBean;
import ipfs.gomobile.android.IPFS;
import ipfs.gomobile.android.RequestBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util implements _Ipfs {
    private IPFS ipfs;
    private DatabaseHelper dbHelper;
    //初始化
    @Override
    public Boolean startipfs(final Activity activity) {
        try {
            ipfs= new IPFS(activity);
            ipfs.start();
            return true;
        } catch (IPFS.ConfigCreationException e) {
            e.printStackTrace();
        } catch (IPFS.RepoInitException e) {
            e.printStackTrace();
        } catch (IPFS.SockManagerException e) {
            e.printStackTrace();
        } catch (IPFS.RepoOpenException e) {
            e.printStackTrace();
        } catch (IPFS.NodeStartException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean startipfs(Activity activity, String url) {
        return null;
    }

    @Override
    public String get_id() {
        if(!ipfs.isStarted())return null;
        try {
            ArrayList<JSONObject> jsonList = ipfs.newRequest("/id").sendToJSONList();
            return jsonList.get(0).getString("ID");
        } catch (RequestBuilder.RequestBuilderException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IPFS.ShellRequestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map get_netstat() {
        ArrayList<JSONObject> jsonList = null;
        try {
            Map<String, Integer> out;
            out = new HashMap();

            jsonList = ipfs.newRequest("/stats/bw").sendToJSONList();
            int TotalIn = jsonList.get(0).getInt("TotalIn");
            int TotalOut = jsonList.get(0).getInt("TotalOut");
            int RateIn = (int)jsonList.get(0).getDouble("RateIn");
            int RateOut = (int)jsonList.get(0).getDouble("RateOut");
            jsonList = ipfs.newRequest("/swarm/peers").sendToJSONList();
            int peers = jsonList.get(0).getJSONArray("Peers").length();
            out.put("TotalIn", TotalIn);
            out.put("TotalOut", TotalOut);
            out.put("RateIn", RateIn);
            out.put("RateOut", RateOut);
            out.put("peers", peers);
            return out;
        } catch (RequestBuilder.RequestBuilderException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IPFS.ShellRequestException e) {
            e.printStackTrace();
        }
//        return jsonList.get(0).getString("ID");
        return null;
    }

    @Override
    public String resolvecache(String ipns,Context context, String lable, String ipfsgw) throws IPFS.ShellRequestException, JSONException, RequestBuilder.RequestBuilderException {
        String ipfs ;
        dbHelper = new DatabaseHelper(context, "ipfs", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        NamecacheBean cache = new NamecacheBean();
        Cursor c = db.rawQuery("select ipns,ipfs,cachetime,app from ipnscache where ipns= ? limit 1", new String[] { ipns });
        while (c.moveToNext()) {
            cache.setIpns(c.getString(0));
            cache.setIpfs(c.getString(1));
            cache.setCachetime(c.getString(2));
            cache.setApp(c.getString(3));
        }
        c.close();
        if(cache.getIpfs() == null){
//            TODO 启异步线程获取ipfs值，只要一个最快的就可以了。
            ipfs = this.resolve(ipns);
            ipfs = getAsyn(ipfsgw.replace("ipfs/:hash", "ipns/"+ipns));

            db.execSQL("INSERT INTO ipnscache(ipns, ipfs, cachetime, app) values(?,?,?,?)",
                    new String[]{ipns,ipfs,now, lable});

            return ipfs;
        }else {
            if (now - cache.getCachetime() > 3600){
//                TODO 异步获取ipns
                ipfs = this.resolve(ipns);
                db.execSQL("INSERT INTO ipnscache(ipns, ipfs, cachetime, app) values(?,?,?,?)",
                        new String[]{ipns,ipfs,now, lable});


                return cache.getIpfs();
            }else {
                return cache.getIpfs();
            }
        }






        return null;
    }
    public String getAsyn(String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //...
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.header("X-Ipfs-Pat");
                    return result;
                }
            }
        });
    }

    @Override
    public String resolve(String ipns) throws IPFS.ShellRequestException, RequestBuilder.RequestBuilderException, JSONException {
        ArrayList<JSONObject> jsonList = ipfs.newRequest("/name/resolve").withArgument(ipns).sendToJSONList();
        return jsonList.toString();
    }

    @Override
    public String get_json(String ipfspath) throws IPFS.ShellRequestException, RequestBuilder.RequestBuilderException, JSONException {
        ArrayList<JSONObject> jsonList = null;
        jsonList = ipfs.newRequest("/cat").withArgument(ipfspath).sendToJSONList();
        return jsonList.toString();
    }

    @Override
    public String get_updatejson(Context context) throws IPFS.ShellRequestException, RequestBuilder.RequestBuilderException, JSONException {
        String hash = this.resolvecache(App.updata_hash,context, "APK",App.ipfsgw);
        return this.get_json("/"+hash+'/'+"update.json");
    }


}
