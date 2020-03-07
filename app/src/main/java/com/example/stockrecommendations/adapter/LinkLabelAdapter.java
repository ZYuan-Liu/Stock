package com.example.stockrecommendations.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.stockrecommendations.R;
import com.example.stockrecommendations.entity.LinkLabelBean;

import java.util.List;

/**
 * Created by lzy123 on 2018/4/18.
 */

public class LinkLabelAdapter extends RecyclerView.Adapter {

    private Context context;
    private final int TITLE = 99;
    private final int CONTENT = 100;
    private List<LinkLabelBean> list;

    public LinkLabelAdapter(Context context, List<LinkLabelBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TITLE) {
            holder = new TitleHolder(LayoutInflater.from(context).inflate(R.layout.activity_label_title, null));
        } else {
            holder = new LabelHolder(LayoutInflater.from(context).inflate(R.layout.activity_label_content, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TITLE) {
            ((TitleHolder) holder).setData(position);
        } else {
            ((LabelHolder) holder).setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getStr().contains("标题")) {
            return TITLE;
        } else {
            return CONTENT;
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        private final TextView adapterTitle;
        private final ImageView adapterTitleIv;

        public TitleHolder(View inflate) {
            super(inflate);
            adapterTitle = inflate.findViewById(R.id.adater_title);
            adapterTitleIv = inflate.findViewById(R.id.adapter_iv);
        }

        @SuppressLint("WrongConstant")
        public void setData(int position) {
            switch (list.get(position).getStr()) {
                case "标题1":
                    adapterTitleIv.setImageResource(R.drawable.flag);
                    adapterTitle.setText("股票池");
                    break;
                case "标题2":
                    adapterTitleIv.setImageResource(R.drawable.flag_fen);
                    adapterTitle.setText("公司规模");
                    break;
                case "标题3":
                    adapterTitleIv.setImageResource(R.drawable.flag_blue);
                    adapterTitle.setText("行业板块");
                    break;
                case "标题4":
                    adapterTitleIv.setImageResource(R.drawable.flag_red);
                    adapterTitle.setText("地区板块");
                    break;

                default:
                    adapterTitleIv.setVisibility(View.GONE);
                    adapterTitle.setVisibility(View.GONE);
                    break;
            }
        }
    }

    private class LabelHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private final LinearLayout llContent;

        public LabelHolder(View inflate) {
            super(inflate);
            textView = (TextView) inflate.findViewById(R.id.textViewContent);
            llContent = (LinearLayout) inflate.findViewById(R.id.llContent);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 50;
            llContent.setLayoutParams(layoutParams);
        }

        public void setData(final int position) {
            textView.setText(list.get(position).getStr());
            if (list.get(position).isSelect()) {
                textView.setSelected(true);
            } else {
                textView.setSelected(false);
            }  //每次滑动的时候重新设置一下标签选中状态，防止recyclerview回收导致混乱
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textView.isSelected()) {
                        textView.setSelected(false);
                        list.get(position).setSelect(false);
                    } else {
                        textView.setSelected(true);
                        list.get(position).setSelect(true);
                    }

                }
            });
        }


    }
}
