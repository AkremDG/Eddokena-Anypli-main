package com.digid.eddokenaCM.Authentification;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.InitArticleTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteAllTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.InitCatalogueTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.InitEnteteTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.InitLigneTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.InitClientsTableCallback;
import com.digid.eddokenaCM.Utils.DecompressCallback;
import com.digid.eddokenaCM.Utils.PopManager;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.Utils.Utilities;
import com.digid.eddokenaCM.WebServices.Articles.ArticlesAPI;
import com.digid.eddokenaCM.WebServices.Authentification.AuthenticationCallback;
import com.digid.eddokenaCM.WebServices.Authentification.AuthentificationAPI;
import com.digid.eddokenaCM.WebServices.Catalogue.CatalogueAPI;
import com.digid.eddokenaCM.WebServices.Clients.ClientsAPI;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* Login Fragment */

public class LoginFragment extends Fragment implements AuthenticationCallback, DeleteAllCallback,
        DecompressCallback, InitEnteteTableCallback, InitLigneTableCallback, InitArticleTableCallback,
        InitClientsTableCallback, InitCatalogueTableCallback {
    private NavController navController;
    private TextInputEditText loginEt;
    private TextInputEditText mdpEt;
    private Button connectBtn;
    private Button signUpBtn;
    private int i = 0;
    private PopManager popUp;
    private Button closeConnectionErrorDialog, closeAuthentificationDialog;
    private Dialog connectionDialog, authentificationDialog;
    private List<Boolean> wsResultList = new ArrayList<>();
    private Dialog connectionDialogWS, wsDialog;
    private Button reesayerWsErrorDialog, closeWsErrorDialog;
    private String errorMessage = "";
    private TextView dialogMsg;
    private ProgressBar dialogProgresBar;

    private Dialog errorDialog;
    private TextView dialogErrorTv;

    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;


    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermission();
        navController = Navigation.findNavController(view);
        popUp = new PopManager(view.getContext());

        uiMapping(view);
        initListner(view);

    }

    /*
     * Mapping of layout's components
     */

    private void uiMapping(View view) {

        loginEt = view.findViewById(R.id.login_Et);
        mdpEt = view.findViewById(R.id.mdp_Et);
        connectBtn = view.findViewById(R.id.login_button);
        signUpBtn = view.findViewById(R.id.inscription_btn);

        loginEt.setText("riadh@ekmandi.com");
        mdpEt.setText("password");

    }

    /*
     * Set components listners
     */

    private void initListner(View view) {
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utilities.getInstance().isOnline(getContext())) {
                    popUp.showDialog("dataload");
                    connectionDialogWS = popUp.getDataLoadDialog();
                    new AuthentificationAPI().getToken(view.getContext(), loginEt.getText().toString(), mdpEt.getText().toString(), LoginFragment.this);
                    //SessionManager.getInstance().setToken(getContext(),"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsInZlcnNpb24iOmZhbHNlfQ.eyJzdWIiOiIxIiwiaW50aXR1bGUiOiJBZ2VudCBjb21tZXJjaWFsIDEiLCJpZERlcG90IjoiMSJ9.rxVE97faH_PwsP0258-3WQ4jXIB3LOkBxGvjHlx_gi1z_E2YXHgDyWdIUcBwpPEKvO31PoNI7UOVVeikX37scJgJvvn5xyia-IRXulTke9PBrwx83SEOzJ8Dezz2iIXMk6RuL6FCCyOuXLcrtQp-Pk4zUDItOA4xPXFOkmQJq2E");
                } else {
                    popUp.showDialog("connectionerror");
                    connectionDialog = popUp.getConnectionErrorDialog();
                    closeConnectionErrorDialog = connectionDialog.findViewById(R.id.articlecom_cancel_btn);
                    closeConnectionErrorDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popUp.hideDialog("connectionerror");
                        }
                    });

                }

            }
        });
    }

    /*
     * Authentication's success callback
     */

    @Override
    public void authenticationSuccess(){
        dialogMsg = connectionDialogWS.findViewById(R.id.data_loading_TV);
        dialogMsg.setText("Veuillez patienter ...");
        new DeleteAllTask(getContext(),0, LoginFragment.this).execute();
    }

    /* Authentication's failer callback */
    @Override
    public void authenticationFailed() {
        popUp.hideDialog("dataload");
        popUp.showDialog("authenticationerror");
        authentificationDialog = popUp.getAuthenticationErrorDialog();
        closeAuthentificationDialog = authentificationDialog.findViewById(R.id.articlecom_cancel_btn);
        closeAuthentificationDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.hideDialog("authenticationerror");
            }
        });

    }

    /*
     * check WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION permissions are granted, otherwise ask for them
     * @return true if user allowed access
     * */

    protected void checkPermission(){
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                + ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION)
                + ContextCompat.checkSelfPermission(getContext(),Manifest.permission.SEND_SMS)
                + ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(),Manifest.permission.SEND_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Send sms, Camera, Read Contacts and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.SEND_SMS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(getContext(),"Autorisations déjà accordées",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        + grantResults[3]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    // Permissions are granted
                    Toast.makeText(getContext(),"Permissions granted.",Toast.LENGTH_SHORT).show();
                }else {
                    // Permissions are denied
                    Toast.makeText(getContext(),"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    /*
     * Delete's Callbacks
     */

    @Override
    public void onDeleteSucces() {
        wsResultList.clear();
        if (Utilities.getInstance().isOnline(getContext())) {
            new ArticlesAPI().getArticles(getContext(), this);
            new ClientsAPI().getClients(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), SessionManager.getInstance().getToken(getContext()), this);
            new CatalogueAPI().getCatalogue(getContext(), SessionManager.getInstance().getToken(getContext()), this);
            //downLoadImgFiles();
        } else {
            popUp.showDialog("connectionerror");
            connectionDialog = popUp.getConnectionErrorDialog();
            closeConnectionErrorDialog = connectionDialog.findViewById(R.id.articlecom_cancel_btn);
            closeConnectionErrorDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp.hideDialog("connectionerror");
                }
            });
        }

    }

    @Override
    public void onDelteError(int result) {
        Toast.makeText(getContext(), " FAILLLLLLLLL", Toast.LENGTH_SHORT).show();

    }

    /*
     * Verification of all WS's callbacks results
     */

    private void allWSResult() {

        if (wsResultList.size() == 6) {
            boolean isOk = true;
            for (boolean item : wsResultList) {
                if (!item) {
                    isOk = false;}
            }
            if (isOk) {
                Log.i("loginn", "allWSResult: handler");
                popUp.hideDialog("dataload");
                Log.i("loginn", "allWSResult: isOk");


                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            navController.navigate(R.id.action_loginFragment_to_menuCoFragment);
                        } catch (Exception e) {
                            Log.d("Exceptionnnnnn", e.getMessage());
                        }
                    }
                }, 2000);
                /*
                navController.navigate(R.id.action_loginFragment_to_menuCoFragment);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            navController.navigate(R.id.action_loginFragment_to_menuCoFragment);

                        }
                    }, 2000);

                 */

            }
            else {
                popUp.hideDialog("dataload");
                if (errorDialog==null){

                    popUp.showWSErrorDialog(errorMessage);
                    errorDialog=popUp.getWsErrorDialog();
                } else {
                    dialogErrorTv = popUp.getWsErrorDialog().findViewById(R.id.ws_error_tv);
                    dialogErrorTv.setText(errorMessage.trim());
                }
                reesayerWsErrorDialog = errorDialog.findViewById(R.id.ws_reesayer_btn);
                reesayerWsErrorDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.hideWSErrorDialog();
                        errorDialog=null;
                        popUp.showDialog("dataload");
                        wsResultList = new ArrayList<>();
                        errorMessage = "";

                        new ArticlesAPI().getArticles(getContext(), LoginFragment.this);
                        new ClientsAPI().getClients(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), SessionManager.getInstance().getToken(getContext()), LoginFragment.this);
                        new CatalogueAPI().getCatalogue(getContext(), SessionManager.getInstance().getToken(getContext()), LoginFragment.this);
                        //downLoadImgFiles();
                        allWSResult();

                    }
                });
                closeWsErrorDialog = errorDialog.findViewById(R.id.ws_quitter_btn);
                closeWsErrorDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SessionManager.getInstance().setToken(getContext(), "#");
                        SessionManager.getInstance().setUserId(getContext(), "-1");
                        SessionManager.getInstance().setRegistrationId(getContext(), "#");
                        errorDialog=null;
                        popUp.hideWSErrorDialog();
                        wsResultList.clear();
                        errorMessage="";


                    }
                });

            }


        }
        else {
            boolean isOk = true;
            for (boolean item : wsResultList) {
                if (!item) {
                    isOk = false;
                }
            }
            if (!isOk){

                SessionManager.getInstance().setToken(getContext(), "#");
                SessionManager.getInstance().setUserId(getContext(), "-1");
                SessionManager.getInstance().setRegistrationId(getContext(), "#");
                Log.i("loginn", "else: ");
                popUp.hideDialog("dataload");
                if (errorDialog==null){
                    popUp.showWSErrorDialog(errorMessage);
                    errorDialog=popUp.getWsErrorDialog();
                } else {
                    dialogErrorTv = popUp.getWsErrorDialog().findViewById(R.id.ws_error_tv);
                    dialogErrorTv.setText(errorMessage.trim());

                }
                reesayerWsErrorDialog = errorDialog.findViewById(R.id.ws_reesayer_btn);
                reesayerWsErrorDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.hideWSErrorDialog();
                        errorDialog=null;
                        popUp.showDialog("dataload");
                        wsResultList = new ArrayList<>();
                        errorMessage = "";

                        new ArticlesAPI().getArticles(getContext(), LoginFragment.this);
                        new ClientsAPI().getClients(getContext(), Long.parseLong(SessionManager.getInstance().getUserId(getContext())), SessionManager.getInstance().getToken(getContext()), LoginFragment.this);
                        new CatalogueAPI().getCatalogue(getContext(), SessionManager.getInstance().getToken(getContext()), LoginFragment.this);
                        //downLoadImgFiles();
                        allWSResult();

                    }
                });
                closeWsErrorDialog = errorDialog.findViewById(R.id.ws_quitter_btn);
                closeWsErrorDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SessionManager.getInstance().setToken(getContext(), "#");
                        SessionManager.getInstance().setUserId(getContext(), "-1");
                        SessionManager.getInstance().setRegistrationId(getContext(), "#");
                        popUp.hideWSErrorDialog();
                        errorDialog=null;
                        wsResultList.clear();
                        errorMessage="";
                        Log.i("loginn", "onClick: ");

                    }
                });
            }
        }
    }


    /*
     * Download articles images zip file
     */

    private void downLoadImgFiles() {

        wsResultList.add(true);
        wsResultList.add(true);
        allWSResult();
        /*if (!Utilities.getInstance().isArticleImgsDownloaded(getContext())) {

            Log.i("TestDownload", "downLoadImgFiles: ");
            DownloadFileAsync download = new DownloadFileAsync(getContext(), Environment.getExternalStorageDirectory() + "/ArticleImgs/Multimedia.zip", new DownloadFileAsync.PostDownload() {
                @Override
                public void downloadDone() {
                    // check unzip file now
                    wsResultList.add(true);
                    allWSResult();
                    //File file = new File(Environment.getExternalStorageDirectory(), "/SccArticleImgs/Ar_Photos.zip");
                    File file;
                    if (android.os.Build.VERSION.SDK_INT >= 30){
                        file = new File(getContext().getExternalFilesDir("").getAbsolutePath(), "/ArticleImgs/Multimedia.zip");
                    } else{
                        file = new File(Environment.getExternalStorageDirectory(), "/ArticleImgs/Multimedia.zip");
                    }
                    new DecompressAync( file, LoginFragment.this).execute();
                }

                @Override
                public void downloadError() {

                    errorMessage = errorMessage + "\n Telechargement des images échoué";
                    wsResultList.add(false);
                    allWSResult();
                }
            });
            download.execute();
        } else {
            wsResultList.add(true);
            wsResultList.add(true);
            allWSResult();
        }*/

    }

    /*
     * Decompress zip file's callback
     */

    @Override
    public void decompressSuccess() {

        Log.i("loginn", "decompressSuccess: ");
        wsResultList.add(true);
        allWSResult();
        if (android.os.Build.VERSION.SDK_INT >= 30){
            new File(getContext().getExternalFilesDir("").getAbsolutePath(), "/ArticleImgs/Multimedia.zip").delete();
            File oldFolder = new File(getContext().getExternalFilesDir("").getAbsolutePath(), "/ArticleImgs");
            File newFolder = new File(getContext().getExternalFilesDir("").getAbsolutePath(), "/.ArticleImgs");
            boolean success = oldFolder.renameTo(newFolder);
            new File(getContext().getExternalFilesDir("").getAbsolutePath() + "/ArticleImgs").delete();
        } else{
            new File(Environment.getExternalStorageDirectory(), "/ArticleImgs/Multimedia.zip").getAbsoluteFile().delete();
            File oldFolder = new File(Environment.getExternalStorageDirectory(), "/ArticleImgs");
            File newFolder = new File(Environment.getExternalStorageDirectory(), "/.ArticleImgs");
            boolean success = oldFolder.renameTo(newFolder);
            new File(Environment.getExternalStorageDirectory() + "/ArticleImgs").getAbsoluteFile().delete();
        }
    }

    @Override
    public void decompressFailled() {
        Log.i("loginn", "decompressFailled: ");
        errorMessage = errorMessage + "\n Decompression des images échoué";
        wsResultList.add(false);
        allWSResult();
    }

    /*
     * Aritcle's WS & Insertion in DB, CALLBACKS
     */

    @Override
    public void onArticleCallFailed() {
        Log.i("loginn", "onArticleCallFailed: ");
        errorMessage = errorMessage + "\n Appel de catalogue article échoué";
        wsResultList.add(false);
        allWSResult();

    }

    @Override
    public void onArticleCallSuccess() {
        Log.i("loginn", "onArticleCallSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }

    @Override
    public void getArticlesListCallback(List<Article> articleList) {

    }

    @Override
    public void onArticleInsertionError() {
        Log.i("loginn", "onArticleInsertionError: ");
        errorMessage = errorMessage + "\n Insertion des articles échoué";
        wsResultList.add(false);
        allWSResult();
    }
    @Override
    public void onArticleInsertionSuccess() {
        Log.i("loginn", "onArticleInsertionSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }
    /*
     * Client's WS & Insertion in DB, CALLBACKS
     */
    @Override
    public void onClientCallFailed() {
        Log.i("loginn", "onClientCallFailed: ");
        errorMessage = errorMessage + "\n Appel de catalogue clients échoué";
        wsResultList.add(false);
        allWSResult();
    }
    @Override
    public void onClientCallSuccess() {
        Log.i("loginn", "onClientCallSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }

    @Override
    public void onClientInsertionError() {
        Log.i("loginn", "onClientInsertionError: ");
        errorMessage = errorMessage + "\n Insertion des clients  failed";
        wsResultList.add(false);
        allWSResult();
    }

    @Override
    public void onClientInsertionSuccess() {
        Log.i("loginn", "onClientInsertionSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }

    /*
     * Catalog's WS & Insertion in DB, CALLBACKS
     */

    @Override
    public void onCatalogueCallFailed() {
        Log.i("loginn", "onCatalogueCallFailed: ");
        errorMessage = errorMessage + "\n Appel de catalogue échoué";
        wsResultList.add(false);
        allWSResult();
    }

    @Override
    public void onCatalogueCallSuccess() {
        Log.i("loginn", "onCatalogueCallSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }

    @Override
    public void onCatalogueInsertionError() {
        Log.i("loginn", "onCatalogueInsertionError: ");
        errorMessage = errorMessage + "\n Insertion de catalogue  échoué";
        wsResultList.add(false);
        allWSResult();
    }

    @Override
    public void onCatalogueInsertionSuccess(long[] id) {
        Log.i("loginn", "onCatalogueInsertionSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }

    /*
     * Orders's WS & Insertion in DB, CALLBACKS
     */

    @Override
    public void onAllEnteteInsertionError() {
        Log.i("loginn", "onAllEnteteInsertionError: ");
        errorMessage = errorMessage + "\n Insertion des entetes  échoué";
        wsResultList.add(false);
        allWSResult();
    }

    @Override
    public void onAllEnteteInsertionSuccess() {
        Log.i("loginn", "onAllEnteteInsertionSuccess: ");
        wsResultList.add(true);
        allWSResult();

    }

    @Override
    public void onEnteteCallFailed() {
        Log.i("loginn", "onEnteteCallFailed: ");
        errorMessage = errorMessage + "\n Appel des entetes  échoué";
        wsResultList.add(false);
        allWSResult();
    }

    @Override
    public void onEnteteCallSuccess() {
        Log.i("loginn", "onEnteteCallSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }

    @Override
    public void onLigneCallFailed() {
        Log.i("loginn", "onLigneCallFailed: ");
        errorMessage = errorMessage + "\n Appel des lignes  échoué";
        wsResultList.add(false);
        allWSResult();
    }
    @Override
    public void onLigneCallSuccess() {
        Log.i("loginn", "onLigneCallSuccess: ");
        wsResultList.add(true);
        allWSResult();

    }
    @Override
    public void onLigneInsertionError() {
        Log.i("loginn", "onLigneInsertionError: ");
        errorMessage = errorMessage + "\n Insertion des lignes  échoué";
        wsResultList.add(false);
        allWSResult();
    }
    @Override
    public void onLigneInsertionSuccess() {
        Log.i("loginn", "onLigneInsertionSuccess: ");
        wsResultList.add(true);
        allWSResult();
    }
}
