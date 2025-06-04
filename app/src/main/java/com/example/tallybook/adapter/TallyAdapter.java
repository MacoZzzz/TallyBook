package com.example.tallybook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tallybook.R;
import com.example.tallybook.bean.Tally;

import java.util.ArrayList;
import java.util.List;

public class TallyAdapter extends RecyclerView.Adapter<TallyAdapter.ViewHolder> {
    private List<Tally> list =new ArrayList<>();
    private Context mActivity;
    private ItemListener mItemListener;
    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    public TallyAdapter(){
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mActivity = viewGroup.getContext();
        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_rv_tally_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Tally tally = list.get(i);
        if (tally != null) {
            viewHolder.type.setText(tally.getTypeId()==0?"收入":"支出");
            viewHolder.money.setText(String.format("%s",tally.getMoney()));
            viewHolder.remark.setText(tally.getRemark());
            viewHolder.date.setText(tally.getDate());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener!=null){
                        mItemListener.ItemClick(tally);
                    }
                }
            });
           viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   if (mItemListener!=null){
                       mItemListener.ItemLongClick(tally);
                   }
                   return false;
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItem(List<Tally> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView type;
        private TextView money;
        private TextView remark;
        private TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            money = itemView.findViewById(R.id.money);
            date = itemView.findViewById(R.id.date);
            remark = itemView.findViewById(R.id.remark);
        }
    }

    public interface ItemListener{
        void ItemClick(Tally tally);
        void ItemLongClick(Tally tally);
    }
}
