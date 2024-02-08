package com.digid.eddokenaCM.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.R;

import java.util.List;

public class PopupVh extends RecyclerView.ViewHolder {
    private TextView productName;
    private TextView productPrice;
    public PopupVh(@NonNull View itemView) {
        super(itemView);

        productName =  itemView.findViewById(R.id.productNameTv);
        productPrice = itemView.findViewById(R.id.promotionPriceTv);

    }
    public static class PopupAdapter extends RecyclerView.Adapter<PopupVh> {

        private Context context;
        private List<Article> articleList;

        public PopupAdapter(Context context, List<Article> articleList) {
            this.context = context;
            this.articleList = articleList;
        }

        @NonNull
        @Override
        public PopupVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PopupVh(LayoutInflater.from(context).inflate(R.layout.promotion_item,parent , false));

        }

        @Override
        public void onBindViewHolder(@NonNull PopupVh holder, int position) {
            Double prixApi;

                if(articleList.get(position).getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage()!=null)
                {
                    prixApi = articleList.get(position).getArticlePrices().get(0).getPcsPrices().get(0).getAmount() *
                            (1- articleList.get(0).getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() );
                }else {
                    prixApi =articleList.get(position).getArticlePrices().get(0).getPcsPrices().get(0).getAmount();
                }



            holder.productName.setText(articleList.get(position).getNameFr());
            holder.productPrice.setText("Nouveau Prix : "+String.valueOf(prixApi)+" DT ");

        }

        @Override
        public int getItemCount() {
            return articleList.size();
        }
    }
}


