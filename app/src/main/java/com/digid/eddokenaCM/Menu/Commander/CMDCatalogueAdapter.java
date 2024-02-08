package com.digid.eddokenaCM.Menu.Commander;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Menu.CatalogueArticle.CatalogueAdapter;
import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.R;

import java.util.ArrayList;
import java.util.List;

public class CMDCatalogueAdapter extends RecyclerView.Adapter<CMDCatalogueAdapter.MyHolder> implements Filterable {


    private List<Catalog> dataList;
    private List<Catalog> dataListFull;
    private NavController navController;
    private CatalogueAdapter.OnCatalogueListner mOnclientListner;

    public CMDCatalogueAdapter(List<Catalog> dataList, CatalogueAdapter.OnCatalogueListner mOnclientListner) {
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
        this.mOnclientListner = mOnclientListner;
    }

    @NonNull
    @Override
    public CMDCatalogueAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcatalogue, parent, false);
        navController = Navigation.findNavController(parent);
        return new CMDCatalogueAdapter.MyHolder(view, mOnclientListner);
    }

    @Override
    public void onBindViewHolder(@NonNull CMDCatalogueAdapter.MyHolder holder, int position) {
        holder.catalogueNameTv.setText(dataList.get(position).getNameFr());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView catalogueNameTv;
        private CatalogueAdapter.OnCatalogueListner onCatalogueListner;


        public MyHolder(@NonNull View itemView, CatalogueAdapter.OnCatalogueListner onCatalogueListner) {
            super(itemView);
            catalogueNameTv = itemView.findViewById(R.id.itemcatalogue_name_tv);
            this.onCatalogueListner = onCatalogueListner;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onCatalogueListner.onCatalogueClick(getAdapterPosition());

        }
    }

    public interface onClickListner {
        void onCategorieClick(int position);
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
