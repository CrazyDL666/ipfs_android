package io.ipfs.videoshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.ipfs.videoshare.bean.VideoBean;

/**
 * Created by Administrator on 2020/3/26.
 */

public class video_adpter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private OnWtglItemListener onItemClick;
    private LayoutInflater mInflater = null;
    private Context context;
    private VideoBean videoBean;

    public video_adpter(Context context, VideoBean videoBean, OnWtglItemListener onItemClik) {
        layoutInflater = LayoutInflater.from(context);
        this.onItemClick = onItemClik;
        this.mInflater = LayoutInflater.from(context);
        this.onItemClick = onItemClik;
        this.context = context;
        this.videoBean = videoBean;
    }


    public interface OnWtglItemListener {
        void OnWtglItemCliek(String down_url,String title);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return videoBean.getFiles().size();
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.updata_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(videoBean.getFiles().get(position).getTitle());
        holder.time.setText("");

        holder.kuai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.OnWtglItemCliek(videoBean.getFiles().get(position).getUrl(),videoBean.getFiles().get(position).getTitle());

            }
        });

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.time)
        TextView time;
        @InjectView(R.id.kuai)
        RelativeLayout kuai;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
