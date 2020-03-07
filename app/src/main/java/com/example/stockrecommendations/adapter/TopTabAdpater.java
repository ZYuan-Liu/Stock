package com.example.stockrecommendations.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockrecommendations.R;

import java.util.List;


public class TopTabAdpater extends RecyclerView.Adapter<TopTabAdpater.TabViewHolder> {


    private Context context;
    private List<String> datas;

    public TopTabAdpater(Context context) {
        this.context = context;
    }


    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_selection_item2, viewGroup, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder tabViewHolder, int i) {
        tabViewHolder.mTabTv.setText(datas.get(i));
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    class TabViewHolder extends RecyclerView.ViewHolder {

        TextView mTabTv;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            mTabTv = itemView.findViewById(R.id.tv_right_scroll);
        }
    }

}
