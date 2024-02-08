package com.digid.eddokenaCM.Menu.CatalogueArticle;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.Deal;
import com.digid.eddokenaCM.R;

import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyHolder> implements Filterable {

    private List<Article> dataList;
    private List<Article> dataListFull;
    private Context context;

    public ArticlesAdapter(List<Article> dataList) {
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemarticle, parent, false);
        context = parent.getContext();
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Log.i("TestArticleSelection", "-- "+dataList.get(position).getNameFr());
        Log.i("TestArticleSelection", "-- "+dataList.get(position).getArticlePrices());
        Log.i("TestArticleSelection", "-- "+dataList.get(position).getArticlePcs());
        Log.i("TestArticleSelection", "-- "+dataList.get(position).getArticleCatalogs());

        holder.articleNomTv.setText(dataList.get(position).getNameFr());
        holder.articlePromo.setVisibility(View.GONE);
        holder.articlePrixTv.setText(dataList.get(position).getSageReference());


//        if (dataList.get(position).getPromo() != null) {
//            if (dataList.get(position).getPromo().equals("Oui") || dataList.get(position).getPromo().equals("oui") || dataList.get(position).getPromo().equals("OUI")) {
//                holder.articleCv.setBackgroundResource(R.drawable.article_promo_shape);
//                holder.articlePromo.setVisibility(View.VISIBLE);
//            } else if (dataList.get(position).getPromo().equals("Non") || dataList.get(position).getPromo().equals("non") || dataList.get(position).getPromo().equals("NON") || dataList.get(position).getPromo().equals("")) {
//                holder.articlePromo.setVisibility(View.GONE);
//            }
//        } else {
//            holder.articlePromo.setVisibility(View.GONE);
//
//        }

        if (dataList.get(position).getType().equals("product")){
            holder.articleDispo.setText("Veuillez selectionner un catalogue");

            if (dataList.get(position).getCurrentStock() >0 ) {
                holder.articleDispo.setText("En stock");
                holder.articleDispo.setTextColor(Color.parseColor("#32cb00"));
            } else {
                holder.articleDispo.setText("Hors stock");
                holder.articleDispo.setTextColor(Color.parseColor("#d50000"));
            }

        } else {
            holder.articleDispo.setText("Deal");
            holder.articlePrixTv.setVisibility(View.VISIBLE);
            String desc = "Contenu(s) :\n\n";
            for (Deal deal : dataList.get(position).getDealItems()
                    ) {
                desc+=deal.getArticleName() + " - "+deal.getPackingType()+ " - "+deal.getDiscountedPrice()+ " TND";
                desc+="\n";
            }
            holder.articlePrixTv.setText(desc);
            holder.articleDispo.setTextColor(Color.parseColor("#ff9800"));

        }

//        String str = dataList.get(position).getArPhoto().replace("\\", "#");
//        String[] separated = str.split("#");
//        Picasso.get()
//
//                // Lenovo :
//                //.load(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/.ArticleImgs/Multimedia/"+separated[separated.length-1]))
//
//                // Huawei :
//
//                .load(new File(Environment.getExternalStorageDirectory() + "/.ArticleImgs/Multimedia/" + separated[separated.length - 1]))
//                .placeholder(R.drawable.iconempty)
//                .error(R.drawable.iconempty)
//                .into(holder.articleIv);

//        Log.i("testtphoto", "onBindViewHolder: " + dataList.get(position).getArDesign() + " --- "+ separated[separated.length-1]);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView articlePrixTv;
        private TextView articleNomTv;
        private TextView articleDispo;
        private TextView articlePromo;
        private ImageView articleIv;
        private LinearLayout articleCv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            articlePrixTv = itemView.findViewById(R.id.comarticlecomprix_tv);
            articleNomTv = itemView.findViewById(R.id.comarticlecomname_tv);
            articlePromo = itemView.findViewById(R.id.articlepromo_tv);
            articleDispo = itemView.findViewById(R.id.comarticledispo_tv);
            articleIv = itemView.findViewById(R.id.articlecomimage_iv);
            articleCv = itemView.findViewById(R.id.articleco_ll);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {


        }
    }

    @Override
    public Filter getFilter() {
        return filterFn;
    }

    public Filter filterFn = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Article> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(dataListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Article item : dataListFull) {
                    if (item.getNameFr().toUpperCase().contains(filterPattern) || item.getNameFr().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            try {
                dataList.addAll((List) results.values);

            }catch (Exception e){
                Log.d("Exception",e.getMessage());
            }
            notifyDataSetChanged();
        }


    };


}
