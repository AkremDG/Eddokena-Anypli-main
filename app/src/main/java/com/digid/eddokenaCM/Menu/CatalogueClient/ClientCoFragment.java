package com.digid.eddokenaCM.Menu.CatalogueClient;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.SellectAllClientsCallBack;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.SellectAllClientsTask;
import com.digid.eddokenaCM.Utils.ClearMemory;
import com.digid.eddokenaCM.Utils.PhoneCall;
import com.digid.eddokenaCM.Utils.RegionMultiSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientCoFragment extends Fragment implements RegionMultiSpinner.MultiSpinnerListener, ClearMemory, ClientsAdapter.OnClientListner, SellectAllClientsCallBack {

    private NavController navController;
    private RecyclerView clientsRv;
    private ClientsAdapter clientsAdapter;
    private List<Client> dataList = new ArrayList<Client>();
    private List<Client> dataListFilter = new ArrayList<Client>();

    private Client selectedClient;
    private PhoneCall phoneCall;
    private static final int CALL_REQUEST_CODE = 1;


    private ImageView navClientIv;
    private ImageView navCommandeIv;
    private ImageView navArticleIv;


    private ImageButton clientCallBtn;
    private ImageButton clientLocationBtn;

    private SearchView searchSv;
    private ImageView backIv;

    private TextView clNameTv;
    private TextView clNumTv;
    private TextView clMAtFiscTv;
    private TextView clAdrPerTv;
    private TextView clAdrLivTv;
    private TextView clChiffreTv;
    private TextView client_status_tv;
    private RegionMultiSpinner regionMSP;
    private List<String> regionList= new ArrayList<>();


    public ClientCoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_co, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        phoneCall = new PhoneCall(view.getContext());




        uiMapping(view);
        initListner(view);
        initData(view);

    }

    /*
     * Data initialization
     */

    private void initData(View view) {
        new SellectAllClientsTask(view.getContext(), this::onSelectionSuccess).execute();
    }

    /*
     * Set components listners
     */

    private void initListner(View view) {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        clientCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCall.makePhoneCall(selectedClient.getPhoneNumber());
            }
        });

        clientLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+selectedClient.getDefaultShop().getLatitude()+","+selectedClient.getDefaultShop().getLogitude());
                //Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + selectedClient.getCtAdresse() + ", " + selectedClient.getCtVille());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        if (navCommandeIv != null && navArticleIv != null){
            navCommandeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle fragmentId = new Bundle();
                    fragmentId.putString("ClientNom", selectedClient.getLastName()+" "+selectedClient.getFirstName());
                    fragmentId.putInt("ClientCat", 0);
                    fragmentId.putString("ClientCtNum", selectedClient.getSageCode());
                    navController.navigate(R.id.action_clientCoFragment_to_comArticleCoFragment_profCO, fragmentId);
                }
            });


            navArticleIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.action_clientCoFragment_to_articleCoFragment_profCO);
                }
            });

        }


    }
    /*
     * Mapping of layout's components
     */
    private void uiMapping(View view) {
        clientsRv = view.findViewById(R.id.clientco_clients_rv);
        backIv = view.findViewById(R.id.clientco_back_iv);

        clientCallBtn = view.findViewById(R.id.clientco_call_ibtn);
        clientLocationBtn = view.findViewById(R.id.clientco_location_ibtn);

        navArticleIv = view.findViewById(R.id.clientco_navarticle_iv);
        navClientIv = view.findViewById(R.id.clientco_navclient_iv);
        navCommandeIv = view.findViewById(R.id.clientco_navcommande_iv);

        searchSv = view.findViewById(R.id.clientco_search_sv);

        clNameTv = view.findViewById(R.id.clientco_name_tv);
        clNumTv = view.findViewById(R.id.clientco_num_tv);
        clAdrLivTv = view.findViewById(R.id.clientco_adrLiv_tv);
        clChiffreTv = view.findViewById(R.id.clientco_chiff_tv);
        clMAtFiscTv = view.findViewById(R.id.clientco_matfisc_tv);

        regionMSP=view.findViewById(R.id.clientco_region_spinner);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        clearAppMemory();
    }

    /*
     * Cleaning variables
     */

    @Override
    public void clearAppMemory() {

        clientsRv = null;
        backIv = null;
        dataList = null;

        clientCallBtn = null;
        clientLocationBtn = null;

        navArticleIv = null;
        navClientIv = null;
        navCommandeIv = null;

        searchSv = null;

        clNameTv = null;
        clNumTv = null;
        clAdrLivTv = null;
        clChiffreTv = null;
        clMAtFiscTv = null;

        System.gc();
    }

    /*
     * Selecting client
     */

    @Override
    public void onClientClick(int position) {

        displayClientInfo(position);
    }

    /*
     * Clients's selection locally callback
     */

    @Override
    public void onSelectionSuccess(List<Client> clientsList) {
        if (clientsList.size() != 0){
            if(dataList!=null){
                dataList.clear();

            }
            dataList.addAll(clientsList);
            if (dataList.size() != 0) {
                selectedClient = dataList.get(0);
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
            clientsRv.setLayoutManager(layoutManager);
            clientsRv.setHasFixedSize(true);
            clientsAdapter = new ClientsAdapter(dataList, ClientCoFragment.this::onClientClick);
            clientsRv.setAdapter(clientsAdapter);


            searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    displayNullClientInfo();
                    clientsAdapter.getFilter().filter(newText);
                    return false;
                }
            });
            if (dataList.size() != 0) {
                displayClientInfo(0);
            }
        }

    }

    /*
     * Displaying client infos when clicked
     */

    public void displayClientInfo(int position) {

          selectedClient = dataList.get(position);
          clNameTv.setText(selectedClient.getLastName()+" "+selectedClient.getFirstName());
          clNumTv.setText("Sage Code : " + selectedClient.getSageCode());
        clMAtFiscTv.setText("Client Risk : "+ selectedClient.getClientRisk() );
        clAdrLivTv.setText("Payment Method : "+ selectedClient.getPaymentMethod());
        clChiffreTv.setText("Budget Limit : "+ selectedClient.getBudgetLimit());



        /*

           */

        clientCallBtn.setVisibility(View.VISIBLE);
        clientLocationBtn.setVisibility(View.VISIBLE);


    }
    public void displayNullClientInfo() {

        clNameTv.setText("");
        clNumTv.setText("");
        clMAtFiscTv.setText("");
        clAdrLivTv.setText("");
        clChiffreTv.setText("");
//        client_status_tv.setText("");

        clientCallBtn.setVisibility(View.INVISIBLE);
        clientLocationBtn.setVisibility(View.INVISIBLE);
    }
    /*
     * Phone Call permission request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phoneCall.makePhoneCall(selectedClient.getPhoneNumber());
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRegionSelected(boolean[] selected) {
    }
}
