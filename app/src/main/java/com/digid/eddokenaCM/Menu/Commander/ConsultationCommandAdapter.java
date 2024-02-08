package com.digid.eddokenaCM.Menu.Commander;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Utils.SessionManager;

import java.text.DecimalFormat;
import java.util.List;

public class ConsultationCommandAdapter extends RecyclerView.Adapter<ConsultationCommandAdapter.MyHolder> {

    private static DecimalFormat df = new DecimalFormat("0.0000");
    private List<OrderItem> dataList;
    private Context context;
    private OnClientListner mOnclientListner;

    public ConsultationCommandAdapter(List<OrderItem> dataList, OnClientListner mOnclientListner) {
        this.dataList = dataList;
        this.mOnclientListner = mOnclientListner;
    }

    @NonNull
    @Override
    public ConsultationCommandAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcommandesanscorb, parent, false);
        context=parent.getContext();
        return new ConsultationCommandAdapter.MyHolder(view, mOnclientListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationCommandAdapter.MyHolder holder, int position) {

        if ((SessionManager.getInstance().getUserType(context)==0)||(SessionManager.getInstance().getUserType(context)==5)){
            holder.articleQteeTv.setBackgroundResource(R.drawable.textviewshape);
            holder.articlePrixPieceTv.setVisibility(View.VISIBLE);
            holder.articlePrixTotalTv.setVisibility(View.VISIBLE);
        }

        holder.articleNomTv.setText(dataList.get(position).getArticleName());
        holder.articleCondTv.setText(dataList.get(position).getPackingType());
        holder.articleQteeTv.setText("Qte : " + dataList.get(position).getQty());
        holder.articlePrixPieceTv.setText("Pu : " + df.format(dataList.get(position).getTotalAmount() / dataList.get(position).getQty()) + " TND");
        holder.articlePrixTotalTv.setText(df.format(dataList.get(position).getTotalAmount()) + " TND");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView articlePrixTotalTv;
        private TextView articlePrixPieceTv;
        private TextView articleNomTv;
        private TextView articleQteeTv;
        private TextView articleCondTv;
        private OnClientListner onClientListner;


        public MyHolder(@NonNull View itemView, OnClientListner onClientListner) {
            super(itemView);

            articlePrixTotalTv = itemView.findViewById(R.id.commande_articlePrixtotal_tv);
            articlePrixPieceTv = itemView.findViewById(R.id.commande_articlePrixPiece_tv);
            articleNomTv = itemView.findViewById(R.id.commande_articlenom_tv);
            articleQteeTv = itemView.findViewById(R.id.commande__articleqte_tv);
            articleCondTv = itemView.findViewById(R.id.commande_articlearref_tv);
            this.onClientListner = onClientListner;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onClientListner.onClientClick(getAdapterPosition());


        }
    }

    public interface OnClientListner {
        void onClientClick(int position);
    }
}
