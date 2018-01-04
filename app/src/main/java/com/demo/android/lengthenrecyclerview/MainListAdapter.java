package com.demo.android.lengthenrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 小adp
 * Created by AllenHan on 2017/12/28.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder>{

    private Context mContext;
    private List<String> dataNam;
    private List<Integer> dataNum;
    private OnClickListener mOnClickListener = null;

    public MainListAdapter(Context context, List<String> data, List<Integer> datan){
        this.mContext = context;
        this.dataNam = data;
        this.dataNum = datan;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_recy, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvSituName.setText(dataNam.get(position));
        holder.ivDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onDeleteIconClick(v, position);
            }
        });
        if(position == (dataNam.size() - 1) && position != 0){
            holder.ivDeleteIcon.setVisibility(View.VISIBLE);
        }else{
            holder.ivDeleteIcon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataNam.size();
    }

    public void setOnClickListener(OnClickListener mOnClickListener){
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnClickListener{
        void onDeleteIconClick(View view, int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_delete_icon)
        ImageView ivDeleteIcon;
        @BindView(R.id.tv_name)
        TextView tvSituName;
        @BindView(R.id.tv_num)
        TextView tvSituNum;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
