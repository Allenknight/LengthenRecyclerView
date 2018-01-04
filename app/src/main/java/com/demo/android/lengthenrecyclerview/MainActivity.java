package com.demo.android.lengthenrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvPicSituList;
    PicSituListAdapter myAdp;
    List<String> muName = new ArrayList<>();
    List<Integer> mType = new ArrayList<>();
    int itemNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        rvPicSituList.setLayoutManager(new LinearLayoutManager(this));

        for(int i = 0; i < 30; i ++){
            muName.add("Item" + i);
            if(i< 20) {
                mType.add(0);
                itemNum++;
            }else{
                mType.add(1);
            }
        }


        myAdp = new PicSituListAdapter(this,muName, mType, itemNum);
        rvPicSituList.setAdapter(myAdp);
        myAdp.setOnClickListener(new PicSituListAdapter.OnClickListener() {
            @Override
            public void onAddIconClick(View view, int position) {
                myAdp.notifyDataSetChanged();
//                showToast("add");
            }

            @Override
            public void onDeleteIconClick(View view, int position) {
                myAdp.notifyDataSetChanged();
//                showToast("remove");
            }
        });
    }
}
