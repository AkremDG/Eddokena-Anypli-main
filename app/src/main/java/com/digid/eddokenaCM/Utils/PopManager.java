package com.digid.eddokenaCM.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digid.eddokenaCM.Menu.Commander.CMDArticleCoFragment;
import com.digid.eddokenaCM.R;

public class PopManager {
    private Context context;
    private Dialog dataLoadDialog, connectionErrorDialog, authenticationErrorDialog, wsErrorDialog,
            conditionDialog, promoDialog,inscriptionDialog,connectionErrorDialogCancelable,loadingDialog;
    private TextView wsErrorMessage;

    private NavController navController;
    public PopManager(Context context) {
        this.context = context;
    }


    public void showDialog(String dialogType) {



        if (dialogType.equals("dataload")) {

            dataLoadDialog = new Dialog(context);
            dataLoadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dataLoadDialog.setCancelable(false);
            dataLoadDialog.setContentView(R.layout.dataload_dialog);
            dataLoadDialog.show();

        } else if (dialogType.equals("connectionErrorCancelable")) {
            connectionErrorDialogCancelable = new Dialog(context);
            connectionErrorDialogCancelable.requestWindowFeature(Window.FEATURE_NO_TITLE);
            connectionErrorDialogCancelable.setCancelable(true);
               connectionErrorDialogCancelable.setContentView(R.layout.connection_error_dialog);
           Button okBtn = connectionErrorDialogCancelable.findViewById(R.id.articlecom_cancel_btn);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectionErrorDialogCancelable.dismiss();
                }
            });
            connectionErrorDialogCancelable.show();

        }
        else if (dialogType.equals("connectionerror")) {
            connectionErrorDialog = new Dialog(context);
            connectionErrorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            connectionErrorDialog.setCancelable(false);
            connectionErrorDialog.setContentView(R.layout.connection_error_dialog);
            connectionErrorDialog.show();

        } else if (dialogType.equals("authenticationerror")) {
            authenticationErrorDialog = new Dialog(context);
            authenticationErrorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            authenticationErrorDialog.setCancelable(false);
            authenticationErrorDialog.setContentView(R.layout.authentification_error_dialog);
            authenticationErrorDialog.show();
        } else if (dialogType.equals("condition")) {
            conditionDialog = new Dialog(context);
            conditionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            conditionDialog.setCancelable(false);
            conditionDialog.setContentView(R.layout.conditionnement_dialog);
            conditionDialog.show();
        } else if (dialogType.equals("promo")) {
            promoDialog = new Dialog(context);
            promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            promoDialog.setCancelable(false);
            promoDialog.setContentView(R.layout.promo_dialog);
            promoDialog.show();
        } else if (dialogType.equals("inscription")) {
            inscriptionDialog = new Dialog(context);
            inscriptionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            inscriptionDialog.setCancelable(false);
            inscriptionDialog.setContentView(R.layout.inscription_dialog);
            inscriptionDialog.show();
        } else if (dialogType.equals("loadingDialog")) {
            loadingDialog = new Dialog(context);
            loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loadingDialog.setCancelable(false);
            loadingDialog.setContentView(R.layout.loading_dialog);
            loadingDialog.show();

        }
    }


    public void showWSErrorDialog(String dialogType) {

        wsErrorDialog = new Dialog(context);
        wsErrorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        wsErrorDialog.setCancelable(false);
        wsErrorDialog.setContentView(R.layout.ws_error_dialog);
        wsErrorMessage = wsErrorDialog.findViewById(R.id.ws_error_tv);
        wsErrorMessage.setText(dialogType);
        wsErrorDialog.show();

    }


    public void hideWSErrorDialog() {
        wsErrorDialog.dismiss();
    }


    public void hideDialog(String dialogType) {
        if (dialogType.equals("dataload"))
            dataLoadDialog.dismiss();
        else if (dialogType.equals("connectionerror"))
            connectionErrorDialog.dismiss();
        else if (dialogType.equals("authenticationerror"))
            authenticationErrorDialog.dismiss();
        else if (dialogType.equals("condition"))
            conditionDialog.hide();
        else if (dialogType.equals("promo"))
            promoDialog.hide();
        else if (dialogType.equals("inscription"))
            inscriptionDialog.hide();
        else if (dialogType.equals("loadingDialog"))
            loadingDialog.hide();
    }

    public Dialog getDataLoadDialog() {
        return dataLoadDialog;
    }

    public void setDataLoadDialog(Dialog dataLoadDialog) {
        this.dataLoadDialog = dataLoadDialog;
    }

    public Dialog getConnectionErrorDialog() {
        return connectionErrorDialog;
    }

    public void setConnectionErrorDialog(Dialog connectionErrorDialog) {
        this.connectionErrorDialog = connectionErrorDialog;
    }

    public Dialog getAuthenticationErrorDialog() {
        return authenticationErrorDialog;
    }

    public void setAuthenticationErrorDialog(Dialog authenticationErrorDialog) {
        this.authenticationErrorDialog = authenticationErrorDialog;
    }

    public Dialog getWsErrorDialog() {
        return wsErrorDialog;
    }

    public void setWsErrorDialog(Dialog wsErrorDialog) {
        this.wsErrorDialog = wsErrorDialog;
    }

    public Dialog getConditionDialog() {
        return conditionDialog;
    }

    public void setConditionDialog(Dialog conditionDialog) {
        this.conditionDialog = conditionDialog;
    }

    public Dialog getPromoDialog() {
        return promoDialog;
    }

    public void setPromoDialog(Dialog promoDialog) {
        this.promoDialog = promoDialog;
    }

    public Dialog getInscriptionDialog() {
        return inscriptionDialog;
    }

    public void setInscriptionDialog(Dialog inscriptionDialog) {
        this.inscriptionDialog = inscriptionDialog;
    }
}
