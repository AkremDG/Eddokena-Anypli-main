package com.digid.eddokenaCM.Menu.Commander;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

import com.digid.eddokenaCM.Menu.PopupVh;
import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Models.ClientScope;
import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Models.OrderRequest;
import com.digid.eddokenaCM.Models.OrderRequestObject;
import com.digid.eddokenaCM.Models.OrderRequestObjectExtra;
import com.digid.eddokenaCM.Models.OrderRequestObjectItem;
import com.digid.eddokenaCM.Models.OrderRequestObjectItemQte;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.InitArticleTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.findAllCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.findAllTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteEnteteLigneCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteEnteteLigneTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.DeleteOrderByIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.DeleteOrderByIdTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SelectEnteteByIdLocalCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.SelectEnteteByIdLocalTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.UpdateEnteteDoPieceCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.SellectAllLigneByIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.SellectAllLigneByIdTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.SellectClientByIdCallback;
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
import com.ibm.icu.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultationCommandFragment extends Fragment implements SellectClientByIdCallback, ClearMemory, UpdateEnteteDoPieceCallback, SingleCommandeCallback, SelectEnteteByIdLocalCallback, DeleteEnteteLigneCallback, SellectAllLigneByIdCallback, ConsultationCommandAdapter.OnClientListner, InsertEnteteLignesCallback, InitArticleTableCallback, DeleteOrderByIdCallback {

    private List<Long> listIdsArticles = new ArrayList<>();
    RecyclerView listView;
    private NavController navController;
    PopupVh.PopupAdapter adapter;
    private RecyclerView articleRv;
    private ConsultationCommandAdapter commandAdapter;
    private List<OrderItem> dataList = new ArrayList<OrderItem>();
    private OrderRequestObject selectedOrderRequestObject;
    private Order selectedOrder;
    Dialog dialog;

    private static DecimalFormat df = new DecimalFormat("0.000");

    private TextView factDoRef;
    private TextView factNbArticle;
    private TextView factTtPrix;
    private TextView factCode;
    private TextView factDate;
    private TextView nomClient;

    private ImageView modifierBtn;
    private ImageView deleteBtn;
    private ImageView syncBtn;
    private ImageView printBtn;
    private PopManager popUp;
    private String factureDoPiece;
    private String clientNom;
    private String clientScopes="";
    private String CatalogId="";

    private String factureDate;
    private String factureStatus;
    private long factureIdLocal;
    private int idBoNew;
    private String statusNew;
    private boolean factlocal;
    private Long clientCat;
    private Long clientId;
    private int fromNotif;
    //private long factureProfil;

    private Client clientDetails;

    private ImageView backIv;

    private String actionClicked;

    private Intent sendInt;
    private String BILL;
    private String nomEntr="Cogecom";
    private String telEntr= "71222333";
    private String mfEntr="23424fd2678";
    private String mfClt="23424fd2678";
    private String adressEntr="52 rue de blablac, ben arous, Tunis";
    private String nClt;


    public ConsultationCommandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_command, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        popUp = new PopManager(view.getContext());
        fromNotif=0;
        fromNotif = getArguments().getInt("fromNotif");
        factureStatus = getArguments().getString("FactureValidation");
        factureDate = getArguments().getString("FactureDate");
        factlocal = getArguments().getBoolean("Local");
        clientNom = getArguments().getString("ClientNom");
        clientCat = getArguments().getLong("ClientCat");
        clientId = getArguments().getLong("ClientCtNum");

        factureDoPiece = getArguments().getString("Do_Piece");
        factureIdLocal = getArguments().getLong("idLocal_Cmd");


        idBoNew= getArguments().getInt("idBo");
        statusNew= getArguments().getString("status");


        Log.i("TestModdifSelection", "onViewCreated: "+ factureIdLocal);

        uiMapping(view);
        initListner();
        initData(view);


    }

    /*
     * Data initialization
     */

    private void initData(View view) {

        if (factureDoPiece == null) {
            new SellectAllLigneByIdTask(view.getContext(), "", clientId, factureIdLocal, this::onAllLigneSelectionSuccess).execute();
        }
        else {
            new SellectAllLigneByIdTask(view.getContext(), factureDoPiece, clientId, factureIdLocal, this::onAllLigneSelectionSuccess).execute();
        }

        if ((factureStatus.equals("new")) && (factureDoPiece != null) && (!factlocal) ) {

            syncBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            modifierBtn.setVisibility(View.VISIBLE);
            printBtn.setVisibility(View.VISIBLE);

        }
        else if ((factureStatus.equals("confirmed")) && (factureDoPiece != null) && (!factlocal)) {

            syncBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            modifierBtn.setVisibility(View.GONE);
            printBtn.setVisibility(View.GONE);

        }
        else if ((factureStatus.equals("new")) && (factureDoPiece == null) && (factlocal)){

            syncBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            modifierBtn.setVisibility(View.VISIBLE);
            printBtn.setVisibility(View.VISIBLE);
        }else if(factureStatus.equals("canceled")){
            syncBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            modifierBtn.setVisibility(View.GONE);
            printBtn.setVisibility(View.GONE);
        }else if(factureStatus.equals("preparing")){
            syncBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            modifierBtn.setVisibility(View.GONE);
            printBtn.setVisibility(View.GONE);
        }

    }

    /*
     * Set components listners
     */

    private void initListner() {

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromNotif==0){
                    navController.popBackStack();
                } else if (fromNotif==1){
                    Bundle fromModif = new Bundle();
                    fromModif.putInt("fromModif",1);
                    navController.navigate(R.id.action_changeCommandFragment_to_historiqueCoFragment,fromModif);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Êtes-vous sûr de vouloir supprimer/annuler cette commande?")
                        .setCancelable(true)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                actionClicked = "deleteBtn";
                                popUp.showDialog("dataload");
                                Dialog dio = popUp.getDataLoadDialog();
                                TextView txt = dio.findViewById(R.id.data_loading_TV);
                                txt.setText("En Cours ...");
                                new SelectEnteteByIdLocalTask(getContext(), factureIdLocal, ConsultationCommandFragment.this::onSelectionSingleEnteteSuccess).execute();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        modifierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle facture = new Bundle();
                facture.putString("FragmentId", "ConsultationCommandFragment");
                facture.putLong("ClientCat", clientCat);
                facture.putLong("ClientCtNum", clientId);
                facture.putString("ClientNom", clientNom);
                facture.putString("Do_Piece", factureDoPiece);
                facture.putLong("idLocal_Cmd", factureIdLocal);
                facture.putString("ClientScope", clientScopes);
                facture.putString("CatalogIdFacture", CatalogId);


                facture.putInt("idBo", idBoNew);
                facture.putString("status", statusNew);


                for(int i=0;i<dataList.size();i++){
                    Log.i("pppppppppppp", String.valueOf(dataList.get(i).getArticle().getNameFr()));
                    listIdsArticles.add(dataList.get(i).getArticle().getId());
                }

                new findAllTask(getContext(), listIdsArticles, new findAllCallback() {
                    @Override
                    public void onFindAllCallbackError() {
                        Log.i("alooooooooo", "errrror: ");
                    }

                    @Override
                    public void onFindAllCallbackSuccess(List<Article> listArticles) {
                        Log.i("alooooooooo", "successsssssss: "+String.valueOf(listArticles.toString()));
                        for(Article article : listArticles){
                            Log.i("articlesFRRRRRRRRRRRRRRR", String.valueOf(article.getNameFr()));
                        }

                    }
                }).execute();





                Log.i("TestModdifSelection", "onViewCreated: "+ factureIdLocal);



                navController.navigate(R.id.action_changeCommandFragment_to_comArticleCoFragment, facture);
            }
        });

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataList.size()!=0){

                    actionClicked = "syncBtn";
                    popUp.showDialog("dataload");
                    Dialog dialogg = popUp.getDataLoadDialog();
                    TextView txt = dialogg.findViewById(R.id.data_loading_TV);
                    txt.setText("En Cours ...");
                    new SelectEnteteByIdLocalTask(getContext(), factureIdLocal, ConsultationCommandFragment.this::onSelectionSingleEnteteSuccess).execute();
                }
            }
        });

//        printBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (dataList.size()!=0){
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    builder.setMessage("Veuillez-vous imprimer cette commande en pdf?")
//                            .setCancelable(false)
//                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//                                @RequiresApi(api = Build.VERSION_CODES.DONUT)
//                                public void onClick(DialogInterface dialog, int id) {
//                                    //printBon();
//                                    createPdf();
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
//            }
//        });
    }

    /*
     * Mapping of layout's components
     */

    private void uiMapping(View view) {
        articleRv = view.findViewById(R.id.changecommande_articles_rv);
        backIv = view.findViewById(R.id.changecommande_back_iv);

        nomClient = view.findViewById(R.id.changecommande_clientnom_tv);
        factCode = view.findViewById(R.id.changecommande_factcode_tv);
        factDate = view.findViewById(R.id.changecommande_factdate_tv);
        factTtPrix = view.findViewById(R.id.changecommande_totalprix_tv);
        factNbArticle = view.findViewById(R.id.changecommande_nbarticle_tv);
        factDoRef = view.findViewById(R.id.changecommande_factCom_tv);

        deleteBtn = view.findViewById(R.id.changecommande_delete_btn);
        modifierBtn = view.findViewById(R.id.changecommande_modifier_btn);
        syncBtn = view.findViewById(R.id.changecommande_sync_btn);
        printBtn = view.findViewById(R.id.changecommande_print_btn);

        printBtn.setVisibility(View.INVISIBLE);
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

        dataList = new ArrayList<>();
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


        articleRv = null;
        backIv = null;

        nomClient = null;
        factCode = null;
        factDate = null;
        factTtPrix = null;
        factNbArticle = null;

        deleteBtn = null;
        modifierBtn = null;
        syncBtn = null;

        dataList = null;

        System.gc();
    }

    @Override
    public void onClientClick(int position) {
    }

    /*
     * Selecting order's articles locally callback
     */

    @Override
    public void onAllLigneSelectionSuccess(List<OrderItem> articleList) {
        float totalPiece = 0;
        long tt = 0;
        float totalPrix = 0;
        dataList.clear();

        dataList = articleList;

        if (articleList.size() != 0) {
            nomClient.setText("Client  :   " + clientNom);
            factDoRef.setText("");
            factCode.setText("N° Piece :  " + dataList.get(0).getRef());
            factDate.setText("Date :  " + Utilities.getInstance().getDateFormat(factureDate));


            for (OrderItem article : articleList) {

                Log.i("FirstSelectedd", article.toString());

                totalPrix = (float) (totalPrix + (article.getTotalAmount()));

            }

            factNbArticle.setText("Nombre d'articles  : " + dataList.size());
            factTtPrix.setText("Net à payer  : " + df.format(totalPrix) + " TND");

            clientScopes="";
            for (ClientScope clt: dataList.get(0).getClient().getClientScopes()
            ) {
                clientScopes+=String.valueOf(clt.getCatalogId())+",";
            }

            CatalogId = dataList.get(0).getClient().getClientScopes().get(0).getCatalogId();
            
            //new SellectClientByIdTask(getContext(), ,ConsultationCommandFragment.this).execute();
        } else {
            factCode.setText("N° Piece :  ");
            factDate.setText("Date :  ");
        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        articleRv.setLayoutManager(layoutManager);
        articleRv.setHasFixedSize(true);
        commandAdapter = new ConsultationCommandAdapter(dataList, this::onClientClick);
        articleRv.setAdapter(commandAdapter);
    }

    /*
     * Deleting order locally callback
     */

    @Override
    public void ontEnteteLignesDeleteSucces() {
        Bundle fromModif = new Bundle();
        fromModif.putInt("fromModif",1);
        navController.navigate(R.id.action_changeCommandFragment_to_historiqueCoFragment,fromModif);
    }

    @Override
    public void ontEnteteLignesDeleteError() {
    }

    /*
     * Selecting single order locally callback
     */

    @Override
    public void onSelectionSingleEnteteSuccess(Order order) {

        if (actionClicked.equals("syncBtn")) {

            Preferences.getInstance().addIdCmd(getContext(), order.getIdOrder());
            Preferences.getInstance().addSingleIdCMD(getContext(), order.getIdOrder());

            order.setStatus("new");

            Log.i("yyyyyyyyyyy", "onSelectionSingleEnteteSuccess: "+ order.getClient());

            if (order.getIdBo() != null)
                selectedOrderRequestObject = new OrderRequestObject(order.getDoDate(), order.getIdBo().longValue(), order.getClientId(),order.getClient().getShopId(),order.getNote(),
                        order.getTotalAmount(),"confirmed");
            else
                selectedOrderRequestObject = new OrderRequestObject(order.getDoDate(), null, order.getClientId(),order.getClient().getShopId(),order.getNote(),
                        order.getTotalAmount(),"confirmed");

            selectedOrderRequestObject.setExtra(new OrderRequestObjectExtra(order.getIdOrder()));

            List<OrderRequestObjectItem> list = new ArrayList<>();

            for (OrderItem item : dataList
                 ) {
                List<OrderRequestObjectItemQte> listQte = new ArrayList<>();
                Double Pu = item.getTotalAmount()/item.getQty();
                listQte.add(new OrderRequestObjectItemQte(item.getPackingType(), item.getQty()));
                list.add(new OrderRequestObjectItem(item.getArticleId(), listQte,Pu));

            }

            selectedOrderRequestObject.setItems(list);


            if (Utilities.getInstance().isOnline(getContext())) {

                popUp.hideDialog("dataload");
                selectedOrderRequestObject.setExpectedTotalAmount(null);
                selectedOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));

                popUp.showDialog("loadingDialog");
                new CommendeAPI().addFacture(getContext(), new OrderRequest(selectedOrderRequestObject),
                        SessionManager.getInstance().getToken(getContext()), ConsultationCommandFragment.this);


                //new ArticlesAPI().getArticles(getContext(), this);

            } else {
                popUp.showDialog("connectionErrorCancelable");
                popUp.hideDialog("dataload");

                /*
                popUp.hideDialog("dataload");
                navController.popBackStack(R.id.historiqueCoFragment, false);
                launchSyncCmdWorker();
                 */
            }
            //new InsertAllEnteteLignesTask(getContext(), order, ConsultationCommandFragment.this).execute();
        }

        else if (actionClicked.equals("deleteBtn")) {

            if (order.getIdBo() == null) {
                new DeleteEnteteLigneTask(getContext(), order, ConsultationCommandFragment.this).execute();
                popUp.hideDialog("dataload");
            }
            else {
                Preferences.getInstance().addIdCmd(getContext(), order.getIdOrder());
                Preferences.getInstance().addSingleIdCMD(getContext(), order.getIdOrder());

                order.setStatus("canceled");

                selectedOrderRequestObject = new OrderRequestObject(order.getDoDate(), order.getIdBo().longValue(), order.getClientId(),order.getClient().getShopId(),order.getNote(),
                        order.getTotalAmount(),order.getStatus());

                selectedOrderRequestObject.setExtra(new OrderRequestObjectExtra(order.getIdOrder()));

                List<OrderRequestObjectItem> list = new ArrayList<>();

                for (OrderItem item : dataList
                ) {
                    List<OrderRequestObjectItemQte> listQte = new ArrayList<>();

                    Double Pu = item.getTotalAmount()/item.getQty();
                    listQte.add(new OrderRequestObjectItemQte(item.getPackingType(), item.getQty()));
                    list.add(new OrderRequestObjectItem(item.getArticleId(), listQte,Pu));

                }

                selectedOrderRequestObject.setItems(list);


                if (Utilities.getInstance().isOnline(getContext())) {

                    selectedOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));

                    new CommendeAPI().addFacture(getContext(), new OrderRequest(selectedOrderRequestObject), SessionManager.getInstance().getToken(getContext()), ConsultationCommandFragment.this);
                } else {
                    popUp.showDialog("connectionErrorCancelable");
                    popUp.hideDialog("dataload");
                    /*
                    popUp.hideDialog("dataload");
                    navController.popBackStack(R.id.historiqueCoFragment, false);
                    launchSyncCmdWorker();
                     */
                }
//                order.setLigneList(dataList);
//
//                selectedOrder = order;
//                new InsertAllEnteteLignesTask(getContext(), order, ConsultationCommandFragment.this).execute();
            }
        }

    }

    /*
     * Updating order's Do_Piece locally callback
     */

    @Override
    public void onUpdateCmdListSuccess() {

        popUp.hideDialog("dataload");
        navController.popBackStack(R.id.historiqueCoFragment, false);
    }

    @Override
    public void onUpdateCmdListFailure() {
    }

    /*
     * Order's insertion locally callback
     */
    @Override
    public void ontEnteteLignesInsertSucces(long idEntete) {
//        Preferences.getInstance().addIdCmd(getContext(), idEntete);
//        Preferences.getInstance().addSingleIdCMD(getContext(), idEntete);
//
//        selectedOrder.setId(idEntete);
//        if (Utilities.getInstance().isOnline(getContext())) {
//            new CommendeAPI().addFacture(getContext(), selectedOrder, SessionManager.getInstance().getToken(getContext()), ConsultationCommandFragment.this);
//        } else {
//            popUp.hideDialog("dataload");
//            navController.popBackStack(R.id.historiqueCoFragment, false);
//            launchSyncCmdWorker();
//        }
    }

    @Override
    public void ontEnteteLignesInsertError() {

    }

    /*
     * Order's send WS callback
     */

    @Override
    public void addCmdSuccess(Long idCmdLocal, String doPiece) {

       // popUp.hideDialog("loadingDialog");

        Preferences.getInstance().deleteAllIdCmd(getContext());
        Log.i("historique", "addCmdSuccess: " + idCmdLocal +" - "+doPiece);
        HashMap<Long, String> idDopeceMap = new HashMap<>();
        idDopeceMap.put(idCmdLocal, doPiece);

//        dialog.dismiss();
        Log.d("addCmdSuccess","Sent to server with success !");

        new DeleteOrderByIdTask(getContext(),this,idCmdLocal).execute();

        //new UpdateEnteteDoPieceTask(getContext(), idDopeceMap, ConsultationCommandFragment.this).execute();
    }

    @Override
    public void addCmdFailed(String error) {
        popUp.hideDialog("loadingDialog");
        popUp.hideDialog("dataload");
        //sendLongSMS("28677624",error);
        Toast.makeText(getContext(), "Veuillez Synchroniser et réessayer !", Toast.LENGTH_LONG).show();
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

//    private void createPdf(){
//
//        float totalTTC=0f;
//        int pageNumber=1;
//
//        // create a new document
//        PdfDocument document = new PdfDocument();
//
//        if (dataList.size()<20) {
//            // crate a page description
//            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
//        }
//        // crate a page description
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842,(dataList.size()/20)+1).create();
//        // start a page
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//        Paint paint = new Paint();
//        Paint linepaint = new Paint();
//        linepaint.setStrokeWidth(1);
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(20);
//        //canvas.drawText("Bon° Reception", 220, 50, paint);
//
//        int y=EnteteFacture(canvas,paint,pageInfo,linepaint);
//        paint.setTextSize(11);
//
//        int conteur=0;
//        for (OrderItem entry: dataList) {
//
//            String dlDesign =entry.getDlDesign();
//            String euEnumere="";
//            float qte =entry.getQteColise();
//            double prixTTC =roundDown5(entry.getDlMontantTTC());
//            totalTTC = totalTTC + entry.getDlMontantTTC();
//            euEnumere =entry.getEuEnumere();
//
//            canvas.drawText(dlDesign+"  ",25,y,paint);
//            canvas.drawText(euEnumere ,340,y,paint);
//            canvas.drawText("  "+qte,420,y,paint);
//            canvas.drawText("  "+prixTTC+ " TND",470,y,paint);
//
//            y+=25;
//            conteur++;
//            if (conteur%20==0){
//                paint.setTextSize(8);
//                canvas.drawText("Page : "+ pageNumber+"/"+((dataList.size()/20)+1),540,830,paint);
//                pageNumber++;
//                document.finishPage(page);
//
//                page = document.startPage(pageInfo);
//                canvas = page.getCanvas();
//                y=EnteteFacture(canvas,paint,pageInfo,linepaint);
//
//            }
//        }
//
//        canvas.drawLine(10,pageInfo.getPageHeight()-100,pageInfo.getPageWidth()-10, pageInfo.getPageHeight()-100 , linepaint);
//        paint.setTextSize(13);
//        paint.setColor(Color.BLACK);
//
//        canvas.drawText("Total HT : "+ df.format(totalTTC)+" TND ",310,770,paint);
//
//
//        /*String number = df.format(roundDown5(totalTTC));
//        int index=number.indexOf(",");
//        int aftercomma= Integer.parseInt(number.substring(index+1));
//
//        String montantChiffre=" - " + EnglishNumberToWords.convert(totalTTC)+" DINAR " + EnglishNumberToWords.convertLessThanOneThousand(aftercomma)+ " MILLIME ";
//        if (montantChiffre.length()>=41){
//
//            String x = montantChiffre.substring(0,41);
//            int i = x.lastIndexOf(' ');
//            String leftX = x.substring(0,i);
//            String rightX = x.substring(i+1);
//
//            canvas.drawText(leftX,310,790,paint);
//            canvas.drawText(rightX +montantChiffre.substring(41),310,805,paint);
//
//        } else {
//            canvas.drawText(montantChiffre,310,790,paint);
//
//        }*/
//        paint.setTextSize(8);
//        canvas.drawText("Page : "+ pageNumber+"/"+((dataList.size()/20)+1),540,830,paint);
//
//        //canvas.drawBitmap(scaleDown(signatureBit,200,true) , 350 , 720 , paint);
//        //canvas.drawt
//        // finish the page
//        document.finishPage(page);
//        // draw text on the graphics object of the page
//
//        // write the document content
//        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/DocCommande/";
//        File file = new File(directory_path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String targetPdf = directory_path+"cmd-"+ Calendar.getInstance().getTimeInMillis()+".pdf";
//        File filePath = new File(targetPdf);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//            Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            Log.e("mainX", "error "+e.toString());
//            Toast.makeText(getContext(), "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
//        }
//        // close the document
//        document.close();
//
//        if (filePath.exists())
//        {
//            Intent intent=new Intent(Intent.ACTION_VIEW);
//            Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", filePath);
//            intent.setDataAndType(uri, "application/pdf");
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//            try
//            {
//                startActivity(intent);
//            }
//            catch(ActivityNotFoundException e)
//            {
//                Toast.makeText(getContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//    }

//    public int EnteteFacture(Canvas canvas, Paint paint, PdfDocument.PageInfo pageInfo, Paint linepaint){
//
//        //up lines
//        canvas.drawLine(10,20,(pageInfo.getPageWidth()/2)-5, 20 , linepaint);
//        canvas.drawLine((pageInfo.getPageWidth()/2)+(5),20,pageInfo.getPageWidth()-10, 20 , linepaint);
//
//        String nomEnt="COGECOM";
//        String MTEnt="703875F/E/N/003";
//        String contEnt=" - ";
//        String adrEnt="14 ROUTE TAZARKI TUNIS";
//
//        String nomCl = clientDetails.getFirstName()+clientDetails.getLastName();
//        String MTCl = "";
//        String contCl = clientDetails.getPhoneNumber();
//        String adrCl = "";
//
//        int textX=20;
//        int textY=40;
//
//        paint.setTextSize(10);
//
//        if ((nomEnt.length()>42) || (nomCl.length()>42)){
//            if ((nomEnt.length()>42) && (nomCl.length()<=42)){
//                Utilities.getInstance().replaceCharEntetePdf("Entreprise :",nomEnt,textY,20,canvas,paint);
//                canvas.drawText("Client : "+nomCl,315,textY,paint);
//                textY+=30;
//            }
//            else if ((nomEnt.length()<=42) && (nomCl.length()>42)) {
//                Utilities.getInstance().replaceCharEntetePdf("Client :",nomCl,textY,315,canvas,paint);
//                canvas.drawText("Entreprise : "+nomEnt,20,textY,paint);
//                textY+=30;
//            }
//            else {
//                Utilities.getInstance().replaceCharEntetePdf("Entreprise :",nomEnt,textY,20,canvas,paint);
//                Utilities.getInstance().replaceCharEntetePdf("Client :",nomCl,textY,315,canvas,paint);
//                textY+=30;
//            }
//        } else {
//            canvas.drawText("Entreprise : "+nomEnt,20,textY,paint);
//            canvas.drawText("Client : "+nomCl,315,textY,paint);
//            textY+=15;
//        }
//
//        canvas.drawText("M/F : "+MTEnt,20,textY,paint);
//        canvas.drawText("M/F : "+MTCl,315,textY,paint);
//        textY+=15;
//        canvas.drawText("N° contact : "+contEnt,20,textY,paint);
//        canvas.drawText("N° contact : "+contCl,315,textY,paint);
//        textY+=15;
//
//        if ((adrEnt.length()>42)||(adrCl.length()>42)){
//            if ((adrEnt.length()>42)&&(adrCl.length()<=42)){
//                Utilities.getInstance().replaceCharEntetePdf("Adresse :",adrEnt,textY,20,canvas,paint);
//                canvas.drawText("Adresse : "+adrCl,315,textY,paint);
//                textY+=30;
//            }else if ((adrCl.length()>42)&&(adrEnt.length()<=42)){
//                Utilities.getInstance().replaceCharEntetePdf("Adresse :",adrCl,textY,315,canvas,paint);
//                canvas.drawText("Adresse : "+adrEnt,20,textY,paint);
//                textY+=30;
//            } else {
//                Utilities.getInstance().replaceCharEntetePdf("Adresse :",adrEnt,textY,20,canvas,paint);
//                Utilities.getInstance().replaceCharEntetePdf("Adresse :",adrCl,textY,315,canvas,paint);
//                textY+=30;
//            }
//        } else {
//            canvas.drawText("Adresse : "+adrEnt,20,textY,paint);
//            canvas.drawText("Adresse : "+adrCl,315,textY,paint);
//            textY+=15;
//        }
//
//        //up&down
//
//        canvas.drawLine(10,textY,(pageInfo.getPageWidth()/2)-5, textY , linepaint);
//        canvas.drawLine((pageInfo.getPageWidth()/2)+5,textY,pageInfo.getPageWidth()-10, textY , linepaint);
//
//        //left&right
//        canvas.drawLine(10 ,20,10, textY , linepaint);
//        canvas.drawLine((pageInfo.getPageWidth()/2)-5 ,20,(pageInfo.getPageWidth()/2)-5, textY , linepaint);
//        canvas.drawLine((pageInfo.getPageWidth()/2)+5 ,20,(pageInfo.getPageWidth()/2)+5, textY , linepaint);
//        canvas.drawLine(pageInfo.getPageWidth()-10 ,20,pageInfo.getPageWidth()-10, textY , linepaint);
//
//
//
//
//        paint.setColor(Color.BLACK);
//        canvas.drawLine(10,textY+15,pageInfo.getPageWidth()-10, textY+15 , linepaint);
//
//        int y=textY+45;
//
//        paint.setTextSize(20);
//        canvas.drawText("Bon° Reception", 20, y+10, paint);
//
//        paint.setTextSize(13);
//        canvas.drawText("Date : "+Utilities.getInstance().getStringFromCalendarII(Calendar.getInstance()) ,310,y+10,paint);
//
//        y+=40;
//
//        canvas.drawLine(10,y,pageInfo.getPageWidth()-10, y , linepaint);
//
//        y+=20;
//        paint.setTextSize(11);
//
//        canvas.drawText("N° Article",55,y,paint);
//        canvas.drawText("Cond°" ,340,y,paint);
//        canvas.drawText("Qté",425,y,paint);
//        canvas.drawText("Prix TTC",490,y,paint);
//
//        y+=10;
//        canvas.drawLine(10,y,pageInfo.getPageWidth()-10, y , linepaint);
//        y+=20;
//        return y;
//
//    }
//
//    public static double roundDown5(float d) {
//        return ((long)(d * 1e3)) / 1e3;
//        //Long typecast will remove the decimals
//    }
//
//
//    private void printBon (){
//
//        sendInt = new Intent();
//        nClt = dataList.get(0).getCtNum();
//
//        if ((nomEntr.length()>20)){
//
//            BILL = String.format("%1$-10s %2$30s","Entr:"+ nomEntr.substring(0,20), "Code: "+ nClt +"\n");
//
//            BILL += String.format("%1$-10s", nomEntr.substring(20))+ "\n";
//        } else {
//            for (int i=0;i<20-nomEntr.length();i++){
//                nomEntr+=" ";
//            }
//            BILL = String.format("%1$-10s %2$30s", "Entr:"+nomEntr, "Code: "+ nClt +"\n");
//        }
//
//        if ((telEntr.length()>20)&&(clientNom.length()>20)){
//
//            BILL += String.format("%1$-10s %2$30s", "Tel/Fax:"+telEntr.substring(0,12), "Clt:"+clientNom.substring(0,20) +"\n");
//
//            BILL += String.format("%1$-10s  %2$30s", telEntr.substring(12),clientNom.substring(20)+ "\n");
//
//        } else if ((telEntr.length()>20)){
//
//            BILL += String.format("%1$-10s %2$30s","Tel/Fax:"+ telEntr.substring(0,12), "Clt:"+ clientNom +"\n");
//
//            BILL += String.format("%1$-10s ", telEntr.substring(0,12)+"\n");
//
//
//        } else if ((clientNom.length()>20)){
//            BILL += String.format("%1$-10s %2$30s", "Tel/Fax:"+telEntr,"Clt:"+  clientNom.substring(0,20) +"\n");
//
//            BILL += String.format("%1$-10s %2$30s", "                      ", clientNom.substring(20)+"\n");
//        } else {
//
//            for (int i=0;i<20-telEntr.length();i++){
//                telEntr+=" ";
//            }
//
//            BILL += String.format("%1$-10s %2$30s","Tel/Fax:"+ telEntr, "Clt:"+ clientNom +"\n");
//        }
//
//        if ((mfEntr.length()>20)&&(mfClt.length()>20)){
//
//            BILL += String.format("%1$-10s %2$30s","M/F:"+ mfEntr.substring(0,16), "M/F:"+mfClt.substring(0,16) +"\n");
//
//            BILL += String.format("%1$-10s  %2$30s",mfEntr.substring(16),mfClt.substring(16)+ "\n");
//
//        } else if ((mfEntr.length()>20)){
//
//            BILL += String.format("%1$-10s %2$30s", "M/F:"+mfEntr.substring(0,16), "M/F:"+ mfClt +"\n");
//
//            BILL += String.format("%1$-10s ", mfEntr.substring(0,16)+"\n");
//
//
//        } else if ((mfClt.length()>20)){
//            BILL += String.format("%1$-10s %2$30s","M/F:"+ mfEntr, "M/F:"+ mfClt.substring(0,16) +"\n");
//
//            BILL += String.format("%1$-10s %2$30s", "                      ",mfClt.substring(16)+"\n");
//        } else {
//
//            for (int i=0;i<20-mfEntr.length();i++){
//                mfEntr+=" ";
//            }
//
//            BILL += String.format("%1$-10s %2$30s","M/F:" +mfEntr, "M/F:"+ mfClt +"\n");
//        }
//
//        if (adressEntr.length()>20){
//
//            BILL +=String.format("%1$-10s", "Adresse: "+ adressEntr.substring(0,19)+" \n");
//
//            BILL +=String.format("%1$-10s", adressEntr.substring(19)+" \n\n");
//        }
//        BILL += "---------------------------------------------------------\n";
//
//        BILL += String.format("%1$-10s %2$32s %3$6s", "Article", "Qt", "Total")+ "\n";
//
//        BILL += "---------------------------------------------------------\n";
//
//        for (int i =0;i<dataList.size();i++){
//            if (dataList.get(i).getDlDesign().length()>40)
//            {
//                BILL +=  "\n" +String.format("%1$-10s %2$2s %3$5s ", dataList.get(i).getDlDesign().substring(0, 40), dataList.get(i).getQteColise(), dataList.get(i).getDlMontantHT() );
//
//                BILL += "\n" + String.format("%1$-10s ",  dataList.get(i).getDlDesign().substring(40));
//            } else {
//                int diff = 40-dataList.get(i).getDlDesign().length();
//                String nomArticle=dataList.get(i).getDlDesign();
//                for (int j=0; j<diff;j++){
//                    nomArticle+=" ";
//                }
//                BILL += "\n" + String.format("%1$-10s %2$2s %3$5s ", nomArticle, dataList.get(i).getQteColise(), df.format(dataList.get(i).getDlMontantHT()));
//            }
//
//        }
//
//        BILL +="\n\n" + "---------------------------------------------------------"  ;
//
//        BILL += "\n" +String.format("%1$-10s ", factNbArticle.getText().toString()) + "\n";
//
//        BILL +=String.format("%1$-10s ", factTtPrix.getText().toString()) + "\n";
//
//        BILL += "---------------------------------------------------------\n\n";
//
//        sendInt.setAction(Intent.ACTION_SEND);
//        sendInt.setPackage("mate.bluetoothprint");
//        sendInt.putExtra(Intent.EXTRA_TEXT,BILL);
//        sendInt.setType("text/plain");
//        startActivity(sendInt);
//
//    }
    @Override
    public void onSellectClientByIdSuccess(Client client) {
        clientDetails=client;
    }

    @Override
    public void onSellectClientByIdFailed() {
        Log.i("ConsultationCmdFrag", "onSellectClientByIdFailed: ");
    }

    @Override
    public void onArticleCallFailed() {

    }

    @Override
    public void onArticleCallSuccess() {

    }

    @Override
    public void getArticlesListCallback(List<Article> articleList) {

        // new callback implementation
        Double  prixApi;
        List<Article> promotedArticles = new ArrayList<>();

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_promotion);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        listView =dialog.findViewById(R.id.listView);

        Button BtnAnnuler  =dialog.findViewById(R.id.btn_cancel);
        Button BtnConfirmer  =dialog.findViewById(R.id.btn_okay);

        for(Article articleApi : articleList){


            for(OrderRequestObjectItem orderRequestObjectItem : selectedOrderRequestObject.getItems()){
                if(articleApi.getId().intValue()==orderRequestObjectItem.getArticleId()){


                    if(articleApi.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage()!=null)
                    {
                        prixApi = articleApi.getArticlePrices().get(0).getPcsPrices().get(0).getAmount() *
                                (1- articleApi.getArticlePrices().get(0).getPcsPrices().get(0).getDiscountPercentage() );
                    }else {
                         prixApi =articleApi.getArticlePrices().get(0).getPcsPrices().get(0).getAmount();
                    }

                    Double prixLocal =orderRequestObjectItem.getPrixUnitaire();

                    prixApi = prixApi*2;
                    Log.d("PRIXAPI",String.valueOf(prixApi));
                    Log.d("PRIXLOCAL",String.valueOf(prixLocal));

                    if(Double.compare(prixApi,prixLocal) == 0){
                        Log.d("EQUAL","SAME PRICE");
                    }else {
                        Log.d("DIFFERENT","DIFFERENT PRICE");
                        promotedArticles.add(articleApi);
                    }

                }
            }
        }

        if(promotedArticles.size()>0){
            popUp.hideDialog("dataload");

            listView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new PopupVh.PopupAdapter(getContext(),promotedArticles);
            listView.setAdapter(adapter);

            dialog.show();

            BtnConfirmer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    if (Utilities.getInstance().isOnline(getContext())) {

                        popUp.hideDialog("dataload");

                        Log.d("REQUEST",selectedOrderRequestObject.toString());
                        selectedOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));

                        new CommendeAPI().addFacture(getContext(), new OrderRequest(selectedOrderRequestObject), SessionManager.getInstance().getToken(getContext()), ConsultationCommandFragment.this);
                        popUp.showDialog("loadingDialog");

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
            });
            BtnAnnuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else {

            if (Utilities.getInstance().isOnline(getContext())) {

                popUp.hideDialog("dataload");
                selectedOrderRequestObject.setDate(Utilities.getInstance().getBOStringFromCalendar(Calendar.getInstance()));

                new CommendeAPI().addFacture(getContext(), new OrderRequest(selectedOrderRequestObject), SessionManager.getInstance().getToken(getContext()), ConsultationCommandFragment.this);

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
    }
    @Override
    public void onArticleInsertionError() {
    }
    @Override
    public void onArticleInsertionSuccess() {
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
                                popUp.hideDialog("loadingDialog");

                                navController.popBackStack(R.id.historiqueCoFragment, false);
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

        Toast.makeText(getContext(), "FAILUREEEEE DELETE", Toast.LENGTH_SHORT).show();
    }

//    public void sendLongSMS(String phoneNo, String msg) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            ArrayList<String> parts = smsManager.divideMessage(msg);
//            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
//            Toast.makeText(getContext(), "Message Sent",
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception ex){
//            Toast.makeText(getContext(),ex.getMessage().toString(),
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//
//    }
}
