package io.ipfs.videoshare.ipfs_util;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ipfs.gomobile.android.IPFS;
import ipfs.gomobile.android.RequestBuilder;

public class Util implements _Ipfs {
    private IPFS ipfs;

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


}
