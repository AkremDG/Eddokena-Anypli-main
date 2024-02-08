package com.digid.eddokenaCM.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.Deal;
import com.digid.eddokenaCM.R;

import java.util.List;

public class MenuPromoRVAdapter extends RecyclerView.Adapter<MenuPromoRVAdapter.MyHolder> {

    private List<Article> dataList;
    private Context context;

    public MenuPromoRVAdapter(List<Article> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MenuPromoRVAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempromo, parent, false);
        context = parent.getContext();
        return new MenuPromoRVAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuPromoRVAdapter.MyHolder holder, int position) {

//        Log.i("fhfhfhfhfh", "onBindViewHolder: "+
//                dataList.get(position).getArticlePrices().get(0).getPcsPrices());

        holder.articleNomTv.setText(dataList.get(position).getNameFr() + "\n"
                );

        String desc = "Contenu(s) :\n\n";
        for (Deal deal : dataList.get(position).getDealItems()
        ) {
            desc+=deal.getArticleName() + " - "+deal.getQuantity()+ " "+
                    deal.getPackingType()+ " - "+deal.getDiscountedPrice()+ " TND";
            desc+="\n";
        }

        holder.articleDescTv.setText(desc);
        holder.articlePriceTv.setText("");


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView articleNomTv;
        private TextView articleDescTv;
        private TextView articlePriceTv;
        private ImageView articleIv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            articleNomTv = itemView.findViewById(R.id.comarticlecomname_tv);
            articleDescTv = itemView.findViewById(R.id.comarticlecomdesc_tv);
            articlePriceTv = itemView.findViewById(R.id.comarticlecomprice_tv);
            articleIv = itemView.findViewById(R.id.articlecomimage_iv);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {


        }
    }

}