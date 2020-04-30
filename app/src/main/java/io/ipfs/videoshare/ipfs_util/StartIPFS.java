package io.ipfs.videoshare.ipfs_util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import io.ipfs.videoshare.Fragment.ForeFragment;
import io.ipfs.videoshare.MainActivity;
import ipfs.gomobile.android.IPFS;

public final class StartIPFS extends AsyncTask<Void, Void, String> {
    private static final String TAG = "StartIPFS";

    private WeakReference<ForeFragment> activityRef;
    private boolean backgroundError;

    public StartIPFS(ForeFragment activity) {
        activityRef = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... v) {
        ForeFragment activity = activityRef.get();
        if (activity == null || activity.isRemoving()) {
            cancel(true);
            return null;
        }
        try {
            IPFS ipfs = new IPFS(activity.getContext());
            ipfs.start();
           // Log.e("getRepoPath",ipfs.getRepoPath());
            ArrayList<JSONObject> jsonList = ipfs.newRequest("/id").sendToJSONList();

           // ArrayList<JSONObject> jsonList2 = ipfs.newRequest("/swarm/peers").sendToJSONList();



            byte[] infoRaw = ipfs.newRequest("add")
                    .withBody("123")
                    .send();
            JSONObject infoJSON = new JSONObject(new String(infoRaw));
            Log.e("xxxx",infoJSON.toString());



            activity.setIpfs(ipfs);
           // Log.e("xxxxxx",jsonList2.toString()+"///"+jsonList2.);
            return jsonList.get(0).getString("ID");
        } catch (Exception err) {
            err.printStackTrace();
            backgroundError = true;
            return "false";
        }

    }


    protected void onPostExecute(String result) {
        ForeFragment activity = activityRef.get();
        if (activity == null || activity.isRemoving()) return;

        if (backgroundError) {
            Log.e(TAG, "IPFS start error: " + result);
        } else {
            Log.i(TAG, "Your PeerID is: " + result);
            activity.displayPeerIDResult(result);
        }
    }
}
