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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.CMDItem;
import com.digid.eddokenaCM.Models.Deal;
import com.digid.eddokenaCM.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CMDArticleCoAdapter extends RecyclerView.Adapter<CMDArticleCoAdapter.MyHolder> implements Filterable {
    private static DecimalFormat df = new DecimalFormat("0.0000");
    private static DecimalFormat dfStock = new DecimalFormat("0.00");
    private HashMap<Long, CMDItem> editModelArrayList = new HashMap<Long, CMDItem>();
    private List<Article> dataList;
    private List<Article> dataListFull;
    private HashMap<Integer, Float> qteList = new HashMap<>();
    private CMDArticleCoAdapter.onClickListner clickListner;
    private CMDArticleCoFragment fragment;
    private Long clientCat;
    private String arRef;
    private String qte;
    private float totalPrix;
    private Context context;
    private boolean hasActualFocus=false;

    ViewModel myViewModel;

    private Integer zoneId;
    private Integer classId;
    private long categoryId;
    private long clientId;

    public CMDArticleCoAdapter(List<Article> dataList, HashMap<Long, CMDItem> editModelArrayList, Long clientCat, float totalPrix, String arRef, CMDArticleCoFragment cmdArticleCoFragment, CMDArticleCoAdapter.onClickListner clickListner
    ,Integer zoneId,Integer classId,long categoryId,long clientId) {

        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
        this.clickListner = clickListner;
        this.clientCat = clientCat;
        this.fragment = cmdArticleCoFragment;
        this.arRef = arRef;
        this.totalPrix = totalPrix;
        this.editModelArrayList = editModelArrayList;
        this.zoneId = zoneId;
        this.classId = classId;
        this.categoryId=categoryId;
        this.clientId = clientId;

    }

    @NonNull
    @Override
    public CMDArticleCoAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemarticle_com, parent, false);
        context = parent.getContext();
        return new CMDArticleCoAdapter.MyHolder(view, clickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull CMDArticleCoAdapter.MyHolder holder, int position) {

        holder.articlePrixTv.setVisibility(View.VISIBLE);
        holder.articlePrixTotalTv.setVisibility(View.VISIBLE);

        /*
        Toast.makeText(context, "zone"+String.valueOf(zoneId), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "class"+String.valueOf(classId), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "category"+String.valueOf(categoryId), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "ClientId"+String.valueOf(categoryId), Toast.LENGTH_SHORT).show();
         */

        /*
        if(dataListFull.get(position).getDealTargets().size()>0){
            //Article has dealTargets
            Toast.makeText(context, dataListFull.get(position).toString(), Toast.LENGTH_SHORT).show();
            new GetDealTargetByArticleIdOrZoneId(context,this,dataList.get(position).getId(),null).execute();
        }

         */





        //Disponibilité d'article
        if (dataList.get(position).getType().equals("deal")){

            holder.articleDispoTv.setBackgroundColor(Color.parseColor("#ff9800"));

            String desc = "Contenu(s) :\n";
            for (Deal deal : dataList.get(position).getDealItems()
            ) {
                desc+=deal.getArticleName() + " - "+deal.getPackingType()+ " - "+deal.getDiscountedPrice()+ " TND";
                desc+="\n";
            }

            holder.condtionTv.setVisibility(View.GONE);
            holder.articleDiscountTv.setVisibility(View.GONE);
            holder.articlePrixTv.setVisibility(View.GONE);
            holder.articleNomTv.setText(dataList.get(position).getNameFr()+" \n\nStock actuel : "+ dataList.get(position).getDealRemainingStock()+"\n"+ desc);
        }
        else {

           try {
               holder.condtionTv.setVisibility(View.VISIBLE);
               holder.articlePrixTv.setVisibility(View.VISIBLE);

               if (dataList.get(position).getCurrentStock() > 0) {

                   if (editModelArrayList.get(dataList.get(position).getId()).getDiscountPercentage() > 0) {

                       holder.articleDispoTv.setBackgroundColor(Color.parseColor("#ff9800"));
                       holder.articleDiscountTv.setVisibility(View.VISIBLE);

                       if (isInteger(editModelArrayList.get(dataList.get(position).getId())
                               .getDiscountPercentage()))

                           holder.articleDiscountTv.setText(((int) editModelArrayList.get(dataList.get(position).getId())
                                   .getDiscountPercentage())+"%");
                       else
                           holder.articleDiscountTv.setText((editModelArrayList.get(dataList.get(position).getId())
                                   .getDiscountPercentage())+"%");

                   }
                   else {
                       holder.articleDispoTv.setBackgroundColor(Color.parseColor("#32cb00"));
                       holder.articleDiscountTv.setVisibility(View.GONE);

                   }
               }
               else {
                   holder.articleDispoTv.setBackgroundColor(Color.parseColor("#d50000"));
               }

               Log.i("SSSSSSSSSSST",String.valueOf(dataList.get(position).getCurrentStock()));

               if (dataList.get(position).getCurrentStock() == -1)
                   holder.articleNomTv.setText(dataList.get(position).getNameFr()+"\n\nStock actuel : Veuillez selectionner un catalogue");
               else
                   holder.articleNomTv.setText(dataList.get(position).getNameFr()+"\n\nStock actuel : "+ dataList.get(position).getCurrentStock());

           }catch (Exception e){
               Log.d("Exception ",e.getMessage());
           }
        }
//        //Image d'article
//        String str = dataList.get(position).getArPhoto().replace("\\", "#");
//        String[] separated = str.split("#");
//        Picasso.get()
//                // lenovo :
//                //.load(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/.ArticleImgs/Multimedia/"+separated[separated.length-1]))
//
//
//                // Huawei :
//                .load(new File(Environment.getExternalStorageDirectory() + "/.ArticleImgs/Multimedia/" + separated[separated.length - 1]))
//                .placeholder(R.drawable.iconempty)
//                .error(R.drawable.iconempty)
//                .into(holder.articleImageIv);

//        if (dataList.get(position).getPromo() != null) {
//            if (dataList.get(position).getPromo().equals("Oui") || dataList.get(position).getPromo().equals("oui") || dataList.get(position).getPromo().equals("OUI")) {
//                holder.articleComLl.setBackgroundResource(R.drawable.articlecom_promo_shape_list);
//                AnimationDrawable animationDrawable = (AnimationDrawable) holder.articleComLl.getBackground();
//                animationDrawable.setEnterFadeDuration(500);
//                animationDrawable.setExitFadeDuration(500);
//                animationDrawable.start();
//            } else {
//                holder.articleComLl.setBackgroundResource(R.drawable.article_promo_white_shape);
//            }
//        } else {
//            holder.articleComLl.setBackgroundResource(R.drawable.article_promo_white_shape);
//        }

        //Nom d'article

//        holder.articleNomTv.setText(dataList.get(position).getNameFr()+"\n\nStock réel : "+ dataList.get(position).getRealStock()
//                +"\nStock actuel : "+ dataList.get(position).getCurrentStock());

        //Qte d'article par defaut


        holder.qteEt.setText(String.valueOf(editModelArrayList.get(dataList.get(position).getId()).getSelectedQte()));


        holder.qteEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((hasFocus)) {


                    //editModelArrayList.get(dataList.get(position).getId()).setQteEt("1.0");
                    hasActualFocus=hasFocus;
                    editModelArrayList.get(dataList.get(position).getId()).setSelectedQte(1);
                    holder.qteEt.setText("");
                    holder.articlePrixTotalTv.setText(df.format(1 * editModelArrayList.get(dataList.get(position).getId()).getSelectedTotalPrice() )+ " TND");

                } else {
                    hasActualFocus=hasFocus;
                    if (holder.qteEt.getText().equals("")) {
                        editModelArrayList.get(dataList.get(position).getId()).setSelectedQte(1);
                        holder.qteEt.setText(String.valueOf(editModelArrayList.get(dataList.get(position).getId())
                                .getSelectedQte()));
                    }
//                    else {
//                        if (dataList.size() != 0) {
//                            //editModelArrayList.get(dataList.get(position).getId()).setQteEt(String.valueOf(dataList.get(position).getSelectedQte()));
//                            dataList.get(position).setSelectedQte();
//                        }
//                    }
                }
            }
        });


        holder.condtionTv.setText(editModelArrayList.get(dataList.get(position).getId()).getSelectedCondition());


        //reste des champs (Condition, PrixPiece, PrixTotal)
        holder.condtionTv.setText(editModelArrayList.get(dataList.get(position).getId()).getSelectedCondition());

        holder.articlePrixTotalTv.setText(df.format(editModelArrayList.get(dataList.get(position).getId())
                .getSelectedTotalPrice()) + " TND");



        holder.articlePrixTv.setText(df.format(editModelArrayList.get(dataList.get(position).getId())
                .getSelectedPrice()) + " TND");

        Log.i("TestArticleSelecttttiiiiiion", "onBindViewHolder: "+editModelArrayList.get(dataList.get(position).getId())
                .getSelectedTotalPrice());

//        holder.articlePrixTv.setText(df.format(dataList.get(position).getArticlePrice().getUnit().getFinalPrice()) + " TND");
//        holder.articlePrixTotalTv.setText(df.format(dataList.get(position).getSelectedTotalPrice()) + " TND");

        //diglogoblack panier
        if (fragment.panierData.size() != 0) {
            if (dataList.get(position).getType().equals("product")){
                if (fragment.panierData.get(String.valueOf(dataList.get(position).getId())) == null) {
                    holder.addArticleIv.setImageResource(R.drawable.iconpanier);
                    holder.condtionFixTv.setVisibility(View.GONE);
                    holder.condtionTv.setVisibility(View.VISIBLE);
                    holder.qteTv.setVisibility(View.GONE);
                    holder.qteEt.setVisibility(View.VISIBLE);
                } else {
                    holder.condtionFixTv.setText(editModelArrayList.get(dataList.get(position).getId()).getSelectedCondition());
                    holder.condtionTv.setVisibility(View.GONE);
                    holder.condtionFixTv.setVisibility(View.VISIBLE);
                    holder.qteTv.setText("" + editModelArrayList.get(dataList.get(position).getId()).getSelectedQte());
                    holder.qteEt.setVisibility(View.GONE);
                    holder.qteTv.setVisibility(View.VISIBLE);
                    holder.addArticleIv.setImageResource(R.drawable.iconadded);
                }
            } else {
                if (fragment.panierData.get(String.valueOf(dataList.get(position).getId())) == null) {
                    holder.addArticleIv.setImageResource(R.drawable.iconpanier);
                    holder.qteTv.setVisibility(View.GONE);
                    holder.qteEt.setVisibility(View.VISIBLE);


                } else {
                    holder.qteTv.setText("" + editModelArrayList.get(dataList.get(position).getId()).getSelectedQte());
                    holder.qteEt.setVisibility(View.GONE);
                    holder.qteTv.setVisibility(View.VISIBLE);
                    holder.addArticleIv.setImageResource(R.drawable.iconadded);
                }
            }
        }
        else {
            if (dataList.get(position).getType().equals("product")) {
                holder.condtionTv.setVisibility(View.VISIBLE);
                holder.condtionFixTv.setVisibility(View.GONE);
                holder.qteTv.setVisibility(View.GONE);
                holder.qteEt.setVisibility(View.VISIBLE);
                holder.addArticleIv.setImageResource(R.drawable.iconpanier);
            }
            else {
                holder.qteTv.setVisibility(View.GONE);
                holder.qteEt.setVisibility(View.VISIBLE);
                holder.addArticleIv.setImageResource(R.drawable.iconpanier);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView articlePrixTv, articleDiscountTv, articleNomTv, condtionTv, condtionFixTv, articlePrixTotalTv, articleDispoTv, qteTv;
        private EditText qteEt;
        private ImageView addArticleIv, articleImageIv;
        private LinearLayout articleComLl;
        private onClickListner clickListner;


        public MyHolder(@NonNull View itemView, onClickListner clickListner) {
            super(itemView);

            articleImageIv = itemView.findViewById(R.id.articlecomimage_iv);
            articleDiscountTv = itemView.findViewById(R.id.articlediscount_tv);
            articlePrixTv = itemView.findViewById(R.id.articlecomprix_tv);
            articlePrixTotalTv = itemView.findViewById(R.id.articlecomtotal_tv);
            articleNomTv = itemView.findViewById(R.id.articlecomname_tv);
            condtionTv = itemView.findViewById(R.id.articlecomcond_tv);
            condtionFixTv = itemView.findViewById(R.id.articlecomcondfix_tv);
            articleDispoTv = itemView.findViewById(R.id.articledispo_tv);
            qteEt = itemView.findViewById(R.id.articlecomqte_et);
            qteTv = itemView.findViewById(R.id.articlecomqte_tv);
            articleComLl = itemView.findViewById(R.id.articocom_ll);

            qteEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    Log.i("kkkkfjfjfjfff",String.valueOf(dataList.get(getAdapterPosition()).toString()));

                    if (!s.toString().equals("")) {

                        if (fragment.panierData.get(String.valueOf(dataList.get(getAdapterPosition()).getId()))== null)
                        {
                            Log.i("kkkkfjfjfjfff", "afterTextChanged: "+hasActualFocus);

                            if (hasActualFocus) {
                                Log.i("kkkkfjfjfjfff", "afterTextChanged: "+ s);

                                editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).setSelectedQte(Double.parseDouble(s.toString()));

                                Log.i("kkkkfjfjfjfff",String.valueOf(editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedQte()));
                                Log.i("kkkkfjfjfjfff",String.valueOf(editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionQte()));
                                Log.i("kkkkfjfjfjfff",String.valueOf(editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionPrice()));

                                if(editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionQte()==null)
                                {
                                    //When the Type is "deal" : ArticlePcs{unit=null, display=null, carton=null, pallet=null}
                                    editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).
                                            setSelectedTotalQte((Double.parseDouble(s.toString())));
                                }else {
                                    editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).setSelectedTotalQte((Double.parseDouble(s.toString()) * editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionQte()));
                                }

                                Log.i("kkkkfjfjfjfff",String.valueOf(editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedTotalQte()));

                                editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).setSelectedTotalPrice( ((editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedQte() * (editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionPrice()))));

                                Log.i("kkkkfjfjfjfff",String.valueOf(editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedTotalPrice()));

                                articlePrixTotalTv.setText(df.format(editModelArrayList.get(dataList.get(getAdapterPosition()).getId())
                                        .getSelectedTotalPrice()) + " TND");


                                /* OLD CALCULLLL
                                editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).
                                        setSelectedTotalPrice( ((editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedQte()

                                                * (editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionPrice())))

                                                *(1- editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getDiscountPercentage() )  );


                                 */


                                Log.i("TestArticleSelecttttiiiiiion", "afterTextChanged / has focus: " + dataList.get(getAdapterPosition()).getSelectedTotalPrice());
                            }
                        }
                    } else {

                            editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).setSelectedQte(1);
                            editModelArrayList.get(dataList.get(getAdapterPosition()).getId())
                                    .setSelectedTotalQte((1 * editModelArrayList.get(dataList.get(getAdapterPosition()).getId())
                                            .getSelectedTotalQte()) );



                        editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).
                                setSelectedTotalPrice( ((editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedQte()

                                        * (editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionPrice())))

                                );

                        articlePrixTotalTv.setText(df.format(editModelArrayList.get(dataList.get(getAdapterPosition()).getId())
                                .getSelectedTotalPrice()) + " TND");
                            /* old calcull
                            editModelArrayList.get(dataList.get(getAdapterPosition()).getId())
                                    .setSelectedTotalPrice( ((1 * (editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getSelectedConditionPrice()) )*(1-
                                            editModelArrayList.get(dataList.get(getAdapterPosition()).getId()).getDiscountPercentage() ) ) ); //*1 _ pourcentage

                             */
                    }
                }
            });

            //qteTv = itemView.findViewById(R.id.articlecomqte_tv);
            addArticleIv = itemView.findViewById(R.id.articlecomadd_bnt);
            this.clickListner = clickListner;
            addArticleIv.setOnClickListener(this);
            condtionTv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.articlecomcond_tv:
                    this.clickListner.onConditionnmentClick(getAdapterPosition());
                    break;

                case R.id.articlecomadd_bnt:
                    if (fragment.panierData.size() != 0) {
                        try {
                            if (fragment.panierData.get(dataList.get(getAdapterPosition()).getId()) == null) {
                                this.clickListner.onAddClick(getAdapterPosition());
                            } else {
                                Log.i("tessst", "onClick: ");
                            }
                        }catch (Exception e){
                            Log.i("TAG", "onClick: ");
                        }


                    } else if (fragment.panierData.size() == 0) {
                        this.clickListner.onAddClick(getAdapterPosition());
                    }

                    break;
                default:
                    break;
            }


        }
    }

    public interface onClickListner {
        void onConditionnmentClick(int position);

        void onAddClick(int position);
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

                String filterPattern = constraint.toString().trim();

                for (Article item : dataListFull) {


                    if (
                            item.getNameFr().toUpperCase(Locale.ROOT).startsWith(filterPattern) ||
                            item.getNameFr().toUpperCase(Locale.ROOT).contains(filterPattern) ||
                            item.getNameFr().toLowerCase(Locale.ROOT).startsWith(filterPattern) ||
                            item.getNameFr().toLowerCase(Locale.ROOT).contains(filterPattern)
                    ) {

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
            try
            {
                dataList.addAll((List) results.values);

            }catch (Exception e) {
                Log.d("Ex",e.getMessage());
            }
            notifyDataSetChanged();
        }


    };

    public boolean isInteger(double N) {
        int X = (int) N;
        double temp2 = N - X;
        if (temp2 > 0) {
            return false;
        }
        return true;
    }

}