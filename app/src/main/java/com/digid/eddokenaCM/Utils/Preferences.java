package com.digid.eddokenaCM.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Preferences {

    public static final String DIGCOM_PREFS_NAME = "DigGlobalPrefsFile";

    public static final String ID_CMD_TO_Sync = "ID_CMD_TO_Sync";

    public static final String ID_CMD = "ID_CMD";

    public static final String ID_REG_TO_Sync = "ID_REG_TO_Sync";

    public static final String ID_REG = "ID_REG";

    private static final Preferences sharedInstance = new Preferences();

    public static Preferences getInstance() {
        return sharedInstance;
    }

    private Preferences() {
    }


    public void addIdCmd(Context context, long idCmd) {

        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> idList = prefs.getStringSet(ID_CMD_TO_Sync, new HashSet<String>());
        idList.add("" + idCmd);
        editor.putStringSet(ID_CMD_TO_Sync, idList);
        editor.apply();
    }

    public void addIdReg(Context context, long idCmd) {

        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> idList = prefs.getStringSet(ID_REG_TO_Sync, new HashSet<String>());
        idList.add("" + idCmd);
        editor.putStringSet(ID_REG_TO_Sync, idList);
        editor.apply();
    }

    public void deleteAllIdCmd(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(ID_CMD_TO_Sync, new HashSet<String>());
        editor.apply();
    }

    public void deleteAllIdReg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(ID_REG_TO_Sync, new HashSet<String>());
        editor.apply();
    }

    public void deleteNotAllIdCmd(Context context, HashMap<Long, String> doPieceMap) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> idList = prefs.getStringSet(ID_CMD_TO_Sync, new HashSet<String>());
        for (Map.Entry map : doPieceMap.entrySet()) {
            idList.remove(map.getKey().toString());
        }
        editor.putStringSet(ID_CMD_TO_Sync, idList);
        editor.apply();
    }

    public void deleteNotAllIdReg(Context context, HashMap<Long, Integer> RegNoMap) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> idList = prefs.getStringSet(ID_REG_TO_Sync, new HashSet<String>());
        for (Map.Entry map : RegNoMap.entrySet()) {
            idList.remove(map.getKey().toString());
        }
        editor.putStringSet(ID_REG_TO_Sync, idList);
        editor.apply();
    }

    public List<Long> getAllIdCmdToSync(Context context) {
        List<Long> result = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> setReult = prefs.getStringSet(ID_CMD_TO_Sync, new HashSet<String>());
        for (String idStr : setReult) {
            result.add(Long.parseLong(idStr));
        }

        return result;
    }

    public List<Long> getAllIdRegToSync(Context context) {
        List<Long> result = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> setReult = prefs.getStringSet(ID_REG_TO_Sync, new HashSet<String>());
        for (String idStr : setReult) {
            result.add(Long.parseLong(idStr));
        }

        return result;
    }

    public void addSingleIdCMD(Context context, long idCmd) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(ID_CMD, idCmd);
        editor.apply();
    }

    public long getCmdId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(ID_CMD, -1);
    }

    public void addSingleIdREG(Context context, long idREG) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(ID_REG, idREG);
        editor.apply();
    }


}
