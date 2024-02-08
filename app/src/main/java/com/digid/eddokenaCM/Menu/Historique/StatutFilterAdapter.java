package com.digid.eddokenaCM.Menu.Historique;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digid.eddokenaCM.Models.Statut;
import com.digid.eddokenaCM.R;

import java.util.ArrayList;
import java.util.List;

public class StatutFilterAdapter extends ArrayAdapter<Statut> {

    private Context context;
    private List<Statut> statutList;

    public StatutFilterAdapter(@NonNull Context context, @NonNull List<Statut> StatutsList) {
        super(context,0,StatutsList );
        this.statutList = new ArrayList<>(StatutsList);
        this.context = context;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.statut_item, parent, false);
        }
        TextView colorTv = convertView.findViewById(R.id.status_color_tv);
        TextView textStatutTv = convertView.findViewById(R.id.status_text_tv);

        Statut statut = getItem(position);

        if(statut != null){
            colorTv.setBackgroundColor(statut.getColor());
            textStatutTv.setText(statut.getText());

        }
        return convertView;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<Statut> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length()==0){
                suggestions.addAll(statutList);
            }else {
                for(Statut statut : statutList){
                    if(  statut.getText().toLowerCase().contains(constraint.toString().toLowerCase().trim())    ){
                        suggestions.add(statut);
                    }
                }
            }
            filterResults.values=suggestions;
            filterResults.count=suggestions.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Statut) resultValue).getText();
        }
    };
}
