package com.digid.eddokenaCM.Menu.Commander;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.CMDItem;
import com.digid.eddokenaCM.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.MyHolder>   {

    private MutableLiveData<Integer> nbValue = new MutableLiveData<>();
    int nb;

    int nbHorsStock;

    private static DecimalFormat df = new DecimalFormat("0.0000");
    private LinkedHashMap<String, Article> dataMap;
    private HashMap<Long, CMDItem> editModelArrayList = new HashMap<Long, CMDItem>();
    Double firstSelectedQte;
    private PanierAdapter.onClickListner clickListner;
    private Context context;
    private CMDArticleCoFragment fragment;
    private boolean hasActualFocus=false;

    public PanierAdapter(LinkedHashMap<String, Article> dataMap, HashMap<Long, CMDItem> editModelArrayList, CMDArticleCoFragment fragment, PanierAdapter.onClickListner clickListner) {
        this.dataMap = dataMap;
        this.editModelArrayList = editModelArrayList;
        this.fragment = fragment;

        this.clickListner = clickListner;
    }

    @NonNull
    @Override
    public PanierAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcommand, parent, false);
        context=parent.getContext();
        return new PanierAdapter.MyHolder(view, clickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull PanierAdapter.MyHolder holder, int position) {


        Article selectedArticle = getArticleByPostion(position);

        if(editModelArrayList.get(selectedArticle.getId())!=null){

            if (editModelArrayList.get(selectedArticle.getId()).getSelectedConditionQte() == null){

                holder.articlePrixTv.setVisibility(View.GONE);
                holder.articleCondTv.setVisibility(View.GONE);


            } else {
                holder.articlePrixTv.setVisibility(View.VISIBLE);
                holder.articleCondTv.setVisibility(View.VISIBLE);
            }

        }
        holder.articleNomTv.setText(selectedArticle.getNameFr());


        holder.articlePrixTv.setText(df.format(editModelArrayList.get(selectedArticle.getId()).getSelectedPrice()) + "  TND");
        holder.articlePrixTotalTv.setText(df.format(editModelArrayList.get(selectedArticle.getId()).getSelectedTotalPrice()) + " TND");
        holder.articleCondTv.setText(editModelArrayList.get(selectedArticle.getId()).getSelectedCondition());
        holder.articleQteTv.setText("" + editModelArrayList.get(selectedArticle.getId()).getSelectedQte());
        holder.articleQteEt.setText("" + editModelArrayList.get(selectedArticle.getId()).getSelectedQte());

        if (selectedArticle.getType().equals("deal")){
            if (selectedArticle.getDealRemainingStock() == null){
                holder.articleQteTv.setVisibility(View.VISIBLE);

               holder.articleQteEt.setVisibility(View.GONE);


            } else {
                if (selectedArticle.getDealRemainingStock() <=0){
                    holder.articleQteTv.setVisibility(View.VISIBLE);
                   holder.articleQteEt.setVisibility(View.GONE);


                } else {
                    holder.articleQteTv.setVisibility(View.GONE);

                    holder.articleQteEt.setVisibility(View.VISIBLE);
                }
            }
        } else {
            Log.i("ttttttttttttttt", "onBindViewHolder: "+ selectedArticle.getCurrentStock());

            if (selectedArticle.getCurrentStock() == null){
                holder.articleQteTv.setVisibility(View.VISIBLE);
                holder.articleQteEt.setVisibility(View.GONE);


            } else {

                if (selectedArticle.getCurrentStock() <= 0){
                    holder.articleQteTv.setVisibility(View.VISIBLE);
                    holder.articleQteEt.setVisibility(View.GONE);
                } else {
                    holder.articleQteTv.setVisibility(View.GONE);
                    holder.articleQteEt.setVisibility(View.VISIBLE);

                }
            }
        }

        firstSelectedQte= editModelArrayList.get(selectedArticle.getId()).getSelectedQte();
        holder.articleQteEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hasActualFocus=hasFocus;
                    holder.articleQteEt.setText("");
                } else {
                    Log.i("textQTE", "onFocusChange: ");
                    hasActualFocus=hasFocus;
                    holder.articleQteEt.setText("" + editModelArrayList.get(selectedArticle.getId()).getSelectedQte());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataMap.size();
    }

    public Article getArticleByPostion(int position) {

        return dataMap.get((dataMap.keySet().toArray())[position]);
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private EditText articleQteEt;
        private TextView articleQteTv;
        private TextView articleNomTv;
        private TextView articleCondTv;
        private TextView articlePrixTotalTv;
        private TextView articlePrixTv;
        private ImageView deleteItemIv;
        PanierAdapter.onClickListner clickListner;

        public MyHolder(@NonNull View itemView, PanierAdapter.onClickListner clickListner) {
            super(itemView);

            articleQteTv = itemView.findViewById(R.id.commande_articlequantite_tv);
            articleQteEt = itemView.findViewById(R.id.commande_articlequantite_et);
            articleNomTv = itemView.findViewById(R.id.commande_articlenom_tv);
            articleCondTv = itemView.findViewById(R.id.commande__articlecondition_tv);
            articlePrixTotalTv = itemView.findViewById(R.id.commande_articletotalprix_tv);
            articlePrixTv = itemView.findViewById(R.id.commande_articleprix_tv);
            deleteItemIv = itemView.findViewById(R.id.commande_articledeleteitem_iv);

            articleQteEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if (getAdapterPosition() != -1){
                            if (!s.toString().equals("")){
                                if (hasActualFocus) {

                                    // oldddd  if (getArticleByPostion(getAdapterPosition()).getCurrentStock() > (Float.parseFloat(s.toString()) *
                                    //                                            editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedQte()))

                                   //OLD if (getArticleByPostion(getAdapterPosition()).getCurrentStock() >= (Float.valueOf(s.toString())

                                    if(  getArticleByPostion(getAdapterPosition()).getType().equals("deal") ) {


                                        if (getArticleByPostion(getAdapterPosition()).getDealRemainingStock() >= (Float.parseFloat(s.toString()))) {
                                            articleQteEt.setTextColor(Color.BLACK);


                                            editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).setSelectedQte(Float.parseFloat(s.toString()));
                                            editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).setSelectedTotalQte(Float.parseFloat(s.toString()));

                                                  /* OLLD NULLL
                                                  editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).setSelectedTotalPrice(
                                                          editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedQte()
                                                          * editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionPrice());


                                                   */
                                            editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).setSelectedTotalPrice(
                                                    (Float.parseFloat(s.toString()))
                                                            * editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionPrice());


                                            articlePrixTotalTv.setText(df.format(editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedTotalPrice()) + " TND");


                                            float tt = 0;
                                            for (Map.Entry<String, Article> entry : fragment.panierData.entrySet()) {
                                                tt += editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice();
                                            }

                                            fragment.panierTotalFactureTv.setText("Net à Payer  :" + df.format(tt) + " TND");
                                            nb++;
                                            nbValue.setValue(nb);
                                        }

                                        else {
                                            Toast.makeText(context, "Stock insuffisant !", Toast.LENGTH_SHORT).show();
                                            try{
                                                articleQteEt.setText(String.valueOf(firstSelectedQte));
                                                articleQteEt.clearFocus();
                                            }catch (Exception e){

                                            }

                                        }


                                    }

                                    else {
                                        if (getArticleByPostion(getAdapterPosition()).getCurrentStock() >= (Double.parseDouble(s.toString()) *
                                                editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionQte()  ))

                                        {
                                            articleQteEt.setTextColor(Color.BLACK);

                                        /* OLD
                                        if (Float.parseFloat(s.toString()) > editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getMaxQte()){
                                            Toast.makeText(itemView.getContext(), "Quantité min : "+editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId())
                                                    .getMinQte(), Toast.LENGTH_LONG).show();
                                        }
                                        else if (Float.parseFloat(s.toString()) < editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).
                                                getMinQte()){
                                            Toast.makeText(itemView.getContext(), "Quantité min : "+editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId())
                                                    .getMinQte(), Toast.LENGTH_LONG).show();
                                        }

                                         */


                                            editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).setSelectedQte(Float.valueOf(s.toString()));

                                            try{
                                                editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).setSelectedTotalQte(Float.valueOf(s.toString()) *
                                                        editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionQte());
                                            }catch (Exception e){

                                            }


                                            editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).setSelectedTotalPrice(editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedQte() * editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionPrice());

                                            Log.i("kkkkfjfjfjff", "afterTextChanged: "+editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedQte());
                                            Log.i("kkkkfjfjfjff", "afterTextChanged: "+editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionQte());
                                            Log.i("kkkkfjfjfjff", "afterTextChanged: "+editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionPrice());
                                            Log.i("kkkkfjfjfjff", "afterTextChanged: "+editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedTotalQte());
                                            Log.i("kkkkfjfjfjff", "afterTextChanged: "+editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedTotalPrice());

                                            articlePrixTotalTv.setText(df.format(editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedTotalPrice()) + " TND");

                                            float tt = 0;
                                            for (Map.Entry<String, Article> entry : fragment.panierData.entrySet()) {
                                                tt += editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice();
                                            }

                                            fragment.panierTotalFactureTv.setText("Net à Payer  :" + df.format(tt) + " TND");
                                            nb++;
                                            nbValue.setValue(nb);
                                        }

                                        else {

                                            Toast.makeText(itemView.getContext(), "Stock Insuffisant", Toast.LENGTH_SHORT).show();

                                            try {
                                                articleQteEt.setText(String.valueOf(firstSelectedQte));
                                                articleQteEt.clearFocus();
                                            } catch (Exception e){

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ex ){

                    }
                }
            });

            this.clickListner = clickListner;
            deleteItemIv.setOnClickListener(this);
            articleQteTv.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.commande_articledeleteitem_iv:
                    this.clickListner.onDeleteClick(getAdapterPosition());
                    break;

                case R.id.commande_articlequantite_tv:

                    break;
                default:
                    break;
            }
        }
    }

    public interface onClickListner {
        void onDeleteClick(int position);
    }
}