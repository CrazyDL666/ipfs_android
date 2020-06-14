package io.ipfs.videoshare.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.ipfs.videoshare.R;
import io.ipfs.videoshare.bean.UserHashBean;

import static io.ipfs.videoshare.Fragment.FirstFragment.TYPE_FOOTER;
import static io.ipfs.videoshare.Fragment.FirstFragment.TYPE_HEADER;
import static io.ipfs.videoshare.Fragment.FirstFragment.TYPE_NORMAL;


/**
 * Created by DL on 2018/6/26.
 */



public class One_list_Adapter extends RecyclerView.Adapter<One_list_Adapter.MyViewHolder>{
    private Context context;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    private LayoutInflater inflater;
    private List<UserHashBean> userHashBeanList=new ArrayList<>();
    private View mHeaderView;
    private View mFooterView;

    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v, String hash);
    }

    public One_list_Adapter(Context context,List<UserHashBean> userHashBeanList, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener){
        this.userHashBeanList=userHashBeanList;
        this.context=context;

        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener=mOnRecyclerviewItemClickListener;
    }


    @Override
    public int getItemViewType(int position) {

        if (mHeaderView == null && mFooterView == null){
//普通item
            return TYPE_NORMAL;
        }else if (position == 0 && mHeaderView != null){
//第一个item应该加载Header
            return TYPE_HEADER;
        }else if (position == getItemCount()-1 && mFooterView != null){
//最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, final int viewType ) {

        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new MyViewHolder(mFooterView);
        }
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof MyViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了

                //((ListHolder) holder).tv.setText(mDatas.get(position-1));
                ((MyViewHolder) holder).kuai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnRecyclerviewItemClickListener.onItemClickListener(v,userHashBeanList.get(position).getHash());
                    }
                });
                holder.title.setText(userHashBeanList.get(position).getUser());
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }


    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return userHashBeanList.size();
        }else if(mHeaderView == null && mFooterView != null){
            return userHashBeanList.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return userHashBeanList.size() + 1;
        }else {
            return userHashBeanList.size() + 2;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout kuai;
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            kuai=view.findViewById(R.id.kuai);
            title=view.findViewById(R.id.title);


        }

    }


}


