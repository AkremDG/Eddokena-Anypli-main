package com.digid.eddokenaCM;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.digid.eddokenaCM.Utils.SessionManager;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {


    ProgressBar progressBar;
    private NavController navController;


    public SplashScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Calendar calendar = Calendar.getInstance();
        Long expire = calendar.getTimeInMillis();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent notifIntent = null;
                try {
                     notifIntent = getActivity().getIntent();
                } catch (Exception exception){
                    Log.e("splashScreen", "run: ", exception);
                    navController
                            .navigate(R.id.action_splashScreenFragment_to_loginFragment,
                                    null,
                                    new NavOptions.Builder()
                                            .setPopUpTo(R.id.splashScreenFragment,
                                                    true).build()
                            );
                }

                if (notifIntent != null) {
                    if (notifIntent.getExtras() != null) {
                        if (notifIntent.getExtras().get("notif")!=null){
                            if (notifIntent.getExtras().get("notif").equals("notif")){
                                notifIntent=null;
                                Bundle notif = new Bundle();
                                notif.putInt("fromSplach",1);
                                navController
                                        .navigate(R.id.action_splashScreenFragment_to_menuCoFragment_fromNotif,
                                                notif,
                                                new NavOptions.Builder()
                                                        .setPopUpTo(R.id.splashScreenFragment,
                                                                true).build());
                            }
                        }
                        else {
                            if (SessionManager.getInstance().getToken(getContext()).equals("#")) {
                                Log.i("splash", "run1: ");
                                navController
                                        .navigate(R.id.action_splashScreenFragment_to_menuCoFragment,
                                                null,
                                                new NavOptions.Builder()
                                                        .setPopUpTo(R.id.splashScreenFragment,
                                                                true).build()
                                        );
                            } else {
                                navController
                                        .navigate(R.id.action_splashScreenFragment_to_loginFragment,
                                                null,
                                                new NavOptions.Builder()
                                                        .setPopUpTo(R.id.splashScreenFragment,
                                                                true).build()
                                        );
                            }
                        }
                    } else {
                        Log.i("splash", "run2: ");
                        if (!SessionManager.getInstance().getToken(getContext()).equals("#")) {
                            Log.i("splash", "run1: ");
                            navController
                                    .navigate(R.id.action_splashScreenFragment_to_menuCoFragment,
                                            null,
                                            new NavOptions.Builder()
                                                    .setPopUpTo(R.id.splashScreenFragment,
                                                            true).build()
                                    );
                        } else {
                            navController
                                    .navigate(R.id.action_splashScreenFragment_to_loginFragment,
                                            null,
                                            new NavOptions.Builder()
                                                    .setPopUpTo(R.id.splashScreenFragment,
                                                            true).build()
                                    );
                        }
                    }
                }

            }

        }, 2000L);
    }
}
