package com.digid.eddokenaCM.Menu.CatalogueClient;

import android.util.Log;
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

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.R;

import java.util.ArrayList;
import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.MyHolder> implements Filterable {

    private List<Client> dataList;
    private List<Client> dataListFull;
    private NavController navController;
    private OnClientListner mOnclientListner;


    public ClientsAdapter(List<Client> dataList, OnClientListner onClientListner) {
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
        mOnclientListner = onClientListner;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemclientsanspanier, parent, false);
        navController = Navigation.findNavController(parent);
        return new ClientsAdapter.MyHolder(view, mOnclientListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Log.i("TestClientSelection", "-> "+dataList.get(position).getFirstName()+" "+dataList.get(position).getLastName());
        Log.i("TestClientSelection", "-> "+dataList.get(position).getShopLat()+" --- "+dataList.get(position).getShopLon());
        Log.i("TestClientSelection", "-> "+dataList.get(position).getCltClass()+" --- "+dataList.get(position).getCategoryName());

        holder.clientNameTv.setText(dataList.get(position).getFirstName()+" "+dataList.get(position).getLastName());
        holder.clientNumTv.setText(dataList.get(position).getSageCode());
        holder.clientTelephoneTv.setText(dataList.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        return filterFn;
    }

    public Filter filterFn = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Client> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(dataListFull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Client item : dataListFull) {
                    if (String.valueOf(item.getSageCode()).toLowerCase().contains(filterPattern)
                            || item.getLastName().toLowerCase().contains(filterPattern)
                            || item.getFirstName().toLowerCase().contains(filterPattern)) {
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
            dataList.addAll((List) results.values);
            notifyDataSetChanged();
        }


    };

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView clientNameTv;
        private TextView clientNumTv;
        private TextView clientTelephoneTv;
        private OnClientListner onClientListner;


        public MyHolder(@NonNull View itemView, OnClientListner onClientListner) {
            super(itemView);


            clientNameTv = itemView.findViewById(R.id.clientco_clientname_tv);
            clientNumTv = itemView.findViewById(R.id.clientco_clientnum_tv);
            clientTelephoneTv = itemView.findViewById(R.id.clientco_clientphone_tv);
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
