package com.digid.eddokenaCM.Menu;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.InitArticleTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.SellectAllArticlesCallBack;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.SellectAllArticlesWithConditionTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.InitCatalogueTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SellectAllEnteteCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SellectAllEnteteTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.InitClientsTableCallback;
import com.digid.eddokenaCM.Utils.ClearMemory;
import com.digid.eddokenaCM.Utils.PopManager;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.Utils.Utilities;
import com.digid.eddokenaCM.WebServices.Articles.ArticlesAPI;
import com.digid.eddokenaCM.WebServices.Catalogue.CatalogueAPI;
import com.digid.eddokenaCM.WebServices.Clients.ClientsAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuCoFragment extends Fragment implements  InitArticleTableCallback, InitClientsTableCallback, InitCatalogueTableCallback,
        DeleteAllCallback, ClearMemory, SellectAllEnteteCallback, SellectAllArticlesCallBack {

    private NavController navController;
    private CardView articleCoCv, clientCoCv, historiqueCoCv, statistiqueCoCv;
    private LinearLayout commanderCoBtn;
    private ImageView logoutBtn;
    private ImageView promoIv;
    private TextView menuCoUserNameTv;
    private TextView nombrePromoTv;
    private PopManager popUp;
    private List<Article> articlesEnpromo = new ArrayList<>();
    private FloatingActionButton syncFAB;

    private Dialog dataloadDialog;
    private TextView dataloadTv;
    private int done=0;


    public MenuCoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_co, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        popUp = new PopManager(view.getContext());

        if (getArguments()!=null){
            if (getArguments().getInt("fromSplach")==1){
                getArguments().putInt("fromSplach",0);
                navController.navigate(R.id.action_menuCoFragment_to_historiqueCoFragment);
            }
        }

        uiMapping(view);
        initListner(view);
        initData(view);

        menuCoUserNameTv.setText("User : \n" + SessionManager.getInstance().getUserName(getContext()));

    }

    /*
     * Data initialization
     */

    private void initData(View view) {
        new SellectAllArticlesWithConditionTask(view.getContext(), null,null, null,0,null, "Menu", null, this::onSelectionSuccess).execute();

    }
    /*
     * Selecting article locally callback
     */

    @Override
    public void onSelectionSuccess(List<Article> articleList) {
        articlesEnpromo.clear();
        articlesEnpromo.addAll(articleList);

        nombrePromoTv.setText(articlesEnpromo.size() + " Article(s) \n en promotion");
    }
    /*
     * Mapping of layout's components
     */

    private void uiMapping(View view) {

        promoIv = view.findViewById(R.id.menuCo_promo_iv);
        nombrePromoTv = view.findViewById(R.id.menuCo_nombrepromo_tv);
        menuCoUserNameTv = view.findViewById(R.id.menuCouserName_tv);
        articleCoCv = view.findViewById(R.id.menuCo_art_btn);
        clientCoCv = view.findViewById(R.id.menuCo_client_btn);
        historiqueCoCv = view.findViewById(R.id.menuCo_hist_btn);
        statistiqueCoCv = view.findViewById(R.id.menuCo_stat_btn);
        commanderCoBtn = view.findViewById(R.id.menuCo_comm_btn);
        logoutBtn = view.findViewById(R.id.menuco_logout_btn);
        syncFAB = view.findViewById(R.id.menuco_sync_fab);

    }

    /*
     * Set components listners
     */

    private void initListner(View view) {
        syncFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utilities.getInstance().isOnline(getContext())){

                    popUp.showDialog("dataload");
                    dataloadDialog=popUp.getDataLoadDialog();
                    dataloadTv=dataloadDialog.findViewById(R.id.data_loading_TV);
                    dataloadTv.setText("En cours ...");
                    new DeleteAllTask(getContext(),2, MenuCoFragment.this).execute();

                } else {
                    Toast.makeText(getContext(), "Pas de connexion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        promoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.showDialog("promo");
                Dialog promoDialog = popUp.getPromoDialog();
                RecyclerView promoRv = promoDialog.findViewById(R.id.promoarticle_rv);
                ImageView closePromoIv = promoDialog.findViewById(R.id.closepromo_iv);
                closePromoIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.hideDialog("promo");
                    }
                });

                MenuPromoRVAdapter articlesAdapter = new MenuPromoRVAdapter(articlesEnpromo);
                promoRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
                promoRv.setAdapter(articlesAdapter);
            }
        });

        statistiqueCoCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navController.navigate(R.id.action_menuCoFragment_to_statisticFragment);
            }
        });

        articleCoCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_menuCoFragment_to_articleCoFragment);
            }
        });

        clientCoCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_menuCoFragment_to_clientCoFragment);

            }
        });

        historiqueCoCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_menuCoFragment_to_historiqueCoFragment);
            }
        });

        commanderCoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_menuCoFragment_to_comClientFragment);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SellectAllEnteteTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), "", 0, MenuCoFragment.this).execute();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        clearAppMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        //autoPubHandler.removeCallbacksAndMessages(null);
        clearAppMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
         Calendar calendar = Calendar.getInstance();
         Long seconds=calendar.getTimeInMillis();
         if (seconds> SessionManager.getInstance().getExpireDate(getContext())){
         SessionManager.getInstance().setToken(getContext(), "#");
         SessionManager.getInstance().setUserId(getContext(), "-1");
         navController.navigate(R.id.action_menuCoFragment_to_loginFragment);
       }
    }

    /*
     * Cleaning variables
     */

    @Override
    public void clearAppMemory() {


        menuCoUserNameTv = null;
        articleCoCv = null;
        clientCoCv = null;
        historiqueCoCv = null;
        statistiqueCoCv = null;
        commanderCoBtn = null;
        logoutBtn = null;

        System.gc();

    }

    /*
     * Selecting orders locally callback
     */

    @Override
    public void onEnteteSelectionSuccess(List<Order> orderList) {

        int verif = 0;
        /*if (cmdEnteteList.size()!=0){
            for (CmdEntete item : cmdEnteteList) {
                if (item.getBcValidation() != null) {
                    if (item.getBcValidation().equals("null") && item.getDoPiece() == null) {
                        verif++;
                    }
                }
            }
        }*/

        if (verif == 0) {

            SessionManager.getInstance().setUserType(getContext(), -1);
            SessionManager.getInstance().setToken(getContext(), "#");
            SessionManager.getInstance().setloginDate(getContext());
            SessionManager.getInstance().setUserId(getContext(), "-1");
            navController.navigate(R.id.action_menuCoFragment_to_loginFragment);

        } else {

            Toast.makeText(getContext(), "Vous avez "+verif +" Commande(s) en attente", Toast.LENGTH_LONG).show();
            SessionManager.getInstance().setUserType(getContext(), -1);
            SessionManager.getInstance().setToken(getContext(), "#");
            SessionManager.getInstance().setloginDate(getContext());
            SessionManager.getInstance().setUserId(getContext(), "-1");
            navController.navigate(R.id.action_menuCoFragment_to_loginFragment);
        }
    }

    @Override
    public void onDeleteSucces() {


        done=0;

        new ArticlesAPI().getArticles(getContext(), this);
        new ClientsAPI().getClients(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), SessionManager.getInstance().getToken(getContext()), this);
        new CatalogueAPI().getCatalogue(getContext(), SessionManager.getInstance().getToken(getContext()), this);
    }

    @Override
    public void onDelteError(int result) {
        Log.i("MenuCo", "onDelteError: ");
    }

    @Override
    public void onArticleCallFailed() {
        Log.i("MenuCo", "onArticleCallFailed: ");
    }

    @Override
    public void onArticleCallSuccess() {

        done++;
        if (done == 6){
            done=0;
            popUp.hideDialog("dataload");
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getArticlesListCallback(List<Article> articleList) {

    }

    @Override
    public void onArticleInsertionError() {
        Log.i("MenuCo", "onArticleInsertionError: ");
    }

    @Override
    public void onArticleInsertionSuccess() {
        done++;
        if (done == 6){
            done=0;
            popUp.hideDialog("dataload");
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCatalogueCallFailed() {
        Log.i("MenuCo", "onCatalogueCallFailed: ");
    }

    @Override
    public void onCatalogueCallSuccess() {
        done++;
        if (done == 6){
            done=0;
            popUp.hideDialog("dataload");
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCatalogueInsertionError() {
        Log.i("MenuCo", "onCatalogueInsertionError: ");
    }

    @Override
    public void onCatalogueInsertionSuccess(long[] id) {
        done++;
        if (done == 6){
            done=0;
            popUp.hideDialog("dataload");
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClientCallFailed() {
        Log.i("MenuCo", "onClientCallFailed: ");
    }

    @Override
    public void onClientCallSuccess() {
        done++;
        if (done == 6){
            done=0;
            popUp.hideDialog("dataload");
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClientInsertionError() {
        Log.i("MenuCo", "onClientInsertionError: ");
    }

    @Override
    public void onClientInsertionSuccess() {
        done++;
        if (done == 6){
            done=0;
            popUp.hideDialog("dataload");
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }
}
