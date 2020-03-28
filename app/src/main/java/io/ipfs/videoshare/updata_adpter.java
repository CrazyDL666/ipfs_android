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

/**
 * Created by Administrator on 2020/3/26.
 */

public class updata_adpter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private OnWtglItemListener onItemClick;
    private LayoutInflater mInflater = null;
    private Context context;
    private updata_bean updata_bean;

    public updata_adpter(Context context, updata_bean updata_bean, OnWtglItemListener onItemClik) {
        layoutInflater = LayoutInflater.from(context);
        this.onItemClick = onItemClik;
        this.mInflater = LayoutInflater.from(context);
        this.onItemClick = onItemClik;
        this.context = context;
        this.updata_bean = updata_bean;
    }


    public interface OnWtglItemListener {
        void OnWtglItemCliek(String down_url);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return updata_bean.getData().size();
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
        holder.title.setText(updata_bean.getData().get(position).getTitle());
        holder.time.setText(formatData("yyyy-MM-dd", updata_bean.getData().get(position).getDatetime()));


        holder.kuai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.OnWtglItemCliek(updata_bean.getData().get(position).getApk_file());

            }
        });

        return convertView;
    }

    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
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
