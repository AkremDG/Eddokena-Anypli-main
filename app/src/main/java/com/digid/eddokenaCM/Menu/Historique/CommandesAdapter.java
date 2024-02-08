package com.digid.eddokenaCM.Menu.Historique;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Utils.Utilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommandesAdapter extends RecyclerView.Adapter<CommandesAdapter.MyHolder> implements Filterable {

    private static DecimalFormat df = new DecimalFormat("0.000");
    private NavController navController;
    private Context context;
    private List<Order> dataList;
    private List<Order> dataListFull;
    private CommandesAdapter.OnClientListner mOnclientListner;
    private CommandesAdapter.OnValidListener mOnvalidListener;

    private static View mRootView;


    public CommandesAdapter(Context context, List<Order> dataList, OnClientListner mOnclientListner,OnValidListener mOnvalidListener) {
        this.context = context;
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
        this.mOnclientListner = mOnclientListner;
        this.mOnvalidListener = mOnvalidListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhist, parent, false);
        navController = Navigation.findNavController(parent);
        return new CommandesAdapter.MyHolder(view, mOnclientListner,mOnvalidListener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRootView = recyclerView.getRootView();
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.facutreTotalTv.setVisibility(View.VISIBLE);
        //todo les codes couleurs taa les commandes

        if (dataList.get(position).getStatus() != null) {



            if ((dataList.get(position).getStatus().equals("confirmed"))) {
                holder.validityTv.setBackgroundColor(Color.parseColor("#66ffa6"));
                holder.validBtn.setVisibility(View.INVISIBLE);
            } else if (dataList.get(position).getStatus().equals("new")&&dataList.get(position).getIdBo()==null) {
                holder.validityTv.setBackgroundColor(Color.parseColor("#ffeb3b"));
                holder.validBtn.setVisibility(View.VISIBLE);

            } else if (dataList.get(position).getStatus().equals("canceled")) {
                holder.validityTv.setBackgroundColor(Color.parseColor("#FF0000"));
                holder.validBtn.setVisibility(View.INVISIBLE);

            }else if (dataList.get(position).getStatus().equals("new")&&(dataList.get(position).getIdBo()!=null)) {
                holder.validityTv.setBackgroundColor(Color.parseColor("#f57f17"));
                holder.validBtn.setVisibility(View.VISIBLE);

            }else if (dataList.get(position).getStatus().equals("preparing")){
                holder.validityTv.setBackgroundColor(Color.parseColor("#1a237e"));
                holder.validBtn.setVisibility(View.INVISIBLE);
            }
        } else {
            Log.i("historique", "onBindViewHolder6: ");
            holder.validityTv.setBackgroundColor(Color.parseColor("#ffeb3b"));
            holder.validBtn.setVisibility(View.VISIBLE);
        }

        if(dataList.get(position).getClient()!=null) {
            holder.clientNomTv.setText(dataList.get(position).getClient().getLastName() + " " + dataList.get(position).getClient().getFirstName());
            holder.commercialCodeTv.setText(dataList.get(position).getClient().getSageCode());
        }

        if(dataList.get(position).getIdBo()==null){
            holder.factureCodeTv.setText("");

        }else{
            holder.factureCodeTv.setText(String.valueOf(dataList.get(position).getIdBo()));
        }
        holder.factureDateTv.setText(Utilities.getInstance().getDateFormat(dataList.get(position).getDoDate()));
        holder.facutreTotalTv.setText(df.format(dataList.get(position).getTotalAmount()) + " TND");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout histobjectLl;
        private TextView commercialCodeTv;
        private TextView factureCodeTv;
        private TextView factureDateTv;
        private TextView facutreTotalTv;
        private TextView clientNomTv;
        private TextView validityTv;
        private ImageView changeComBtn;
        private ImageView validBtn;
        private CommandesAdapter.OnClientListner onClientListner;
        private CommandesAdapter.OnValidListener onValidListener;


        public MyHolder(@NonNull View itemView, CommandesAdapter.OnClientListner onClientListner, CommandesAdapter.OnValidListener onValidListener) {
            super(itemView);

            histobjectLl = itemView.findViewById(R.id.hist_linerlayout);
            clientNomTv = itemView.findViewById(R.id.hist_collnom_tv);
            commercialCodeTv = itemView.findViewById(R.id.hist_collcode_tv);
            factureCodeTv = itemView.findViewById(R.id.hist_factcode_tv);
            factureDateTv = itemView.findViewById(R.id.hist_factdate_tv);
            facutreTotalTv = itemView.findViewById(R.id.hist_facttotal_tv);
            validityTv = itemView.findViewById(R.id.facturevalidity_tv);
            changeComBtn = itemView.findViewById(R.id.hist_change_iv);
            validBtn=itemView.findViewById(R.id.valid_iv);

            this.onClientListner = onClientListner;
            this.onValidListener = onValidListener;
            changeComBtn.setOnClickListener(this);

            validBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnvalidListener.onValidClick(getAdapterPosition(),dataList.get(getAdapterPosition()).getLigneList());
                }
            });

        }

        @Override
        public void onClick(View v) {
            onClientListner.onClientClick(getAdapterPosition());
        }
    }

    public interface OnClientListner {
        void onClientClick(int position);
    }
    public interface OnValidListener {
        void onValidClick(int position,List<OrderItem> orderItemList);

    }
    @Override
    public Filter getFilter() {
        return filterFn;
    }

    public Filter filterFn = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Order> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(dataListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Order item : dataListFull) {

                    if (    item.getStatus().contains(constraint) ||

                            item.getClient().getFirstName().toLowerCase().contains(filterPattern) ||
                            item.getClient().getLastName().toLowerCase().contains(filterPattern)) {

                        filterList.add(item);
                    }
                    if(item.getStatus().equals("new")&& item.getIdBo()==null){
                        filterList.remove(item);
                    }
                    if(constraint.toString().equals("saved")){
                        if(item.getStatus().equals("new")&&item.getIdBo()==null){
                            //SAFRAAA
                            filterList.add(item);
                        }
                    }
                    if(constraint.toString().equals("all")){
                            filterList.add(item);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filterList;

            Log.i("RESULTATATO",String.valueOf(filterList.size()));
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();

            dataList.addAll((List) results.values);
            notifyDataSetChanged();
        }


    };


}
