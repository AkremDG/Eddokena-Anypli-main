package com.digid.eddokenaCM.Menu.Commander;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.ArticlePricePcs;
import com.digid.eddokenaCM.Models.CMDItem;
import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.Models.DealTargetFilter;
import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Models.Conditionnement;
import com.digid.eddokenaCM.Models.OrderRequest;
import com.digid.eddokenaCM.Models.OrderRequestObject;
import com.digid.eddokenaCM.Models.OrderRequestObjectExtra;
import com.digid.eddokenaCM.Models.OrderRequestObjectItem;
import com.digid.eddokenaCM.Models.OrderRequestObjectItemQte;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.InitArticleTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.SellectAllArticlesCallBack;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.SellectAllArticlesWithConditionTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteEnteteLigneCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteEnteteLigneTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.SellectAllCatalogueByLevelCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.SellectAllCatalogueByLevelTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.DeleteOrderByIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.DeleteOrderByIdTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SelectEnteteByIdLocalCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SelectEnteteByIdLocalTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.UpdateEnteteDoPieceCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.UpdateEnteteDoPieceTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.SellectAllLigneByIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.SellectAllLigneByIdTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.InsertAllEnteteLignesTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.InsertEnteteLignesCallback;
import com.digid.eddokenaCM.Synchronisation.SyncCmdWorker;
import com.digid.eddokenaCM.Utils.ClearMemory;
import com.digid.eddokenaCM.Utils.NotificationManger;
import com.digid.eddokenaCM.Utils.PopManager;
import com.digid.eddokenaCM.Utils.Preferences;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.Utils.Utilities;
import com.digid.eddokenaCM.WebServices.Articles.ArticlesAPI;
import com.digid.eddokenaCM.WebServices.Commande.CommendeAPI;
import com.digid.eddokenaCM.WebServices.Commande.SingleCommandeCallback;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CMDArticleCoFragment extends Fragment implements ClearMemory, SelectEnteteByIdLocalCallback, UpdateEnteteDoPieceCallback,
        SingleCommandeCallback, DeleteEnteteLigneCallback, SellectAllLigneByIdCallback, InsertEnteteLignesCallback,
        PanierAdapter.onClickListner, ConditionAdapter.onClickListner, CMDArticleCoAdapter.onClickListner,
        CMDCatalogueAdapter.onClickListner, SellectAllCatalogueByLevelCallback, SellectAllArticlesCallBack, DeleteOrderByIdCallback {

    private int idBoNew;
    private String statusNew;
    Boolean isFirstAlone=false;
    List<OrderItem> selectedFirstCmdLignesList = new ArrayList<OrderItem>();
    OrderRequestObject selectedFirstOrderRequestObject;

     OrderRequestObject selectedSecondOrderRequestObject;

    Order firstSelectedOrder;
     Order secondSelectedOrder;





    private static DecimalFormat df = new DecimalFormat("0.0000");
    private static DecimalFormat dff = new DecimalFormat("0.000");
    List<OrderItem> orderItems = new ArrayList<>();

    Double primarySelectedPrice,DiscountPercentage,finalDiscounted;

    Button annulationBtn;
     int nbOutStock;
    private NavController navController;
    private RecyclerView articlesComRv;
    private RecyclerView catalogueRv;
    private RecyclerView panierRv;
    private PanierAdapter panierAdapter;
    private CMDArticleCoAdapter articlesComAdapter;
    private CMDCatalogueAdapter cataloguesAdapter;
    private ConditionAdapter conditionAdapter;
    private List<Catalog> selectCatalogueList = new ArrayList<>();
    private List<Article> articleDataList = new ArrayList<Article>();
    private List<Catalog> catalogueList = new ArrayList<Catalog>();
    public LinkedHashMap<String, Article> panierData = new LinkedHashMap<String, Article>();
    public LinkedHashMap<String, Article> panierDataExtra = new LinkedHashMap<String, Article>();
    public HashMap<Long, CMDItem> editModelArrayList = new HashMap<Long, CMDItem>();
    private SlidingMenu panierMenu;
    private SearchView searchSv;
    private ImageView backIv;
    private ImageView panierOpenIv;
    private Button panierValdierBtn;
    private Button panierSyncBtn;
    private Button panierAnnulerBtn;
    public TextView panierMargeTv, panierTotalFactureTv, panierNomClient;
    private EditText panierCommentaireEt;
    private String action;
    private LinearLayout firstFamilleLl;
    private TextView firstFamilleTv;
    private ImageView firstFamilleCloseIv;

    private Long clientCat;
    private Long clientCtNum;
    private String clientNom;
    private String clientScope;
    private List<Long> clientCategoryScopeList = new ArrayList<>();
    private List<Long> clientWarehouseScopeList = new ArrayList<>();
    private String clientDoref;
    private String fragmentId;
    private String doPiece;
    private long idLocal;
    private PopManager popUp;
    private boolean initialize =true;

    private RecyclerView conditionRv;
    private List<Conditionnement> selectedConditionnementList = new ArrayList<>();

    private int selectedArticlePosition = 0;
    private int articlePosition;
    private float totalPrixPanier = 0;
    private Bundle arguments;

    private CMDItem cmdItem;
    private int i = 0;
    private String arRef = "";
    private float totalPrix = 0;
    private Order selectedOrder;
    private OrderRequestObject selectedOrderRequestObject;
    private List<OrderItem> selectedCmdLignesList = new ArrayList<OrderItem>();
    private List<OrderItem> selectedCmdLignesFromDBList = new ArrayList<OrderItem>();


    private List<String> clientSelectedArticles = new ArrayList<String>();
    private long categoryId;
    private long clientId;
    private Integer classId;
    private Integer zoneId;
    private String CatalogIdIntent;

    public CMDArticleCoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_com_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        arguments = getArguments();

        clientCat = getArguments().getLong("ClientCat");
        clientCtNum = getArguments().getLong("ClientCtNum");

        zoneId = getArguments().getInt("zoneId");
        classId = getArguments().getInt("classId");
        categoryId = getArguments().getLong("categoryId");
        clientId = getArguments().getLong("clientId");



        CatalogIdIntent = getArguments().getString("CatalogId");

        Log.i("rrrrrrrrr", "onViewCreated: "+clientCtNum);
        clientNom = getArguments().getString("ClientNom");
        clientScope = getArguments().getString("ClientScope");

        if (!clientScope.equals("")) {
            for (String str : clientScope.split(",")
            ) {
                if (!str.equals("")) {
                    clientCategoryScopeList.add(Long.parseLong(str));
                }
            }
        }

        if (arguments != null && arguments.containsKey("FragmentId")) {

            fragmentId = getArguments().getString("FragmentId");
            if (fragmentId.equals("ConsultationCommandFragment")) {

                doPiece = getArguments().getString("Do_Piece");
                idLocal = getArguments().getLong("idLocal_Cmd");
                CatalogIdIntent = getArguments().getString("CatalogIdFacture");

               idBoNew =  getArguments().getInt("idBo", idBoNew);
               statusNew = getArguments().getString("status", statusNew);



                Log.i("TestModdifSelection", "onViewCreateddd: "+ idLocal);
            }

        }


        popUp = new PopManager(view.getContext());
        uiMapping(view);
        initListner();
        initData(view);
    }

    /*
     * Mapping of layout's components
     */

    private void uiMapping(View view) {


        articlesComRv = view.findViewById(R.id.comarticleco_articles_rv);
        catalogueRv = view.findViewById(R.id.comarticleco_catalogue_rv);
        backIv = view.findViewById(R.id.comarticleco_back_iv);
        searchSv = view.findViewById(R.id.comarticleco_search_sv);
        panierOpenIv = view.findViewById(R.id.comarticleco_panier_iv);

        panierMenu = new SlidingMenu(view.getContext());
        panierMenu.setMode(SlidingMenu.RIGHT);
        panierMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        panierMenu.setShadowWidthRes(R.dimen.shadow_width);
        panierMenu.setShadowDrawable(R.drawable.shadow);
        panierMenu.setBehindOffset(getMenuOffset());
        panierMenu.setFadeDegree(0.35f);
        panierMenu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
        panierMenu.setMenu(R.layout.fragment_panier);

        panierMargeTv = panierMenu.findViewById(R.id.panierMarge_tv);
        panierMargeTv.setVisibility(View.GONE);
        panierTotalFactureTv = panierMenu.findViewById(R.id.panierTtfacture_tv);

        panierMargeTv.setVisibility(View.GONE);
        panierTotalFactureTv.setVisibility(View.VISIBLE);

        panierNomClient = panierMenu.findViewById(R.id.panierClientnom_tv);

        panierCommentaireEt = panierMenu.findViewById(R.id.panierComm_et);
        panierValdierBtn = panierMenu.findViewById(R.id.panierValiderBtn);
        panierSyncBtn = panierMenu.findViewById(R.id.panierSyncBtn);
        panierAnnulerBtn = panierMenu.findViewById(R.id.panierAnnulerBtn);
        panierRv = panierMenu.findViewById(R.id.panier_rv);

        firstFamilleLl = view.findViewById(R.id.comarticleco_firstfam_ll);
        firstFamilleTv = view.findViewById(R.id.comfirstfam_tv);
        firstFamilleCloseIv = view.findViewById(R.id.comfirstfam_close_iv);


    }

    /*
     * Data initialization
     */
    
    private void initData(View view) {
        if(idBoNew>0 && statusNew.equals("new") ){
            panierValdierBtn.setVisibility(View.GONE);
        }
        popUp.showDialog("loadingDialog");

        Log.i("MyIDDDDDDD",String.valueOf(CatalogIdIntent));
        Log.i("clientIdCOMMANDER",String.valueOf(clientId));
        Log.i("clientIdHistorique",String.valueOf(clientCtNum));

        /////////////////////////////////////////////////////////////////////////////// Use clientCtNum IF BUG MODIFICATION
        new SellectAllArticlesWithConditionTask(view.getContext(), new DealTargetFilter(clientCtNum, clientCat, zoneId, classId),
                null,
                null,
                0,
                clientCat,
                "CmdArticle", clientCategoryScopeList, this::onSelectionSuccess).execute();

        new SellectAllCatalogueByLevelTask(view.getContext(), null, clientCategoryScopeList, this::onSelectionCatalogueSuccess).execute();


        if (arguments != null && arguments.containsKey("FragmentId")) {

            if (fragmentId.equals("ConsultationCommandFragment")) {
                if (doPiece == null) {
                    Log.i("TestModdifSelection", "initData: "+ idLocal);
                    new SellectAllLigneByIdTask(view.getContext(), "", clientCtNum, idLocal, this::onAllLigneSelectionSuccess).execute();


                } else {
                    Log.i("TestModdifSelection", "initData: 2"+ doPiece);
                    new SellectAllLigneByIdTask(view.getContext(), doPiece, clientCtNum, idLocal, this::onAllLigneSelectionSuccess).execute();
                }
                panierValdierBtn.setBackgroundColor(Color.parseColor("#ffeb3b"));
                panierSyncBtn.setVisibility(View.VISIBLE);
            }

        }


        panierNomClient.setText("Client :  " + clientNom);
        panierCommentaireEt.setText(clientDoref);
        LinearLayoutManager layoutManagerr = new LinearLayoutManager(view.getContext());
        panierRv.setLayoutManager(layoutManagerr);
        panierRv.setHasFixedSize(true);

        /* OLD
        panierAdapter = new PanierAdapter(panierData, editModelArrayList, this, this::onDeleteClick);
        panierRv.setAdapter(panierAdapter);

         */
    }

    /*
     * Set components listners
     */

    private void initListner() {



        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        panierOpenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panierMenu.showMenu(true);
            }
        });

        panierMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                popUp.showDialog("dataload");

//                for (Map.Entry<String, Article> entry : panierData.entrySet()) {
//                    editModelArrayList.get(entry.getKey()).setQteEt(String.valueOf(entry.getValue().getSelectedQte()));
//                    editModelArrayList.get(entry.getKey()).setPrixTt(entry.getValue().getSelectedTotalPrice());
//                }
                if (articlesComRv != null)
                    articlesComRv.getAdapter().notifyDataSetChanged();

                popUp.hideDialog("dataload");
            }
        });

        firstFamilleCloseIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                popUp.showDialog("loadingDialog");

                if (selectCatalogueList.size() == 1){
                    selectCatalogueList.clear();
                    firstFamilleLl.setVisibility(View.GONE);


                    Log.i("IEEEEEDENTIFIANT",String.valueOf(clientCat));

                    // clientCtNum was client id
                    new SellectAllArticlesWithConditionTask(getContext(),
                            new DealTargetFilter(clientCtNum, clientCat, zoneId, classId), null, null,
                            0,clientCat,"CmdArticle", clientCategoryScopeList, CMDArticleCoFragment.this).execute();

                    new SellectAllCatalogueByLevelTask(getContext(), null, clientCategoryScopeList, CMDArticleCoFragment.this).execute();

               }
                else {

                    selectCatalogueList.remove(selectCatalogueList.size()-1);
                    //firstFamilleLl.setVisibility(View.GONE);



                    new SellectAllArticlesWithConditionTask(getContext(), new DealTargetFilter(clientCtNum, clientCat, zoneId, classId),
                            selectCatalogueList.get(selectCatalogueList.size()-1).getId(),
                            selectCatalogueList.get(0).getId(),
                            selectCatalogueList.get(selectCatalogueList.size()-1).getLevel(),
                            clientCat,"CmdArticle",
                            clientCategoryScopeList,CMDArticleCoFragment.this)
                            .execute();

                    new SellectAllCatalogueByLevelTask(getContext(), selectCatalogueList.get(selectCatalogueList.size()-1).getId()
                            , clientCategoryScopeList, CMDArticleCoFragment.this).execute();
                }

//                selectedCatalogue1 = null;
//                selectedCatalogue2 = null;
//                selectedCatalogue3 = null;
//                selectedCatalogue4 = null;
//                articlePosition = 0;
//                firstFamilleLl.setVisibility(View.GONE);
//                secondFamilleLl.setVisibility(View.GONE);
//                thirdFamilleLl.setVisibility(View.GONE);
//                forthFamilleLl.setVisibility(View.GONE);
//                new SellectAllCatalogueByIdTask(getContext(), 0, CMDArticleCoFragment.this::onSelectionCatalogueSuccess).execute();
//                if (SessionManager.getInstance().getUserType(getContext())==1){
//                    new SellectAllArticlesTask(getContext(),clientSelectedArticles, CMDArticleCoFragment.this::onSelectionSuccess).execute();
//                } else {
//                    new SellectAllArticlesTask(getContext(),null, CMDArticleCoFragment.this::onSelectionSuccess).execute();
//                }
            }
        });

        panierValdierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    double totalPrix = 0;
                    i = 0;
                    selectedCmdLignesList.clear();
                    if (arguments != null && arguments.containsKey("FragmentId")) {

                        if (fragmentId.equals("ConsultationCommandFragment")) {
                            //todo mich haka hadhi
                            Order order = new Order(null, SessionManager.getInstance().getUserCoId(getContext()),
                                    SessionManager.getInstance().getBD(getContext())
                                    , "", Utilities.getInstance().getStringFromCalendar(Calendar.getInstance()),
                                    clientCtNum, "new", panierCommentaireEt.getText().toString(), true);

                            for (Map.Entry<String, Article> entry : panierData.entrySet()) {
                                totalPrix = totalPrix + editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice();
                                OrderItem orderItem;
                                orderItem = new OrderItem(idLocal, Integer.valueOf(Math.toIntExact(entry.getValue().getId())), editModelArrayList.get(entry.getValue().getId())
                                        .getSelectedCondition(), editModelArrayList.get(entry.getValue().getId()).getSelectedQte(),
                                        editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice());

                                selectedCmdLignesList.add(orderItem);
                            }
                            order.getLigneList().clear();

                            order.setLigneList(selectedCmdLignesList);

                            for(OrderItem orderItem : selectedCmdLignesList){
                                Log.i("hertazazaa", orderItem.toString());
                            }

                            order.setTotalAmount(totalPrix);
                            selectedOrder = order;
                            popUp.showDialog("dataload");
                            Dialog dialog = popUp.getDataLoadDialog();
                            TextView textView = dialog.findViewById(R.id.data_loading_TV);
                            textView.setText("En Cours ....");

                            selectedOrder.setIdOrder(idLocal);
                            action = "Enregistrer";
                            new DeleteEnteteLigneTask(getContext(), selectedOrder, CMDArticleCoFragment.this).execute();
                        }
                    } else {
                        //createPdf();
                        Order order = new Order(null, SessionManager.getInstance().getUserCoId(getContext()), SessionManager.getInstance().getBD(getContext())
                                , "", Utilities.getInstance().getStringFromCalendar(Calendar.getInstance()), clientCtNum, "new", panierCommentaireEt.getText().toString(), true);

                        for (Map.Entry<String, Article> entry : panierData.entrySet()) {
                            totalPrix = totalPrix + editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice();
                            selectedCmdLignesList.add(new OrderItem(null, Integer.valueOf(Math.toIntExact(entry.getValue().getId())), editModelArrayList.get(entry.getValue().getId())
                                    .getSelectedCondition(), editModelArrayList.get(entry.getValue().getId()).getSelectedQte(),
                                    editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice()));
                        }
                        order.setLigneList(selectedCmdLignesList);

                        for(OrderItem orderItem : selectedCmdLignesList){
                            Log.i("hertazazaa", orderItem.toString());
                        }

                        order.setTotalAmount(totalPrix);
                        selectedOrder = order;
                        popUp.showDialog("dataload");
                        Dialog dialog = popUp.getDataLoadDialog();
                        TextView textView = dialog.findViewById(R.id.data_loading_TV);
                        textView.setText("En Cours ....");



                        order.setIdOrder(0);
                        for (OrderItem orderItem : order.getLigneList()) {
                            orderItem.setIdBo(0);
                        }

                        int x=0;

                        if(order.getLigneList().size()>0)
                        {
                            for(OrderItem orderItem : order.getLigneList()){
                                Log.i("aaaaawppppppp", orderItem.toString());
                            }

                            new InsertAllEnteteLignesTask(getContext(), order, CMDArticleCoFragment.this).execute();
                        }else {
                            Toast.makeText(getContext(), "Erreur : Panier Vide !", Toast.LENGTH_SHORT).show();
                            popUp.hideDialog("dataload");
                        }

                    }

            }

        });

        panierAnnulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arguments != null && arguments.containsKey("FragmentId")) {

                    if (fragmentId.equals("ConsultationCommandFragment")) {
                        panierMenu.toggle();
                        navController.popBackStack(R.id.historiqueCoFragment, false);
                    }

                } else {

                    i = 0;
                    String z = "Net à payer  :  0";
                    String y = "Marbou7ik  :  0";
                    totalPrixPanier = 0;
                    panierMargeTv.setText(y);
                    panierTotalFactureTv.setText(z);

                    for (Map.Entry<Long, CMDItem> entry : editModelArrayList.entrySet()) {
                        entry.getValue().setSelectedQte(1);
                        if (entry.getValue().getSelectedConditionQte() != null) {
                            entry.getValue().setSelectedTotalQte(1 * entry.getValue().getSelectedConditionQte());
                            entry.getValue().setSelectedTotalPrice(1 * entry.getValue().getSelectedConditionPrice());
                            entry.getValue().setSelectedPrice(entry.getValue().getSelectedTotalPrice() / entry.getValue().getSelectedTotalQte());
                        }
                    }
                    panierData.clear();
                    panierRv.getAdapter().notifyDataSetChanged();
                    articlesComRv.getAdapter().notifyDataSetChanged();
                    panierMenu.toggle();

                }
            }
        });

        panierSyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "Sync";
                new SelectEnteteByIdLocalTask(getContext(), idLocal, CMDArticleCoFragment.this::onSelectionSingleEnteteSuccess).execute();
            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (panierMenu.isMenuShowing()) {
            panierMenu.toggle();
        }
        clearAppMemory();
    }

    /*
     * Cleaning variables
     */


    @Override
    public void clearAppMemory() {

        backIv = null;
        searchSv = null;
        panierOpenIv = null;

        panierTotalFactureTv = null;
        panierCommentaireEt = null;
        panierNomClient = null;

        panierValdierBtn = null;
        panierSyncBtn = null;
        panierAnnulerBtn = null;
        panierRv = null;

        firstFamilleLl = null;
        firstFamilleTv = null;
        firstFamilleCloseIv = null;

        editModelArrayList = null;
        articleDataList = null;
        catalogueList = null;

        catalogueRv = null;
        articlesComRv = null;

        System.gc();


    }

    /*
     * Sliding menu dimensions
     */

    public int getMenuOffset() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (size.x / 7);
    }

    @Override
    public void onPause() {
        super.onPause();
        panierMenu.setSlidingEnabled(false);
    }

    /*
     * Articles's selection locally callback
     */

    @Override
    public void onSelectionSuccess(List<Article> articleList) {

        popUp.hideDialog("loadingDialog");

        articleDataList.clear();
        articleDataList.addAll(articleList);
        if (initialize) {
            initialize=false;
            editModelArrayList.clear();
            for (Article art : articleDataList) {
                editModelArrayList.put(art.getId(), new CMDItem());

                if (art.getType().equals("product")){

                    if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {

                        if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                            if(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty()!=null)
                            {
                                editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());
                            }

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null){
                                if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage()> 0)  {
                                    editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());

                                    Double primarySelectedPrice = art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount();
                                    Double DiscountPercentage = art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage();
                                    double finalDiscounted = primarySelectedPrice * (1 - (DiscountPercentage / 100));

                                    editModelArrayList.get(art.getId()).setSelectedTotalPrice(finalDiscounted);
                                    editModelArrayList.get(art.getId()).setSelectedConditionPrice(finalDiscounted);
                                }
                                else {

                                    editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                                    editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                    editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                }

                            }
                            else {
                                editModelArrayList.get(art.getId()).setDiscountPercentage(0);
                                editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            }

                            editModelArrayList.get(art.getId()).setSelectedQte(1);
                           //OLDDDDD editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());

                            //OLD  editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());

                            Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getSelectedTotalPrice());

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("carton")) {
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                                try {
                                    editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getCarton().getPackingType());
                                    editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getCarton().getPcb());

                                    editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getCarton().getPcb());
                                    editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getCarton().getPcb());
                                } catch (Exception e){
                                    Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + e.getMessage());
                                }

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("display")) {
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getDisplay().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getDisplay().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getDisplay().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getDisplay().getPcb());

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("unit")) {
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());

                                Log.d("ARTICEPCSSSSSS",String.valueOf(art.getArticlePcs()));

                                try {
                                    editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getUnit().getPackingType());
                                    editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getUnit().getPcb());
                                    editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getUnit().getPcb());
                                    editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getUnit().getPcb());
                                }catch (Exception e){
                                    Log.d("Exception",e.getMessage());
                                }

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("pallet")) {
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getPallet().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getPallet().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getPallet().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getPallet().getPcb());
                            }

                        }
                    }
                }

                else {

                    Log.i("CLEEEEEEEE",art.getNameFr());

                    if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {
                        if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                            if(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty()!=null)
                            {
                                editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());
                            }
                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null)
                                editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());
                            else
                                editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                            editModelArrayList.get(art.getId()).setSelectedQte(1);
                            editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getSelectedTotalPrice());

                            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePrices().get(0).getPcsPrices().get(0).getType());

                        }
                    }
                }
            }
        }
        else {
            for (Article art : articleDataList) {
                if (!editModelArrayList.containsKey(art.getId())){
                    editModelArrayList.put(art.getId(), new CMDItem());

                    if (art.getType().equals("product")){

                        if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {

                            if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {


                                if(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty()!=null)
                                {
                                    editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                    editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());
                                }

                                if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null){
                                    if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage()> 0)  {
                                        editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());

                                        Double primarySelectedPrice = art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount();
                                        Double DiscountPercentage = art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage();
                                        double finalDiscounted = primarySelectedPrice * (1 - (DiscountPercentage / 100));

                                        editModelArrayList.get(art.getId()).setSelectedTotalPrice(finalDiscounted);
                                        editModelArrayList.get(art.getId()).setSelectedConditionPrice(finalDiscounted);
                                    }
                                    else {

                                        editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                                        editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                        editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                    }

                                }
                                else {
                                    editModelArrayList.get(art.getId()).setDiscountPercentage(0);
                                    editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                    editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                }

                                editModelArrayList.get(art.getId()).setSelectedQte(1);
                                //OLDDDDD editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());

                                //OLD  editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());

                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getSelectedTotalPrice());

                                if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("carton")) {
                                    Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                                    try {
                                        editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getCarton().getPackingType());
                                        editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getCarton().getPcb());

                                        editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getCarton().getPcb());
                                        editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getCarton().getPcb());
                                    } catch (Exception e){
                                        Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + e.getMessage());
                                    }

                                }
                                else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("display")) {
                                    Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                                    editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getDisplay().getPackingType());
                                    editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getDisplay().getPcb());

                                    editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getDisplay().getPcb());
                                    editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getDisplay().getPcb());

                                }
                                else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("unit")) {
                                    Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());

                                    Log.d("ARTICEPCSSSSSS",String.valueOf(art.getArticlePcs()));

                                    try {
                                        editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getUnit().getPackingType());
                                        editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getUnit().getPcb());
                                        editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getUnit().getPcb());
                                        editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getUnit().getPcb());
                                    }catch (Exception e){
                                        Log.d("Exception",e.getMessage());
                                    }

                                }
                                else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("pallet")) {
                                    Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                                    editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getPallet().getPackingType());
                                    editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getPallet().getPcb());

                                    editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getPallet().getPcb());
                                    editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / art.getArticlePcs().getPallet().getPcb());
                                }

                            }
                        }
                    }

                    else {

                        Log.i("CLEEEEEEEE",art.getNameFr());

                        if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {
                            if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                                if(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty()!=null)
                                {
                                    editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                    editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());
                                }
                                if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null)
                                    editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());
                                else
                                    editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                                editModelArrayList.get(art.getId()).setSelectedQte(1);
                                editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getSelectedTotalPrice());

                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePrices().get(0).getPcsPrices().get(0).getType());

                            }
                        }
                    }
                }
                else {

                    if (panierData.containsKey(String.valueOf(art.getId()))){
                        panierData.get(String.valueOf(art.getId())).setCurrentStock(art.getCurrentStock());
                    }

                    Log.i("kkkkfjfjfjf", "onSelectionSuccess: "+ art.getNameFr());
                    Log.i("kkkkfjfjfjf", "onSelectionSuccess: "+ art.getCurrentStock());
                    Log.i("kkkkfjfjfjf", "onSelectionSuccess: "+ panierData.containsKey(String.valueOf(art.getId())));


                }

            }
        }

        articlesComAdapter = new CMDArticleCoAdapter(articleDataList, editModelArrayList,
                clientCat, totalPrix, arRef, this, this,zoneId,classId,categoryId,clientId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext(), RecyclerView.VERTICAL, false);
        articlesComRv.setLayoutManager(layoutManager);
        articlesComRv.setHasFixedSize(true);
        articlesComRv.setAdapter(articlesComAdapter);
        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                articlesComAdapter.getFilter().filter(newText);

                return false;
            }
        });

        if (panierRv.getAdapter() != null )
            panierRv.getAdapter().notifyDataSetChanged();


    }

    /*
     * Catalog's selection locally callback
     */

    @Override
    public void onSelectionCatalogueSuccess(List<Catalog> cataloguelist) {

        catalogueList.clear();
        catalogueList.addAll(cataloguelist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        cataloguesAdapter = new CMDCatalogueAdapter(catalogueList, this::onCategorieClick);
        catalogueRv.setLayoutManager(layoutManager);
        catalogueRv.setHasFixedSize(true);
        catalogueRv.setAdapter(cataloguesAdapter);
    }

    /*
     * Selecting item from catalog
     */

    @Override
    public void onCategorieClick(int position) {

        if (selectCatalogueList.size() == 0) {
            Log.i("lenaaa","two");

            selectCatalogueList.add(catalogueList.get(position));
            firstFamilleLl.setVisibility(View.VISIBLE);
            firstFamilleTv.setText(selectCatalogueList.get(0).getNameFr());


            new SellectAllCatalogueByLevelTask(getContext(), selectCatalogueList.get(0).getId(),
                    clientCategoryScopeList, this::onSelectionCatalogueSuccess).execute();


            //clientCtNum was clientId
            new SellectAllArticlesWithConditionTask(getContext(),
                    new DealTargetFilter(clientCtNum, clientCat, zoneId, classId),
                    selectCatalogueList.get(0).getId(),
                    selectCatalogueList.get(0).getId(),
                    selectCatalogueList.get(0).getLevel(),
                    clientCat,"CmdArticle", clientCategoryScopeList,CMDArticleCoFragment.this).execute();

        }
        else {

            Log.i("lenaaa","three");

            selectCatalogueList.add(catalogueList.get(position));
            firstFamilleLl.setVisibility(View.VISIBLE);
            firstFamilleTv.setText(selectCatalogueList.get(selectCatalogueList.size()-1).getNameFr());

            new SellectAllCatalogueByLevelTask(getContext(), selectCatalogueList.get(selectCatalogueList.size()-1).getId(),
                    clientCategoryScopeList,this::onSelectionCatalogueSuccess).execute();


            //clientCtNum was clientId
            new SellectAllArticlesWithConditionTask(getContext(),
                    new DealTargetFilter(clientCtNum, clientCat, zoneId, classId),
                    selectCatalogueList.get(selectCatalogueList.size()-1).getId(),
                    selectCatalogueList.get(0).getId(),
                    selectCatalogueList.get(selectCatalogueList.size()-1).getLevel(),
                    clientCat,"CmdArticle",null,CMDArticleCoFragment.this).execute();


        }

    }

    /*
     * Condition's dialog clicked
     */

    @Override
    public void onConditionnmentClick(int position) {

        selectedArticlePosition = position;
        popUp.showDialog("condition");


        Dialog condition = popUp.getConditionDialog();
        Button cancelBtn = condition.findViewById(R.id.articlecom_cancel_btn);
        conditionRv = condition.findViewById(R.id.articlecom_conditionnement_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
        conditionRv.setLayoutManager(layoutManager);
        conditionRv.setHasFixedSize(true);

        if (articleDataList.get(position).getArticlePrices() != null){
            if (articleDataList.get(position).getArticlePrices().get(0) != null){

                conditionAdapter = new ConditionAdapter(articleDataList.get(position).getArticlePrices().get(0).getPcsPrices(), this::onConditionItemClick);
                Log.i("yyyyyyy", "onConditionnmentClick: "+ articleDataList.get(position).getArticlePrices().get(0).getPcsPrices().size());
            }
            else {
                conditionAdapter = new ConditionAdapter(new ArrayList<>(), this::onConditionItemClick);
            }
        }
        else {
            conditionAdapter = new ConditionAdapter(new ArrayList<>(), this::onConditionItemClick);
        }


        conditionRv.setAdapter(conditionAdapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.hideDialog("condition");
            }
        });
    }

    /*
     * Selecting article's condition
     */

    @Override
    public void onConditionItemClick(int position) {




        popUp.hideDialog("condition");
        Article art = articleDataList.get(selectedArticlePosition);

        Log.i("yyyyyyy", "onConditionItemClick: "+ art.getArticlePrices().get(0).getPcsPrices().size());
        Log.i("yyyyyyy", "onConditionItemClick: "+ position);

        try {
            editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(position).getMaxOrderQty());
            editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(position).getMinOrderQty());

        }catch (Exception E){
            Log.d("EXCE",E.getMessage());
        }

        if (art.getArticlePrices().get(0).getPcsPrices().get(position).getDiscountPercentage() != null){

            if (art.getArticlePrices().get(0).getPcsPrices().get(position).getDiscountPercentage()>0){

                editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(position).getDiscountPercentage());

                Double primarySelectedPrice = art.getArticlePrices().get(0).getPcsPrices().get(position).getAmount();
                Double DiscountPercentage = art.getArticlePrices().get(0).getPcsPrices().get(position).getDiscountPercentage();
                double finalDiscounted = primarySelectedPrice * (1 - (DiscountPercentage / 100));

                Log.i("Clieckedd", "onConditionItemClick: "+ primarySelectedPrice);
                Log.i("Clieckedd", "onConditionItemClick: "+ primarySelectedPrice);


                editModelArrayList.get(art.getId()).setSelectedTotalPrice(finalDiscounted);
                editModelArrayList.get(art.getId()).setSelectedConditionPrice(finalDiscounted);


            }
            else {

                editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(position).getAmount());
                editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(position).getAmount());
            }
        }
        else {

            editModelArrayList.get(art.getId()).setDiscountPercentage(0);

            editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(position).getAmount());
            editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(position).getAmount());
        }

        if (art.getArticlePrices().get(0).getPcsPrices().get(position).getType().equals("carton")){

            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getCarton().getPackingType());
            editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getCarton().getPcb());

            editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() *
                    art.getArticlePcs().getCarton().getPcb());
            editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/
                    editModelArrayList.get(art.getId()).getSelectedTotalQte());




        }
        else if (art.getArticlePrices().get(0).getPcsPrices().get(position).getType().equals("display")){

            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getDisplay().getPackingType());
            editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getDisplay().getPcb());

            editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() *
                    art.getArticlePcs().getDisplay().getPcb());

            editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/editModelArrayList.get(art.getId()).getSelectedTotalQte());





        }
        else if (art.getArticlePrices().get(0).getPcsPrices().get(position).getType().equals("unit")){

            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getUnit().getPackingType());
            editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getUnit().getPcb());

            editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() *
                    art.getArticlePcs().getUnit().getPcb());
            editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/
                    editModelArrayList.get(art.getId()).getSelectedTotalPrice());



        }
        else if (art.getArticlePrices().get(0).getPcsPrices().get(position).getType().equals("pallet")){

            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getPallet().getPackingType());
            editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getPallet().getPcb());

            editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() *
                    art.getArticlePcs().getPallet().getPcb());
            editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/
                    editModelArrayList.get(art.getId()).getSelectedTotalPrice());



        }

        articlesComRv.getAdapter().notifyDataSetChanged();
    }

    /*
     * Adding article to basket
     */
    @Override
    public void onAddClick(int position) {
        Article item = articleDataList.get(position);

        if (item.getType().equals("product")){

            Log.i("getSelectedQte",String.valueOf(editModelArrayList.get(item.getId()).getSelectedQte()));
            Log.i("getSelectedConditionQte","RESSS + " + String.valueOf(editModelArrayList.get(item.getId()).getSelectedConditionQte()));
            Log.i("itemgetCurrentStock",String.valueOf(item.getCurrentStock()));

            /*
            if (getArticleByPostion(getAdapterPosition()).getCurrentStock() >= (Double.parseDouble(s.toString()) *
                    editModelArrayList.get(getArticleByPostion(getAdapterPosition()).getId()).getSelectedConditionQte()  ))

             */


            if ((editModelArrayList.get(item.getId()).getSelectedQte() *
                    editModelArrayList.get(item.getId()).getSelectedConditionQte())
                    <= item.getCurrentStock()){


                if(editModelArrayList.get(item.getId()).getMaxQte()!=0 || editModelArrayList.get(item.getId()).getMinQte()!=0) {


                    if (editModelArrayList.get(item.getId()).getSelectedQte() > editModelArrayList.get(item.getId()).getMaxQte()) {

                        Toast.makeText(getContext(), "Quantité max : " + editModelArrayList.get(item.getId())
                                .getMaxQte(), Toast.LENGTH_LONG).show();

                    } else if (editModelArrayList.get(item.getId()).getSelectedQte() <= editModelArrayList.get(item.getId()).getMaxQte()) {

                        if ((editModelArrayList.get(item.getId()).getSelectedQte() == 0)) {
                            Toast.makeText(getContext(), "Quantité Invalide", Toast.LENGTH_LONG).show();
                        }
                        if (editModelArrayList.get(item.getId()).getSelectedQte() < editModelArrayList.get(item.getId()).getMinQte()){

                            Toast.makeText(getContext(), "Quantité min : " + editModelArrayList.get(item.getId())
                                    .getMinQte(), Toast.LENGTH_LONG).show();
                        }

                        else {
                            totalPrixPanier = (float) (totalPrixPanier + editModelArrayList.get(item.getId()).getSelectedTotalPrice());

                            String z = "Net à payer  :" +
                                    "" + df.format(totalPrixPanier);
                            String y = "Marbou7ik  :  0";
                            panierMargeTv.setText(y);
                            panierTotalFactureTv.setText(z);

                            Log.i("TestArticleSelecttttiiiiiion", "onAddClick 2: "+ editModelArrayList.get(item.getId()).getSelectedTotalPrice());


                            panierData.put(String.valueOf(item.getId()), item);
                            arRef = String.valueOf(item.getId());
                            totalPrix = (float) item.getSelectedTotalPrice();

                            panierAdapter = new PanierAdapter(panierData, editModelArrayList, this, this::onDeleteClick);
                            panierRv.setAdapter(panierAdapter);

                            panierRv.getAdapter().notifyItemInserted(i);
                            articlesComRv.getAdapter().notifyDataSetChanged();

                            Log.i("TestArticleSelecttttiiiiiion", "onAddClick 3: "+ item.getSelectedTotalPrice());


                        }




                    } else if (editModelArrayList.get(item.getId()).getSelectedQte() < editModelArrayList.get(item.getId()).getMinQte()) {

                        Toast.makeText(getContext(), "Quantité min : " + editModelArrayList.get(item.getId())
                                .getMinQte(), Toast.LENGTH_LONG).show();
                    }

                }

                else {

                    Log.i("TestArticleSelecttttiiiiiion", "onAddClick 1: "+ item.getSelectedTotalPrice());
                    totalPrixPanier = (float) (totalPrixPanier + editModelArrayList.get(item.getId()).getSelectedTotalPrice());

                    String z = "Net à payer  :" +
                            "" + df.format(totalPrixPanier);
                    String y = "Marbou7ik  :  0";
                    panierMargeTv.setText(y);
                    panierTotalFactureTv.setText(z);

                    Log.i("TestArticleSelecttttiiiiiion", "onAddClick 2: "+ editModelArrayList.get(item.getId()).getSelectedTotalPrice());

                    //editModelArrayList.get(item.getArRef()).setQteEt(String.valueOf(item.getQte()));
//                if (item.getEcQuantite()>=1){
//                    item.setQteTotal(item.getQte()*item.getEcQuantite());
//                } else {
//                    item.setQteTotal(item.getQte());
//                }
                    panierData.put(String.valueOf(item.getId()), item);
                    arRef = String.valueOf(item.getId());
                    totalPrix = (float) item.getSelectedTotalPrice();

                    panierAdapter = new PanierAdapter(panierData, editModelArrayList, this, this::onDeleteClick);
                    panierRv.setAdapter(panierAdapter);

                    panierRv.getAdapter().notifyItemInserted(i);
                    articlesComRv.getAdapter().notifyDataSetChanged();

                    Log.i("TestArticleSelecttttiiiiiion", "onAddClick 3: "+ item.getSelectedTotalPrice());


                }
            }
            else {

                Toast.makeText(getContext(), "Stock Insuffisant", Toast.LENGTH_LONG).show();

            }
        }
        else {

            if(item.getDealRemainingStock()==0){
                Toast.makeText(getContext(), "Stock Insuffisant", Toast.LENGTH_SHORT).show();
            }else{
                if (editModelArrayList.get(item.getId()).getSelectedTotalQte() <= item.getDealRemainingStock() ){


                    //if ((Float.parseFloat(editModelArrayList.get(item.getId()).getQteEt()) == 0)) {
                    if ((editModelArrayList.get(item.getId()).getSelectedQte() == 0)) {
                        Toast.makeText(getContext(), "Quantité Invalide", Toast.LENGTH_LONG).show();
                    }

                    else {

                        Log.i("TestArticleSelecttttiiiiiion", "onAddClick 1: "+ item.getSelectedTotalPrice());
                        totalPrixPanier = (float) (totalPrixPanier + editModelArrayList.get(item.getId()).getSelectedTotalPrice());

                        String z = "Net à payer  :" + df.format(totalPrixPanier);
                        String y = "Marbou7ik  :  0";
                        panierMargeTv.setText(y);
                        panierTotalFactureTv.setText(z);

//                    Log.i("TestArticleSelecttttiiiiiion", "onAddClick 2: "+ editModelArrayList.get(item.getId()).getSelectedTotalPrice());
                        //editModelArrayList.get(item.getArRef()).setQteEt(String.valueOf(item.getQte()));
//                if (item.getEcQuantite()>=1){
//                    item.setQteTotal(item.getQte()*item.getEcQuantite());
//                } else {
//                    item.setQteTotal(item.getQte());
//                }

                        panierData.put(String.valueOf(item.getId()), item);
                        arRef = String.valueOf(item.getId());
                        totalPrix = (float) item.getSelectedTotalPrice();

                        panierAdapter = new PanierAdapter(panierData, editModelArrayList, this, this::onDeleteClick);
                        panierRv.setAdapter(panierAdapter);


                        try {
                            panierRv.getAdapter().notifyItemInserted(i);
                        }catch (Exception e){
                            Log.d("Exception e",e.getMessage());
                        }
                        articlesComRv.getAdapter().notifyDataSetChanged();

                        Log.i("TestArticleSelecttttiiiiiion", "onAddClick 3: "+ item.getSelectedTotalPrice());

                        i = i + 1;
                    }
                }
                else {

                    Toast.makeText(getContext(), "Stock Insuffisant", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    /*
     * Removing article from basket
     */

    @Override
    public void onDeleteClick(int position) {
        if (getArticleByPostion(position).getId() != null) {
            if (panierData.size() == 1) {
                if (getArticleByPostion(position).getType().equals("product")){
                    i = 0;
                    String z = "Net à payer  :  0";
                    String y = "Marbou7ik  :  0";
                    panierMargeTv.setText(y);
                    totalPrixPanier = 0;
                    panierTotalFactureTv.setText(z);
                    Article art = getArticleByPostion(position);

                    if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {
                        if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null)
                                editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());
                            else
                                editModelArrayList.get(art.getId()).setDiscountPercentage(0);
                            try {
                                editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());

                            }catch (Exception e)
                            {
                                Log.d("EXCEPTION",e.getMessage());
                            }

                            editModelArrayList.get(art.getId()).setSelectedQte(1);
                            editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("carton")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getCarton().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getCarton().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getCarton().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getCarton().getPcb());

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("display")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getDisplay().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getDisplay().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getDisplay().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getDisplay().getPcb());

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("unit")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getUnit().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getUnit().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getUnit().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getUnit().getPcb());

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("pallet")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getPallet().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getPallet().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getPallet().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getPallet().getPcb());
                            }

                        }
                    }

                    panierData.clear();
                    panierRv.getAdapter().notifyDataSetChanged();
                    articlesComRv.getAdapter().notifyDataSetChanged();
                    panierMenu.toggle();
                }
                else {
                    i = 0;
                    String z = "Net à payer  :  0";
                    String y = "Marbou7ik  :  0";
                    panierMargeTv.setText(y);
                    totalPrixPanier = 0;
                    panierTotalFactureTv.setText(z);
                    Article art = getArticleByPostion(position);

                    if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {
                        if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null)
                                editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());
                            else
                                editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                                if(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty() != null){
                                    editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                }
                                if(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty() != null){
                                    editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());
                                }
                                editModelArrayList.get(art.getId()).setSelectedQte(1);
                                editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                                editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());




                        }
                    }

                    panierData.clear();
                    panierRv.getAdapter().notifyDataSetChanged();
                    articlesComRv.getAdapter().notifyDataSetChanged();
                    panierMenu.toggle();
                }

            }
            else {
                Article art = getArticleByPostion(position);

                if (art.getType().equals("product")){
                    totalPrixPanier = (float) (totalPrixPanier - editModelArrayList.get(art.getId()).getSelectedTotalPrice());
                    String z = "Net à payer  :" + df.format(totalPrixPanier);
                    String y = "Marbou7ik  :  0";
                    panierMargeTv.setText(y);
                    panierTotalFactureTv.setText(z);

                    if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {
                        if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null)
                                editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());
                            else
                                editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                            try {

                            }catch (Exception e)
                            {
                                editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());

                            }

                            editModelArrayList.get(art.getId()).setSelectedQte(1);
                            editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getSelectedTotalPrice());

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("carton")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getCarton().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getCarton().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getCarton().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getCarton().getPcb());

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("display")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getDisplay().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getDisplay().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getDisplay().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getDisplay().getPcb());

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("unit")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getUnit().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getUnit().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getUnit().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getUnit().getPcb());

                            }
                            else if (art.getArticlePrices().get(0).getPcsPrices().get(0).getType().equals("pallet")){
                                Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getNameFr() + " - "+ art.getId());
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getPallet().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getPallet().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(art.getArticlePcs().getPallet().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice()/art.getArticlePcs().getPallet().getPcb());
                            }

                        }
                    }
                    panierData.remove(String.valueOf(getArticleByPostion(position).getId()));
                    panierRv.getAdapter().notifyItemRemoved(position);
                    articlesComRv.getAdapter().notifyDataSetChanged();
                    i = i - 1;
                } else {
                    totalPrixPanier = (float) (totalPrixPanier - editModelArrayList.get(art.getId()).getSelectedTotalPrice());
                    String z = "Net à payer  :" + df.format(totalPrixPanier);
                    String y = "Marbou7ik  :  0";
                    panierMargeTv.setText(y);
                    panierTotalFactureTv.setText(z);

                    if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {
                        if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                            if (art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() != null)
                                editModelArrayList.get(art.getId()).setDiscountPercentage(art.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage());
                            else
                                editModelArrayList.get(art.getId()).setDiscountPercentage(0);

                            try {
                                editModelArrayList.get(art.getId()).setMaxQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMaxOrderQty());
                                editModelArrayList.get(art.getId()).setMinQte(art.getArticlePrices().get(0).getPcsPrices().get(0).getMinOrderQty());

                            }catch (Exception E){

                            }
                            editModelArrayList.get(art.getId()).setSelectedQte(1);
                            editModelArrayList.get(art.getId()).setSelectedTotalPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            editModelArrayList.get(art.getId()).setSelectedConditionPrice(art.getArticlePrices().get(0).getPcsPrices().get(0).getAmount());
                            Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: "+ art.getSelectedTotalPrice());
                        }
                    }
                    panierData.remove(String.valueOf(getArticleByPostion(position).getId()));
                    panierRv.getAdapter().notifyItemRemoved(position);
                    articlesComRv.getAdapter().notifyDataSetChanged();
                    i = i - 1;
                }

            }

        }
    }

    // Return article model from hashmap with position

    private Article getArticleByPostion(int position) {
        try {
            Object article = (panierData.keySet().toArray())[position];
            return panierData.get(article);
        } catch (Exception e) {
            panierData.clear();
            panierRv.getAdapter().notifyDataSetChanged();
        }
        return null;
    }

    /*
     * Deleting order's articles locally callback
     */

    @Override
    public void ontEnteteLignesDeleteSucces() {
        new InsertAllEnteteLignesTask(getContext(), selectedOrder, CMDArticleCoFragment.this).execute();
    }
    @Override
    public void ontEnteteLignesDeleteError() {

    }
    /*
     * Inserting order locally callback
     */
    @Override
    public void ontEnteteLignesInsertSucces(long idEntete) {
        Log.d("insertion result"," success");

        Preferences.getInstance().addIdCmd(getContext(), idEntete);
        Preferences.getInstance().addSingleIdCMD(getContext(), idEntete);

        if (arguments != null && arguments.containsKey("FragmentId")) {

            if (fragmentId.equals("ConsultationCommandFragment")) {
                if (action.equals("Sync")) {

                    if (Utilities.getInstance().isOnline(getContext())) {

                        if(isFirstAlone==true){

                            selectedFirstOrderRequestObject.setExpectedTotalAmount(null);
                            selectedFirstOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));

                            selectedSecondOrderRequestObject.setExpectedTotalAmount(null);
                            selectedSecondOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));


                            new CommendeAPI().addFacture(getContext(),
                                    new OrderRequest(selectedSecondOrderRequestObject),
                                    SessionManager.getInstance().getToken(getContext()),
                                    CMDArticleCoFragment.this);



                        }else{
                            selectedOrderRequestObject.setExpectedTotalAmount(null);
                            selectedOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));

                            new CommendeAPI().addFacture(getContext(),
                                    new OrderRequest(selectedOrderRequestObject),
                                    SessionManager.getInstance().getToken(getContext()),
                                    CMDArticleCoFragment.this);
                        }






                    } else {
                        popUp.hideDialog("dataload");
                        panierData.clear();
                        panierRv.getAdapter().notifyDataSetChanged();
                        articlesComRv.getAdapter().notifyDataSetChanged();
                        panierMenu.toggle();
                        navController.popBackStack(R.id.menuCoFragment, false);
                        launchSyncCmdWorker();
                    }
                }
                else {

                    i = 0;
                    String y = "Marbou7ik :  0";
                    String z = "Net à payer  :  0";
                    totalPrixPanier = 0;
                    panierMargeTv.setText(y);
                    panierTotalFactureTv.setText(z);
                    popUp.hideDialog("dataload");
                    panierData.clear();
                    panierRv.getAdapter().notifyDataSetChanged();
                    articlesComRv.getAdapter().notifyDataSetChanged();
                    panierMenu.toggle();
                    navController.popBackStack(R.id.menuCoFragment, false);
                }
            }
        }

        else {

            selectedOrder.setIdOrder(idEntete);
            i = 0;
            String y = "Marbou7ik :  0";
            String z = "Net à payer  :  0";
            totalPrixPanier = 0;
            panierMargeTv.setText(y);
            panierTotalFactureTv.setText(z);
            popUp.hideDialog("dataload");
            panierData.clear();
            panierRv.getAdapter().notifyDataSetChanged();
            articlesComRv.getAdapter().notifyDataSetChanged();
            panierMenu.toggle();

            navController.popBackStack(R.id.menuCoFragment, false);
        }
    }

    @Override
    public void ontEnteteLignesInsertError() {
        Log.d("insertion result"," success");
    }

    /*
     * Selecting order's articles locally callback
     */
    Article art;
    @Override
    public void onAllLigneSelectionSuccess(List<OrderItem> articleList) {



        selectedCmdLignesFromDBList.clear();

        selectedCmdLignesFromDBList = articleList;
        for (OrderItem item : articleList) {



            int pos = Utilities.getArticlePositionById(articleDataList, item.getArticleId());

            if (pos == -1){
                //todo
            }
            else {
                art = articleDataList.get(pos);

                if (art.getArticlePrices() != null && art.getArticlePrices().size() > 0) {
                    if (art.getArticlePrices().get(0).getPcsPrices() != null && art.getArticlePrices().get(0).getPcsPrices().size() > 0) {

                        for (ArticlePricePcs pr : art.getArticlePrices().get(0).getPcsPrices()
                        ) {
                            if (item.getPackingType().equals(pr.getType())) {

                                try {
                                    editModelArrayList.get(art.getId()).setMinQte((pr.getMinOrderQty()));
                                    editModelArrayList.get(art.getId()).setMaxQte((pr.getMaxOrderQty()));
                                } catch (Exception e) {
                                    Log.d("Exception in : CMDArticleCoFragment : ", e.getMessage());
                                }


                                if (pr.getDiscountPercentage() != null)
                                    editModelArrayList.get(art.getId()).setDiscountPercentage((pr.getDiscountPercentage()));
                                else
                                    editModelArrayList.get(art.getId()).setDiscountPercentage((0));
                            }
                        }

                        editModelArrayList.get(art.getId()).setSelectedQte(item.getQty());
                        editModelArrayList.get(art.getId()).setSelectedTotalPrice(item.getTotalAmount());


                        for (ArticlePricePcs pcs: art.getArticlePrices().get(0).getPcsPrices()
                             ) {
                            if (pcs.getType().equals(item.getPackingType()))
                                editModelArrayList.get(art.getId()).setSelectedConditionPrice(
                                        pcs.getAmount()
                                );
                        }
                        Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getSelectedTotalPrice());

                        if (item.getPackingType().equals("carton")) {

                            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getCarton().getPackingType());
                            editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getCarton().getPcb());

                            editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() * art.getArticlePcs().getCarton().getPcb());
                            editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / editModelArrayList.get(art.getId()).getSelectedTotalQte());

                        }
                        else if (item.getPackingType().equals("display")) {
                            Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getDisplay().getPackingType());
                            editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getDisplay().getPcb());

                            editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() * art.getArticlePcs().getDisplay().getPcb());
                            editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / editModelArrayList.get(art.getId()).getSelectedTotalQte());

                        }
                        else if (item.getPackingType().equals("unit")) {
                            Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());

                            try {
                                editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getUnit().getPackingType());
                                editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getUnit().getPcb());

                                editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() * art.getArticlePcs().getUnit().getPcb());
                                editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / editModelArrayList.get(art.getId()).getSelectedTotalQte());

                            }catch (Exception e){

                            }



                        }
                        else if (item.getPackingType().equals("pallet")) {
                            Log.i("TestArticleSelecttttiiiion", "onSelectionSuccess: " + art.getNameFr() + " - " + art.getId());
                            editModelArrayList.get(art.getId()).setSelectedCondition(art.getArticlePcs().getPallet().getPackingType());
                            editModelArrayList.get(art.getId()).setSelectedConditionQte(art.getArticlePcs().getPallet().getPcb());

                            editModelArrayList.get(art.getId()).setSelectedTotalQte(editModelArrayList.get(art.getId()).getSelectedQte() * art.getArticlePcs().getPallet().getPcb());
                            editModelArrayList.get(art.getId()).setSelectedPrice(editModelArrayList.get(art.getId()).getSelectedTotalPrice() / editModelArrayList.get(art.getId()).getSelectedTotalQte());
                        }


                    }
                }

                totalPrixPanier = (float) (totalPrixPanier + item.getTotalAmount());
                //editModelArrayList.get(item.getArRef()).setQteEt(String.valueOf(item.getQteColise()));
//            Log.i("TestModifSelection", "onAllLigneSelectionSuccess: "+ art.getSelectedQte());
//            Log.i("TestModifSelection", "onAllLigneSelectionSuccess: "+ art.getSelectedPrice());
//            Log.i("TestModifSelection", "onAllLigneSelectionSuccess: "+ art.getSelectedTotalPrice());
                panierData.put(String.valueOf(item.getArticleId()), art);
                i = i + 1;
            }

            Log.i("ijaaaaaaaaaHNEE", item.toString());


        }
        String z = "Net à Payer  :" + df.format(totalPrixPanier);
        String y = "Marbou7ik  :  0";
        panierMargeTv.setText(y);
        panierTotalFactureTv.setText(z);

        try {

            panierAdapter = new PanierAdapter(panierData, editModelArrayList, this, this::onDeleteClick);
            panierRv.setAdapter(panierAdapter);
            panierRv.getAdapter().notifyItemInserted(i);

        }catch (Exception e){

        }

        articlesComRv.getAdapter().notifyDataSetChanged();
    }

    /*
     * Order's send WS callback
     */

    @Override
    public void addCmdSuccess(Long idCmdLocal, String doPiece) {

        if (action.equals("sync")) {

            Toast.makeText(getContext(), "sUCCESSSS SYNC", Toast.LENGTH_SHORT).show();

            popUp.hideDialog("dataload");
            Preferences.getInstance().deleteAllIdCmd(getContext());
            panierData.clear();
            panierRv.getAdapter().notifyDataSetChanged();
            articlesComRv.getAdapter().notifyDataSetChanged();
            panierMenu.toggle();

            popUp.hideDialog("dataload");

            HashMap<Long, String> idDopeceMap = new HashMap<>();
            idDopeceMap.put(idCmdLocal, doPiece);
            new UpdateEnteteDoPieceTask(getContext(), idDopeceMap, CMDArticleCoFragment.this).execute();

        } else {

            Toast.makeText(getContext(), "sUCCESSSS ELSE", Toast.LENGTH_SHORT).show();


            new DeleteOrderByIdTask(getContext(),this,idCmdLocal).execute();


            /* old charfa
            Preferences.getInstance().deleteAllIdCmd(getContext());
            panierData.clear();
            panierRv.getAdapter().notifyDataSetChanged();
            articlesComRv.getAdapter().notifyDataSetChanged();
            panierMenu.toggle();
            navController.popBackStack(R.id.menuCoFragment, false);
            HashMap<Long, String> idDopeceMap = new HashMap<>();
            idDopeceMap.put(idCmdLocal, doPiece);

            new UpdateEnteteDoPieceTask(getContext(), idDopeceMap, CMDArticleCoFragment.this).execute();

             */


        }

    }





    @Override
    public void addCmdFailed(String error) {
        popUp.hideDialog("dataload");
        //sendLongSMS("28677624",error);
        Toast.makeText(getContext(), "Veuillez Synchroniser et réessayer !", Toast.LENGTH_LONG).show();
    }

    public void sendLongSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
            Toast.makeText(getContext(), "Message Sent", Toast.LENGTH_LONG).show();
            Log.i("rrrrrrrr", "sendLongSMS: "+ phoneNo +" - " +msg);
        } catch (Exception ex){
            Toast.makeText(getContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            Log.i("rrrrrrrr", "sendLongSMS: "+ex.getMessage());
        }
    }

    /*
     * Updating order's Do_Piece locally callback
     */

    @Override
    public void onUpdateCmdListSuccess() {
    }

    @Override
    public void onUpdateCmdListFailure() {

    }

    /*
     *  Selecting order locally callback
     */

    @Override
    public void onSelectionSingleEnteteSuccess(Order order) {

        if(order.getStatus().equals("new")&&order.getIdBo()!=null){



            if (arguments != null && arguments.containsKey("FragmentId")) {

                if (fragmentId.equals("ConsultationCommandFragment")) {
                    Toast.makeText(getContext(), "FIRST", Toast.LENGTH_SHORT).show();

                    popUp.showDialog("dataload");
                    Dialog dialog = popUp.getDataLoadDialog();
                    TextView textView = dialog.findViewById(R.id.data_loading_TV);
                    textView.setText("En Cours ....");
                    //TA9SIMA
                    isFirstAlone= true;

                    float totalPrix = 0;
                    i = 0;
                    firstSelectedOrder= order;
                    selectedFirstCmdLignesList.clear();

                    firstSelectedOrder.setStatus("confirmed");

                    selectedFirstOrderRequestObject = new OrderRequestObject(
                            firstSelectedOrder.getDoDate(),
                            firstSelectedOrder.getIdBo().longValue(),
                            firstSelectedOrder.getClientId(),
                            firstSelectedOrder.getClient().getShopId(),
                            firstSelectedOrder.getNote(),
                            firstSelectedOrder.getTotalAmount(),
                            firstSelectedOrder.getStatus());

                    selectedFirstOrderRequestObject.setExtra(new OrderRequestObjectExtra(firstSelectedOrder.getIdOrder()));

                    firstSelectedOrder.setLigneList(selectedCmdLignesFromDBList);
                    firstSelectedOrder.setTotalAmount(firstSelectedOrder.getTotalAmount());

                    List<OrderRequestObjectItem> listAA = new ArrayList<>();


                    for (OrderItem itemX : selectedCmdLignesFromDBList) {
                        List<OrderRequestObjectItemQte> listQteE = new ArrayList<>();
                        Double PuU = itemX.getTotalAmount()/itemX.getQty();
                        listQteE.add(new OrderRequestObjectItemQte(itemX.getPackingType(), itemX.getQty()));
                        listAA.add(new OrderRequestObjectItem(itemX.getArticleId(), listQteE,PuU));
                    }

                    selectedFirstOrderRequestObject.setOriginOrderId(null);

                    selectedFirstOrderRequestObject.setItems(listAA);

                    selectedFirstOrderRequestObject.setExpectedTotalAmount(null);// done

                  //  new InsertAllEnteteLignesTask(getContext(), firstSelectedOrder, CMDArticleCoFragment.this).execute();

                    float secondTotalPrix = 0;
                    float secondOrderTotalPrix = 0;

                    i = 0;
                    secondSelectedOrder = order;
                    selectedCmdLignesList.clear();


                    if (arguments != null && arguments.containsKey("FragmentId")) {

                        if (fragmentId.equals("ConsultationCommandFragment")) {


                            /*
                            for (OrderItem item : selectedCmdLignesFromDBList) {
                                if (panierData.get(String.valueOf(item.getArticleId())) == null) {
                                    orderItems.add(item);
                                }
                            }

                             */

                            for (Map.Entry<String, Article> entry : panierData.entrySet()) {
                                secondTotalPrix = (float) (secondTotalPrix + editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice());
                                selectedCmdLignesList.add(new OrderItem(secondSelectedOrder.getIdOrder(), Integer.valueOf(Math.toIntExact(entry.getValue().getId())), editModelArrayList.get(entry.getValue().getId()).getSelectedCondition(),
                                        editModelArrayList.get(entry.getValue().getId()).getSelectedQte(), editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice()));
                            }




                            order.setStatus("confirmed");

                            if (order.getIdBo() != null)
                                selectedSecondOrderRequestObject = new OrderRequestObject(
                                        secondSelectedOrder.getDoDate(),
                                        secondSelectedOrder.getIdBo().longValue(),
                                        secondSelectedOrder.getClientId(),
                                        secondSelectedOrder.getClient().getShopId(),
                                        secondSelectedOrder.getNote(),
                                        secondSelectedOrder.getTotalAmount(),
                                        secondSelectedOrder.getStatus());

                            selectedSecondOrderRequestObject.setExtra(new OrderRequestObjectExtra(secondSelectedOrder.getIdOrder()));

                            List<OrderRequestObjectItem> list = new ArrayList<>();

                            //delete cmd loula m thenya
                            List<Integer> listInts = new ArrayList<>();
                            for (OrderItem orderItemOld : selectedCmdLignesFromDBList) {
                                listInts.add(orderItemOld.getArticleId());
                            }
                            Iterator<OrderItem> iterator = selectedCmdLignesList.iterator();
                            while (iterator.hasNext()) {
                                OrderItem orderItem = iterator.next();
                                if (listInts.contains(orderItem.getArticleId())) {
                                    iterator.remove();
                                }
                            }
                            for (OrderItem ORDI : selectedCmdLignesList) {
                                Log.i("Ordra", ORDI.toString());
                            }
                            for (OrderItem item : selectedCmdLignesList) {
                                List<OrderRequestObjectItemQte> listQte = new ArrayList<>();
                                Double Pu = item.getTotalAmount()/item.getQty();
                                listQte.add(new OrderRequestObjectItemQte(item.getPackingType(), item.getQty()));
                                list.add(new OrderRequestObjectItem(item.getArticleId(), listQte,Pu));
                            }


                            selectedSecondOrderRequestObject.setItems(list);

                            secondSelectedOrder.setLigneList(selectedCmdLignesList);

                            secondSelectedOrder.setTotalAmount(secondTotalPrix-firstSelectedOrder.getTotalAmount());
                            selectedSecondOrderRequestObject.setExpectedTotalAmount(null);


                           // new InsertAllEnteteLignesTask(getContext(), secondSelectedOrder, CMDArticleCoFragment.this).execute();

                            List<OrderRequestObject> orderRequestObjects = new ArrayList<>();
                            orderRequestObjects.clear();

                            selectedSecondOrderRequestObject.setOriginOrderId(order.getOriginOrderId());

                            orderRequestObjects.add(selectedFirstOrderRequestObject);
                            orderRequestObjects.add(selectedSecondOrderRequestObject);

                            OrderRequest orderRequest = new OrderRequest(orderRequestObjects);
                            orderRequest.setOrderRequestObjects(orderRequestObjects);

                            Log.i("ObjectToSend", orderRequest.toString());

                            new CommendeAPI().addFacture(getContext(),
                                    orderRequest,
                                    SessionManager.getInstance().getToken(getContext()),
                                    CMDArticleCoFragment.this);

                        }
                    }

                }
            }

        }else if (order.getStatus().equals("new")&&order.getIdBo()==null){
            isFirstAlone = false;

            Toast.makeText(getContext(), "SECOND ", Toast.LENGTH_SHORT).show();
            float totalPrix = 0;
            i = 0;
            selectedOrder = order;
            selectedCmdLignesList.clear();
            if (arguments != null && arguments.containsKey("FragmentId")) {

                if (fragmentId.equals("ConsultationCommandFragment")) {


                    for (OrderItem item : selectedCmdLignesFromDBList) {
                        if (panierData.get(String.valueOf(item.getArticleId())) == null) {
                            orderItems.add(item);
                        }
                    }

                    for (Map.Entry<String, Article> entry : panierData.entrySet()) {
                        totalPrix = (float) (totalPrix + editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice());
                        selectedCmdLignesList.add(new OrderItem(order.getIdOrder(), Integer.valueOf(Math.toIntExact(entry.getValue().getId())), editModelArrayList.get(entry.getValue().getId()).getSelectedCondition(),
                                editModelArrayList.get(entry.getValue().getId()).getSelectedQte(), editModelArrayList.get(entry.getValue().getId()).getSelectedTotalPrice()));
                    }

                    order.setStatus("confirmed");

                    if (order.getIdBo() != null)
                        selectedOrderRequestObject = new OrderRequestObject(order.getDoDate(), order.getIdBo().longValue(),order.getClientId(),order.getClient().getShopId(),order.getNote(),
                                order.getTotalAmount(),order.getStatus());
                    else
                        selectedOrderRequestObject = new OrderRequestObject(order.getDoDate(), null,order.getClientId(),order.getClient().getShopId(),order.getNote(),
                                order.getTotalAmount(),order.getStatus());


                    selectedOrderRequestObject.setExtra(new OrderRequestObjectExtra(order.getIdOrder()));

                    List<OrderRequestObjectItem> list = new ArrayList<>();

                    for (OrderItem item : selectedCmdLignesList) {
                        List<OrderRequestObjectItemQte> listQte = new ArrayList<>();
                        Double Pu = item.getTotalAmount()/item.getQty();
                        listQte.add(new OrderRequestObjectItemQte(item.getPackingType(), item.getQty()));
                        list.add(new OrderRequestObjectItem(item.getArticleId(), listQte,Pu));

                    }

                    selectedOrderRequestObject.setItems(list);

                    order.setLigneList(selectedCmdLignesList);



                    order.setTotalAmount(totalPrix);
                    selectedOrder = order;

                    popUp.showDialog("dataload");
                    Dialog dialog = popUp.getDataLoadDialog();
                    TextView textView = dialog.findViewById(R.id.data_loading_TV);
                    textView.setText("En Cours ....");


                    //selectedOrderRequestObject.setExpectedTotalAmount(selectedOrder.getTotalAmount());

                    selectedOrderRequestObject.setExpectedTotalAmount(null);
                    selectedOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));


                    new CommendeAPI().addFacture(getContext(),
                            new OrderRequest(selectedOrderRequestObject),
                            SessionManager.getInstance().getToken(getContext()),
                            CMDArticleCoFragment.this);

                     //new InsertAllEnteteLignesTask(getContext(), selectedOrder, CMDArticleCoFragment.this).execute();
                }
            }
        }


    }

    /*
     *  Launching worker in case no internet availble
     */

    public void launchSyncCmdWorker() {

        NotificationManger.getInstance().displaySyncNotification(getContext());

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        OneTimeWorkRequest syncCmdOneTimeWorkRequest = new OneTimeWorkRequest.Builder(SyncCmdWorker.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance().enqueueUniqueWork("SyncCmdWorkTag", ExistingWorkPolicy.REPLACE, syncCmdOneTimeWorkRequest);

    }

    private String convertIntoWords(Double str,String language,String Country) {
        Locale local = new Locale(language, Country);
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT);
        return ruleBasedNumberFormat.format(str);
    }

    @Override
    public void onDeleteOrderByIdSuccess() {

        new DeleteAllTask(getContext(), 1, new DeleteAllCallback() {
            @Override
            public void onDeleteSucces() {
                new ArticlesAPI().getArticles(getContext(), new InitArticleTableCallback() {
                    @Override
                    public void onArticleCallFailed() {

                    }

                    @Override
                    public void onArticleCallSuccess() {

                    }

                    @Override
                    public void getArticlesListCallback(List<Article> articleList) {

                    }

                    @Override
                    public void onArticleInsertionError() {

                    }

                    @Override
                    public void onArticleInsertionSuccess() {

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                popUp.hideDialog("dataload");
                                navController.popBackStack(R.id.menuCoFragment, false);
                            }
                        });


                    }
                });
            }

            @Override
            public void onDelteError(int result) {

            }
        }).execute();
    }

    @Override
    public void onDeleteOrderByIdFailure() {

    }
}
