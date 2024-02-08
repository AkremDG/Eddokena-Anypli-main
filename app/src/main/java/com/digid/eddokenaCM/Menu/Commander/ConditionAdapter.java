package com.digid.eddokenaCM.Menu.Commander;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.ArticlePricePcs;
import com.digid.eddokenaCM.R;

import java.util.List;

public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.MyHolder> {

    private List<ArticlePricePcs> dataList;
    private ConditionAdapter.onClickListner clickListner;

    public ConditionAdapter(List<ArticlePricePcs> dataList, ConditionAdapter.onClickListner clickListner) {
        this.dataList = dataList;
        this.clickListner = clickListner;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemconditionnement, parent, false);
        return new ConditionAdapter.MyHolder(view, clickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.condtionTv.setText(dataList.get(position).getType());
        Log.i("yyyyyyy", "onBindViewHolder: "+ dataList.size());
        Log.i("yyyyyyy", "onBindViewHolder: "+ dataList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView condtionTv;
        private ConditionAdapter.onClickListner clickListner;

        public MyHolder(@NonNull View itemView, ConditionAdapter.onClickListner clickListner) {
            super(itemView);
            condtionTv = itemView.findViewById(R.id.articlecom_conditionnement_tv);
            this.clickListner = clickListner;
            condtionTv.setOnClickListener(this::onClick);
        }


        @Override
        public void onClick(View v) {
            this.clickListner.onConditionItemClick(getAdapterPosition());
        }
    }

    public interface onClickListner {
        void onConditionItemClick(int position);
    }


}
