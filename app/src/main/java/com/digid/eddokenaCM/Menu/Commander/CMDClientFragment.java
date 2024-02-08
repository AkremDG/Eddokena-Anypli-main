package com.digid.eddokenaCM.Menu.Commander;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Models.ClientScope;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.SellectAllClientsCallBack;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.SellectAllClientsTask;
import com.digid.eddokenaCM.Utils.ClearMemory;
import com.digid.eddokenaCM.Utils.PopManager;
import com.digid.eddokenaCM.Utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CMDClientFragment extends Fragment implements ClearMemory, CMDClientAdapter.OnClientListner, SellectAllClientsCallBack {

    private NavController navController;
    private RecyclerView clientsRv;
    private CMDClientAdapter clientsAdapter;
    private List<Client> dataList = new ArrayList<Client>();


    private static final int CALL_REQUEST_CODE = 1;


    private SearchView searchSv;
    private ImageView backIv;

    private ImageView valideClientIv;


    private PopManager popUp;

    public CMDClientFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_com_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        popUp = new PopManager(view.getContext());

        uiMapping(view);
        initListner(view);
        initData(view);

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

    }

    /*
     * Mapping of layout's components
     */

    private void uiMapping(View view) {
        clientsRv = view.findViewById(R.id.comclientco_clients_rv);
        backIv = view.findViewById(R.id.comclientco_back_iv);
        searchSv = view.findViewById(R.id.comclientco_search_sv);
    }

    /*
     * Data initialization
     */

    private void initData(View view) {

        popUp.showDialog("loadingDialog");

        new SellectAllClientsTask(view.getContext(), this::onSelectionSuccess).execute();
    }
    /*
     * Selecting the client
     */
    @Override
    public void onClientClick(int position) {


        Bundle fragmentId = new Bundle();

        fragmentId.putString("ClientNom", dataList.get(position).getFirstName()+" "+dataList.get(position).getLastName());
        fragmentId.putLong("ClientCat", dataList.get(position).getCategoryId());
        fragmentId.putLong("ClientCtNum", dataList.get(position).getId());

        if(dataList.get(position).getClassId()!=null
        ){
            fragmentId.putInt("classId",dataList.get(position).getClassId());
        }
        if(dataList.get(position).getZoneId()!=null){
            fragmentId.putInt("zoneId",dataList.get(position).getZoneId());
        }

        if(dataList.get(position).getCategoryId()>=0){
            fragmentId.putLong("categoryId",dataList.get(position).getCategoryId());
        }

        fragmentId.putLong("clientId",dataList.get(position).getId());

        Log.i("rrrrrrrrr", "onClientClick: "+ dataList.get(position).getId());

        String scopes="";
        for (ClientScope clt: dataList.get(position).getClientScopes()) {

            scopes+=String.valueOf(clt.getCatalogId())+",";

        }


        fragmentId.putString("CatalogId", dataList.get(position).getClientScopes().get(0).getCatalogId());

        fragmentId.putString("ClientScope", scopes);


        navController.navigate(R.id.action_comClientFragment_to_comArticleCoFragment, fragmentId);
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

    /*
     * Cleaning variables
     */

    @Override
    public void clearAppMemory() {

        backIv = null;
        searchSv = null;

        dataList = null;
        clientsRv = null;

        System.gc();

    }

    /*
     * Clients's selection locally callback
     */

    @Override
    public void onSelectionSuccess(List<Client> clientsList) {
        popUp.hideDialog("loadingDialog");

        if(dataList!=null)
        {
            dataList.clear();

        }
        dataList.addAll(clientsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        clientsRv.setLayoutManager(layoutManager);
        clientsRv.setHasFixedSize(true);
        clientsAdapter = new CMDClientAdapter(dataList, this::onClientClick);
        clientsRv.setAdapter(clientsAdapter);


        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchSv.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                clientsAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }

}

