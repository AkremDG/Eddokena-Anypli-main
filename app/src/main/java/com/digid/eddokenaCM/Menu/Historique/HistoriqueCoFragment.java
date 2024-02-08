package com.digid.eddokenaCM.Menu.Historique;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Models.OrderRequest;
import com.digid.eddokenaCM.Models.OrderRequestObject;
import com.digid.eddokenaCM.Models.OrderRequestObjectExtra;
import com.digid.eddokenaCM.Models.OrderRequestObjectItem;
import com.digid.eddokenaCM.Models.OrderRequestObjectItemQte;
import com.digid.eddokenaCM.Models.Statut;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.DeleteOrderByIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.DeleteOrderByIdTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.InitEnteteTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SelectEnteteLocalCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SellectAllEnteteNotNullByDateCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SellectAllEnteteNotNullByDateTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SellectAllEnteteNullByDateCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SellectAllEnteteNullByDateTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.InitLigneTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.SellectAllLigneByIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.SellectAllLigneByIdTask;
import com.digid.eddokenaCM.Utils.ClearMemory;
import com.digid.eddokenaCM.Utils.PopManager;
import com.digid.eddokenaCM.Utils.Preferences;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.Utils.Utilities;
import com.digid.eddokenaCM.WebServices.Commande.CommendeAPI;
import com.digid.eddokenaCM.WebServices.Commande.SingleCommandeCallback;
import com.digid.eddokenaCM.WebServices.Historique.HistoriqueAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoriqueCoFragment extends Fragment implements ClearMemory, DeleteAllCallback,
        InitLigneTableCallback, InitEnteteTableCallback, SwipeRefreshLayout.OnRefreshListener,
        SellectAllEnteteNotNullByDateCallback, SellectAllEnteteNullByDateCallback, CommandesAdapter.OnClientListner,CommandesAdapter.OnValidListener,
        SelectEnteteLocalCallback, SingleCommandeCallback, DeleteOrderByIdCallback {

    private List<Order> recapOrders;
    private NavController navController;
    private RecyclerView commandeRv;

    private int clickPosition;

    private List<Order> dataList = new ArrayList<Order>();
    private List<Order> orderList = new ArrayList<>();

    private List<OrderItem> orderItemList = new ArrayList<>();

    private CommandesAdapter commandesAdapter;
    private TextView lundiTv, mardiTv, mercrediTv, jeudiTv, vendrediTv, samediTv, sundayTv, affichePlusTv;
    private ImageView navClientIv, navArticleIv, navCommandeIv;
    private ImageView backUpIv;
    private AutoCompleteTextView searchSv;
    private ImageView backIv;
    private String selectedDay;

    private PopManager popUp;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Dialog connectionDialog;
    private Button closeConnectionErrorDialog;
    private TextView connectionErrorDialogTv;

    private String loadingDone = "";
    private String jourDone = "";
    private Bundle facture;
    private int fromModifFragment=0;
    private int fromSpalshFragment=0;



    public HistoriqueCoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historique_co, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        popUp = new PopManager(view.getContext());
        facture = new Bundle();

        if (getArguments()!=null){
            if (getArguments().getInt("fromModif")==1){
                fromModifFragment=1;
            } else if (getArguments().getInt("fromSplach")==1){
                fromSpalshFragment=1;
            }
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        uiMapping(view);
        initListner();
        initData(view);


        if (navClientIv!= null){
            if (SessionManager.getInstance().getUserType(getContext()) == 1) {
                navClientIv.setVisibility(View.GONE);
            }
        }
    }

    /*
     * Data initialization
     */

    private void initData(View view) {

        List<Statut> listStatut=new ArrayList<>();

        listStatut.add(new Statut("all", Color.parseColor("#ffffff")));
        listStatut.add(new Statut("confirmed",Color.parseColor("#66ffa6")));
        listStatut.add(new Statut("new", Color.parseColor("#f57f17")) );
        listStatut.add(new Statut("canceled",Color.parseColor("#FF0000")));
        listStatut.add(new Statut("saved", Color.parseColor("#ffeb3b")));
        listStatut.add(new Statut("preparing", Color.parseColor("#1a237e")));


        StatutFilterAdapter statutFilterAdapter = new StatutFilterAdapter(getContext(), listStatut);
        searchSv.setAdapter(statutFilterAdapter);



        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:

                selectedDay = "MONDAY";
                onMondaySelected();
                break;
            case Calendar.TUESDAY:
                selectedDay = "TUESDAY";
                onTuesdaySelected();
                break;
            case Calendar.WEDNESDAY:
                selectedDay = "WEDNSDAY";
                onWednsdaySelected();
                break;
            case Calendar.THURSDAY:
                selectedDay = "THURSDAY";
                onThersdaySelected();
                break;
            case Calendar.FRIDAY:
                selectedDay = "FRIDAY";
                onFridaySelected();
                break;
            case Calendar.SATURDAY:
                selectedDay = "SATURDAY";
                onSaturdaySelected();
                break;
            case Calendar.SUNDAY:
                selectedDay = "SUNDAY";
                onSundaySelected();
                break;

            default:
                break;

        }
        facture.putInt("fromNotif",0);
        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")!=null){
                    if (notifIntent.getExtras().get("notif").equals("notif")){
                        mSwipeRefreshLayout.setRefreshing(true);
                        affichePlusTv.performClick();
                        facture.putInt("fromNotif", 1);
                        notifIntent.removeExtra("notif");

                    }
                }
            }
        }

    }

    /*
     * Mapping of layout's components
     */

    private void uiMapping(View view) {

        commandeRv = view.findViewById(R.id.hist_commande_rv);

        lundiTv = view.findViewById(R.id.hist_lundi_tv);
        mardiTv = view.findViewById(R.id.hist_mardi_tv);
        mercrediTv = view.findViewById(R.id.hist_mercredi_tv);
        jeudiTv = view.findViewById(R.id.hist_jeudi_tv);
        vendrediTv = view.findViewById(R.id.hist_vendredi_tv);
        samediTv = view.findViewById(R.id.hist_samedi_tv);
        sundayTv = view.findViewById(R.id.hist_dimanche_iv);


        searchSv = view.findViewById(R.id.hist_search_sv);
        backIv = view.findViewById(R.id.hist_back_iv);
        backUpIv = view.findViewById(R.id.hist_backup_iv);

        navArticleIv = view.findViewById(R.id.hist_navarticle_iv);
        navClientIv = view.findViewById(R.id.hist_navclient_iv);
        navCommandeIv = view.findViewById(R.id.hist_navcommande_iv);

        affichePlusTv = view.findViewById(R.id.hist_afficheplus_tv);


    }

    /*
     * Set components listners
     */

    private void initListner() {

//        backUpIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingDone="";
//                mSwipeRefreshLayout.setRefreshing(true);
//                new SelectEnteteLocalTask(getContext(),HistoriqueCoFragment.this::onLocalEnteteSelectionSuccess).execute();
//            }
//        });
        affichePlusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeRefreshLayout.setRefreshing(true);
                if (Utilities.getInstance().isOnline(getContext())) {
                    loadingDone = "";

                    new DeleteAllTask(getContext(),1, HistoriqueCoFragment.this).execute();

                    Log.i("historique", "onClick: ");
                } else {
                    popUp.showDialog("connectionerror");
                    connectionDialog = popUp.getConnectionErrorDialog();
                    connectionErrorDialogTv = connectionDialog.findViewById(R.id.dialog_connectionlost_tv);
                    connectionErrorDialogTv.setText("Connexion Perdue \n Historique n'est pas à jour");
                    closeConnectionErrorDialog = connectionDialog.findViewById(R.id.articlecom_cancel_btn);
                    closeConnectionErrorDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mSwipeRefreshLayout.setRefreshing(false);
                            popUp.hideDialog("connectionerror");

                            mSwipeRefreshLayout.setRefreshing(false);
                            if (selectedDay.equals("MONDAY")) {
                                onMondaySelected();
                            } else if (selectedDay.equals("TUESDAY")) {
                                onTuesdaySelected();
                            } else if (selectedDay.equals("WEDNSDAY")) {
                                onWednsdaySelected();
                            } else if (selectedDay.equals("THURSDAY")) {
                                onThersdaySelected();
                            } else if (selectedDay.equals("FRIDAY")) {
                                onFridaySelected();
                            } else if (selectedDay.equals("SATURDAY")) {
                                onSaturdaySelected();
                            } else if (selectedDay.equals("SUNDAY")) {
                                onSundaySelected();
                            }

                        }
                    });
                }

            }
        });

        lundiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(jourDone.equals(""))) {

                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            orderList.clear();
                            selectedDay = "MONDAY";
                            loadingDone = "";
                            jourDone = "";
                            onMondaySelected();
                        }

                    });

                }

            }
        });

        mardiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(jourDone.equals(""))) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            orderList.clear();
                            selectedDay = "TUESDAY";
                            loadingDone = "";
                            jourDone = "";
                            onTuesdaySelected();
                        }

                    });

                }
            }
        });

        mercrediTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(jourDone.equals(""))) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            orderList.clear();
                            selectedDay = "WEDNSDAY";
                            loadingDone = "";
                            jourDone = "";
                            onWednsdaySelected();
                        }

                    });

                }
            }
        });

        jeudiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(jourDone.equals(""))) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            orderList.clear();
                            selectedDay = "THURSDAY";
                            loadingDone = "";
                            jourDone = "";
                            onThersdaySelected();
                        }

                    });

                }
            }
        });

        vendrediTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(jourDone.equals(""))) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            orderList.clear();
                            selectedDay = "FRIDAY";
                            loadingDone = "";
                            jourDone = "";
                            onFridaySelected();
                        }

                    });

                }
            }
        });

        samediTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(jourDone.equals(""))) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            orderList.clear();
                            selectedDay = "SATURDAY";
                            loadingDone = "";
                            jourDone = "";
                            onSaturdaySelected();
                        }

                    });

                }
            }
        });

        sundayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(jourDone.equals(""))) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            Log.i("eeeeeeeee", "run: ");
                            orderList.clear();
                            selectedDay = "SUNDAY";
                            loadingDone = "";
                            jourDone = "";
                            onSundaySelected();
                        }

                    });

                }
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingDone.equals("done")) {

                    if (fromModifFragment==0){
                        navController.popBackStack();
                    } else if (fromModifFragment==1){
                        navController.navigate(R.id.action_historiqueCoFragment_to_menuCoFragment);


                    } else if (fromSpalshFragment==1) {
                        navController.popBackStack();
                    }
                }

            }
        });

        if (navCommandeIv != null && navClientIv != null && navArticleIv != null){

            navCommandeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((SessionManager.getInstance().getUserType(getContext()) == 0) || (SessionManager.getInstance().getUserType(getContext()) == 5)) {
                        if (loadingDone.equals("done")) {
                            navController.navigate(R.id.action_historiqueCoFragment_to_comClientFragment_profCO);
                        }

                    } else if (SessionManager.getInstance().getUserType(getContext()) == 1) {
                        if (loadingDone.equals("done")) {
                            navController.navigate(R.id.action_historiqueCoFragment_to_comArticleCoFragment_profCL);
                        }

                    }
                }
            });

            navClientIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (loadingDone.equals("done")) {
                        navController.navigate(R.id.action_historiqueCoFragment_to_clientCoFragment_profCO);
                    }

                }
            });

            navArticleIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (loadingDone.equals("done")) {
                        if ((SessionManager.getInstance().getUserType(getContext()) == 0) || (SessionManager.getInstance().getUserType(getContext()) == 5)){
                            navController.navigate(R.id.action_historiqueCoFragment_to_articleCoFragment_profCO);
                        } else if (SessionManager.getInstance().getUserType(getContext())==1){
                            navController.navigate(R.id.action_historiqueCoFragment_to_articleCoFragment_profCL);
                        }

                    }

                }
            });

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        clearAppMemory();
    }

    @Override
    public void onPause() {
        super.onPause();

        clearAppMemory();
    }

    @Override
    public void onResume() {
        super.onResume();

        uiMapping(getView());

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

        commandeRv = null;

        lundiTv = null;
        mardiTv = null;
        mercrediTv = null;
        jeudiTv = null;
        vendrediTv = null;
        samediTv = null;
        sundayTv = null;


        searchSv = null;
        backIv = null;

        navArticleIv = null;
        navClientIv = null;
        navCommandeIv = null;


        System.gc();


    }

    private void onSundaySelected() {

        dataList.clear();
        sundayTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
        lundiTv.setBackgroundResource(0);
        mardiTv.setBackgroundResource(0);
        mercrediTv.setBackgroundResource(0);
        jeudiTv.setBackgroundResource(0);
        vendrediTv.setBackgroundResource(0);
        samediTv.setBackgroundResource(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")== null){
                    loadingDone = "";
                    new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
                }
            } else {
                loadingDone = "";
                new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

            }
        }
    }

    private void onMondaySelected() {

        dataList.clear();
        lundiTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
        mardiTv.setBackgroundResource(0);
        mercrediTv.setBackgroundResource(0);
        jeudiTv.setBackgroundResource(0);
        vendrediTv.setBackgroundResource(0);
        samediTv.setBackgroundResource(0);
        sundayTv.setBackgroundResource(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")== null){
                    loadingDone = "";
                    new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
                }
            } else {
                loadingDone = "";
                new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

            }
        }
    }

    private void onTuesdaySelected() {

        Log.d("TUESSDAY ","SELECTED OK");

        dataList.clear();
        sundayTv.setBackgroundResource(0);
        lundiTv.setBackgroundResource(0);
        mardiTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
        mercrediTv.setBackgroundResource(0);
        jeudiTv.setBackgroundResource(0);
        vendrediTv.setBackgroundResource(0);
        samediTv.setBackgroundResource(0);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")== null){
                    loadingDone = "";
                    new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
                }
            } else {
                loadingDone = "";

                Log.d("CALLLLL",cal.toString());
                new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
            }
        }
    }

    private void onWednsdaySelected() {

        dataList.clear();
        sundayTv.setBackgroundResource(0);
        lundiTv.setBackgroundResource(0);
        mardiTv.setBackgroundResource(0);
        mercrediTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
        jeudiTv.setBackgroundResource(0);
        vendrediTv.setBackgroundResource(0);
        samediTv.setBackgroundResource(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")== null){
                    loadingDone = "";
                    new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
                }
            } else {
                loadingDone = "";
                new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

            }
        }
    }

    private void onThersdaySelected() {

        dataList.clear();
        sundayTv.setBackgroundResource(0);
        lundiTv.setBackgroundResource(0);
        mardiTv.setBackgroundResource(0);
        mercrediTv.setBackgroundResource(0);
        jeudiTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
        vendrediTv.setBackgroundResource(0);
        samediTv.setBackgroundResource(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")== null){
                    loadingDone = "";
                    new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
                }
            } else {
                loadingDone = "";
                new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

            }
        }
    }

    private void onFridaySelected() {

        dataList.clear();
        sundayTv.setBackgroundResource(0);
        lundiTv.setBackgroundResource(0);
        mardiTv.setBackgroundResource(0);
        mercrediTv.setBackgroundResource(0);
        jeudiTv.setBackgroundResource(0);
        vendrediTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
        samediTv.setBackgroundResource(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")== null){
                    loadingDone = "";
                    new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
                }
            } else {
                loadingDone = "";
                new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

            }
        }
    }

    private void onSaturdaySelected() {

        dataList.clear();
        sundayTv.setBackgroundResource(0);
        lundiTv.setBackgroundResource(0);
        mardiTv.setBackgroundResource(0);
        mercrediTv.setBackgroundResource(0);
        jeudiTv.setBackgroundResource(0);
        vendrediTv.setBackgroundResource(0);
        samediTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() != null) {
                if (notifIntent.getExtras().get("notif")== null){
                    loadingDone = "";
                    new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
                }
            } else {
                loadingDone = "";
                new SellectAllEnteteNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

            }
        }
    }

    /*
    * Selecting order
    */

    @Override
    public void onClientClick(int position) {

        loadingDone = "";
        //facture.putLong("FactureProfil", dataList.get(position).getProfil());
        facture.putString("FactureValidation", dataList.get(position).getStatus());
        facture.putString("FactureDate", dataList.get(position).getDoDate());


            facture.putLong("ClientCat", dataList.get(position).getClient().getCategoryId());

        facture.putLong("ClientCtNum", dataList.get(position).getClientId());
        //facture.putString("ClientDoRef", dataList.get(position).getDoRef());
        if (dataList.get(position).getLocal() != null)
            facture.putBoolean("Local", dataList.get(position).getLocal());
        if (dataList.get(position).getIdBo() == null) {
            facture.putString("Do_Piece", null);
        }
        else {
            facture.putString("Do_Piece", String.valueOf(dataList.get(position).getIdBo()));
        }

        Log.i("TestModdifSelection", "onClientClick: " +  dataList.get(position).getIdOrder() );
        facture.putLong("idLocal_Cmd", dataList.get(position).getIdOrder());




        navController.navigate(R.id.changeCommandFragment, facture);

    }

    /*
    * Selecting not synchronized orders locally callback
    */

    @Override
    public void onSelectionEnteteNullSuccess(List<Order> orderList) {



        for(Order order : orderList)
        {
            Log.d("ALOOOOOOO",String.valueOf(order.getIdBo()));

        }


        Intent notifIntent = getActivity().getIntent();
        if (notifIntent != null) {
            if (notifIntent.getExtras() == null) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                if (notifIntent.getExtras().get("notif")== null){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }
        dataList.clear();
        dataList = orderList;


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        commandeRv.setLayoutManager(layoutManager);
        commandeRv.setHasFixedSize(true);
        commandesAdapter = new CommandesAdapter(getContext(), dataList, this::onClientClick,this::onValidClick);
        commandeRv.setAdapter(commandesAdapter);

        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchSv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                commandesAdapter.getFilter().filter(s);

            }
        });
        /*
        searchSv.addTextChangedListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                commandesAdapter.getFilter().filter(newText);
                return false;
            }
        });

         */
        loadingDone = "done";
        jourDone = "done";

    }

    /*
     * Selecting synchronized orders locally callback
     */
    @Override
    public void onSelectionEnteteNotNullSuccess(List<Order> orderList) {


        Log.i("historique", "onSelectionEnteteNotNullSuccess: ");
        dataList.clear();
        dataList = orderList;


        for(Order order : dataList){
            Log.d("ORDERrrrrrrrrrrrrr",order.toString());
        }



        mSwipeRefreshLayout.setRefreshing(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        commandeRv.setLayoutManager(layoutManager);
        commandeRv.setHasFixedSize(true);
        commandesAdapter = new CommandesAdapter(getContext(), dataList, this::onClientClick,this::onValidClick);
        commandeRv.setAdapter(commandesAdapter);
        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchSv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                commandesAdapter.getFilter().filter(s);


            }
        });
        /*
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                commandesAdapter.getFilter().filter(newText);
                return false;
            }
        });

         */
        loadingDone = "done";
        jourDone = "done";
    }

    @Override
    public void onLocalEnteteSelectionSuccess(List<Order> orderList) {

        dataList.clear();
        dataList = orderList;
        mSwipeRefreshLayout.setRefreshing(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        commandeRv.setLayoutManager(layoutManager);
        commandeRv.setHasFixedSize(true);
        commandesAdapter = new CommandesAdapter(getContext(), dataList, this::onClientClick,this::onValidClick);
        commandeRv.setAdapter(commandesAdapter);

        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);



        searchSv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                commandesAdapter.getFilter().filter(s);

            }
        });
        /*
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                commandesAdapter.getFilter().filter(newText);
                return false;
            }
        });

         */
        loadingDone = "done";
        jourDone = "done";
    }

    @Override
    public void onRefresh() {

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                onMondaySelected();
                break;
            case Calendar.TUESDAY:
                onTuesdaySelected();
                break;
            case Calendar.WEDNESDAY:
                onWednsdaySelected();
                break;
            case Calendar.THURSDAY:
                onThersdaySelected();
                break;
            case Calendar.FRIDAY:
                onFridaySelected();
                break;
            case Calendar.SATURDAY:
                onSaturdaySelected();
                break;
            case Calendar.SUNDAY:
                onSundaySelected();
                break;

            default:
                break;

        }
    }

    /*
     * Orders's selection & insertion callback
     */

    @Override
    public void onEnteteCallFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onEnteteCallSuccess() {
        Log.i("tteesstt", "onEnteteCallSuccess: ");
    }

    @Override
    public void onAllEnteteInsertionError() {
        Log.i("tteesstt", "onAllEnteteInsertionError: ");

    }

    @Override
    public void onAllEnteteInsertionSuccess() {
        Log.i("tteesstt", "onAllEnteteInsertionSuccess: ");

    }

    @Override
    public void onLigneCallFailed() {
        Log.i("tteesstt", "onLigneCallFailed: ");
    }

    @Override
    public void onLigneCallSuccess() {
        Log.i("tteesstt", "onLigneCallSuccess: ");
    }

    @Override
    public void onLigneInsertionError() {
        Log.i("tteesstt", "onLigneInsertionError: ");
    }

    @Override
    public void onLigneInsertionSuccess() {

        Log.i("historique", "onLigneInsertionSuccess: ");
        if (selectedDay.equals("MONDAY")) {

            dataList.clear();
            lundiTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
            mardiTv.setBackgroundResource(0);
            mercrediTv.setBackgroundResource(0);
            jeudiTv.setBackgroundResource(0);
            vendrediTv.setBackgroundResource(0);
            samediTv.setBackgroundResource(0);
            sundayTv.setBackgroundResource(0);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            loadingDone = "";
            Log.d("date",cal.toString());
            new SellectAllEnteteNotNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

        } else if (selectedDay.equals("TUESDAY")) {


            dataList.clear();
            sundayTv.setBackgroundResource(0);
            lundiTv.setBackgroundResource(0);
            mardiTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
            mercrediTv.setBackgroundResource(0);
            jeudiTv.setBackgroundResource(0);
            vendrediTv.setBackgroundResource(0);
            samediTv.setBackgroundResource(0);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            loadingDone = "";

            new SellectAllEnteteNotNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();
            
        } else if (selectedDay.equals("WEDNSDAY")) {

            Log.i("historique", "onLigneInsertionSuccess: ");
            dataList.clear();
            sundayTv.setBackgroundResource(0);
            lundiTv.setBackgroundResource(0);
            mardiTv.setBackgroundResource(0);
            mercrediTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
            jeudiTv.setBackgroundResource(0);
            vendrediTv.setBackgroundResource(0);
            samediTv.setBackgroundResource(0);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            loadingDone = "";
            new SellectAllEnteteNotNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

        } else if (selectedDay.equals("THURSDAY")) {

            dataList.clear();
            sundayTv.setBackgroundResource(0);
            lundiTv.setBackgroundResource(0);
            mardiTv.setBackgroundResource(0);
            mercrediTv.setBackgroundResource(0);
            jeudiTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
            vendrediTv.setBackgroundResource(0);
            samediTv.setBackgroundResource(0);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            loadingDone = "";
            new SellectAllEnteteNotNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

        } else if (selectedDay.equals("FRIDAY")) {

            dataList.clear();
            sundayTv.setBackgroundResource(0);
            lundiTv.setBackgroundResource(0);
            mardiTv.setBackgroundResource(0);
            mercrediTv.setBackgroundResource(0);
            jeudiTv.setBackgroundResource(0);
            vendrediTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
            samediTv.setBackgroundResource(0);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            loadingDone = "";
            new SellectAllEnteteNotNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

        } else if (selectedDay.equals("SATURDAY")) {

            dataList.clear();
            sundayTv.setBackgroundResource(0);
            lundiTv.setBackgroundResource(0);
            mardiTv.setBackgroundResource(0);
            mercrediTv.setBackgroundResource(0);
            jeudiTv.setBackgroundResource(0);
            vendrediTv.setBackgroundResource(0);
            samediTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            loadingDone = "";
            new SellectAllEnteteNotNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

        } else if (selectedDay.equals("SUNDAY")) {

            dataList.clear();
            sundayTv.setBackgroundResource(R.drawable.sellectedtextview_shape);
            lundiTv.setBackgroundResource(0);
            mardiTv.setBackgroundResource(0);
            mercrediTv.setBackgroundResource(0);
            jeudiTv.setBackgroundResource(0);
            vendrediTv.setBackgroundResource(0);
            samediTv.setBackgroundResource(0);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            loadingDone = "";
            new SellectAllEnteteNotNullByDateTask(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), cal, this).execute();

        }
    }

    /*
    * Deleting orders callback
    */

    @Override
    public void onDeleteSucces() {
        Log.i("historique", "onDeleteSucces: ");
       new HistoriqueAPI().getCmdEntete(getContext(), SessionManager.getInstance().getToken(getContext()), HistoriqueCoFragment.this, HistoriqueCoFragment.this,HistoriqueCoFragment.this);
    }

    @Override
    public void onDelteError(int result) {
        mSwipeRefreshLayout.setRefreshing(false);
        Log.i("tteesstt", "onDelteError: ");
    }

    @Override
    public void onValidClick(int position, List<OrderItem> orderItemList) {


        clickPosition= position;

        new SellectAllLigneByIdTask(getContext(), "", dataList.get(position).getClientId(),
                dataList.get(position).getIdOrder(),
                new SellectAllLigneByIdCallback() {
                    @Override
                    public void onAllLigneSelectionSuccess(List<OrderItem> articleList) {

                        OrderRequestObject selectedOrderRequestObject;

                        Preferences.getInstance().addIdCmd(getContext(), dataList.get(position).getIdOrder());
                        Preferences.getInstance().addSingleIdCMD(getContext(), dataList.get(position).getIdOrder());

                        dataList.get(position).setStatus("new");

                        if (dataList.get(position).getIdBo() != null){
                            selectedOrderRequestObject = new OrderRequestObject(dataList.get(position).getDoDate(),
                                    dataList.get(position).getIdBo().longValue(),
                                    dataList.get(position).getClientId(),
                                    dataList.get(position).getClient().getShopId(),
                                    dataList.get(position).getNote(),
                                    dataList.get(position).getTotalAmount(),
                                    "confirmed");}
                        else{
                            selectedOrderRequestObject = new OrderRequestObject(dataList.get(0).getDoDate(),
                                    null,
                                    dataList.get(position).getClientId(),
                                    dataList.get(position).getClient().getShopId(),
                                    dataList.get(position).getNote(),
                                    dataList.get(position).getTotalAmount(),
                                    "confirmed");}

                        selectedOrderRequestObject.setExtra(new OrderRequestObjectExtra(dataList.get(position).getIdOrder()));

                        List<OrderRequestObjectItem> list = new ArrayList<>();


                        for (OrderItem item : articleList) {

                            List<OrderRequestObjectItemQte> listQte = new ArrayList<>();
                            Double Pu = item.getTotalAmount()/item.getQty();
                            listQte.add(new OrderRequestObjectItemQte(item.getPackingType(), item.getQty()));
                            list.add(new OrderRequestObjectItem(item.getArticleId(), listQte,Pu));

                            Log.i("OOOOOO",String.valueOf(item.getTotalAmount()));

                        }


                        selectedOrderRequestObject.setItems(list);

                        selectedOrderRequestObject.setExpectedTotalAmount(null);


                        if (Utilities.getInstance().isOnline(getContext())) {
                            Log.d("DEUX",selectedOrderRequestObject.toString());

                            popUp.showDialog("loadingDialog");


                            selectedOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));

                            new CommendeAPI().addFacture(getContext(), new OrderRequest(selectedOrderRequestObject),
                                    SessionManager.getInstance().getToken(getContext()),
                                    HistoriqueCoFragment.this);

                        } else {
                            popUp.showDialog("connectionErrorCancelable");
                            popUp.hideDialog("dataload");

                /*
                popUp.hideDialog("dataload");
                navController.popBackStack(R.id.historiqueCoFragment, false);
                launchSyncCmdWorker();
                 */
                        }

                    }
                }).execute();




    }

    @Override
    public void addCmdSuccess(Long idCmdLocal, String doPiece) {

        new DeleteOrderByIdTask(getContext(),this,idCmdLocal).execute();

    }

    @Override
    public void addCmdFailed(String error) {
        Log.d("DeleteTaskFailure",error);
        popUp.hideDialog("loadingDialog");
        Toast.makeText(getContext(), "Connexion au serveur perdue", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteOrderByIdSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList.remove(clickPosition);
                commandesAdapter.notifyItemRemoved(clickPosition);
                popUp.hideDialog("loadingDialog");

            }
        },200);


    }

    @Override
    public void onDeleteOrderByIdFailure() {
        popUp.hideDialog("loadingDialog");

    }
}
