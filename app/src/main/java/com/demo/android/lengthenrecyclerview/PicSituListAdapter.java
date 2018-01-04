package com.demo.android.lengthenrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 大adp
 * Created by AllenHan on 2018/1/2.
 */

public class PicSituListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //类型
    private enum ITEM_TYPE{
        ITEM1,
        ITEM2
    }

    private Context mContext;
    private List<String> dataNam;
    private List<Integer> dataType = new ArrayList<>();
    private OnClickListener mOnClickListener = null;
    private List<MainListAdapter> listAdp = new ArrayList<>();

    List<List<String>> nameList = new ArrayList<>();
    List<List<Integer>> numList = new ArrayList<>();
    List<Integer> muSize = new ArrayList<>();

    int recyPos;

    int item1Num;

    public PicSituListAdapter(Context context, List<String> data, List<Integer> dataType, int item1Num) {
        this.mContext = context;
        this.dataNam = data;
        this.dataType = dataType;
        this.item1Num = item1Num;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.ITEM1.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
            final RecyclerView.ViewHolder viewHolder = new RecyViewHolder(view);
            return viewHolder;
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_2, parent, false);
            final RecyclerView.ViewHolder viewHolder = new ItemViewHolder(view);
            viewHolder.setIsRecyclable(false);
            return viewHolder;
        }
    }

    //设置ITEM类型 1就是item1 否则item2
    @Override
    public int getItemViewType(int position) {
        return dataType.get(position) == 1 ? ITEM_TYPE.ITEM1.ordinal() : ITEM_TYPE.ITEM2.ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder)holder).tvName.setText(dataNam.get(position));
        }else if(holder instanceof RecyViewHolder){
            //变长 recyclerview做item的item
            ((RecyViewHolder)holder).tvTitle.setText(dataNam.get(position));

            recyPos = position - item1Num;

            //recyPos 类似 RecyViewHolder的position
            if(recyPos == nameList.size() || ( recyPos == 0 && nameList.size() == 0) ){
                nameList.add(new ArrayList<String>());
                numList.add(new ArrayList<Integer>());
                muSize.add(1);
                nameList.get(recyPos).add(dataNam.get(position) + "01");
                listAdp.add(new MainListAdapter(mContext, nameList.get(recyPos), numList.get(recyPos)));
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            ((RecyViewHolder)holder).rvItem.setNestedScrollingEnabled(false);
            ((RecyViewHolder)holder).rvItem.setLayoutManager(linearLayoutManager);

            ((RecyViewHolder)holder).rvItem.setAdapter(listAdp.get(recyPos));
            listAdp.get(recyPos).setOnClickListener(new MainListAdapter.OnClickListener() {
                @Override
                public void onDeleteIconClick(View view, int po) {
                    recyPos = position - item1Num;
                    listAdp.get(recyPos).notifyItemRemoved(po);
                    nameList.get(recyPos).remove(po);

                    muSize.set(recyPos, muSize.get(recyPos) - 1);
                    listAdp.get(recyPos).notifyDataSetChanged();
                    mOnClickListener.onDeleteIconClick(view, po);
                }
            });


            ((RecyViewHolder)holder).ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyPos = position - item1Num;
                    if(muSize.get(recyPos) < 9) {
                        muSize.set(recyPos, muSize.get(recyPos) + 1);
                        nameList.get(recyPos).add(dataNam.get(position) + "0" + muSize.get(recyPos));
                        listAdp.get(recyPos).notifyItemInserted(muSize.get(recyPos) - 1);
                        listAdp.get(recyPos).notifyDataSetChanged();

                    }else if(muSize.get(recyPos) <= 11){

                        muSize.set(recyPos, muSize.get(recyPos) + 1);

                        nameList.get(recyPos).add(dataNam.get(position) + muSize.get(recyPos));
                        listAdp.get(recyPos).notifyItemInserted(muSize.get(recyPos) - 1);
                        listAdp.get(recyPos).notifyDataSetChanged();

                    }else{
                        Toast.makeText(mContext, "不能再增加了", Toast.LENGTH_SHORT).show();
                    }
                    mOnClickListener.onAddIconClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataNam.size();
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnClickListener {
        void onAddIconClick(View view, int position);

        void onDeleteIconClick(View view, int position);
    }


    class RecyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rv_item)
        RecyclerView rvItem;
        @BindView(R.id.iv_add)
        ImageView ivAdd;

        public RecyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name2)
        TextView tvName;
        @BindView(R.id.tv_num2)
        TextView tvNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
