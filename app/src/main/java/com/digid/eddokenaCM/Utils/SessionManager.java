package com.digid.eddokenaCM.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {


    private static final SessionManager ourInstance = new SessionManager();
    private final String TOKEN = "Token";
    private final String BD = "BD";
    private final String USER_ID = "userId";
    private final String USER_Co_ID = "userCoId";
    private final String USER_CATEGORIE = "userCategorie";
    private final String USER_NAME = "userName";
    private final String CLIENT_NAME = "clientName";
    private final String USER_TYPE = "userProfile";
    private final String LOGIN_DATE = "Logindate";
    private final String EXPIRE_DATE = "Expiredate";
    private final String REGISTRATION_ID = "registrationID";
    private final String USER_AUTH_ID = "userAuthID";

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
    }


    public void setToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(TOKEN, "#");
    }

    public void setUserId(Context context, String userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(USER_ID, "-1");
    }

    public void setBD(Context context, String bd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(BD, bd);
        editor.apply();
    }

    public String getBD(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(BD, "");
    }

    public void setUserCoId(Context context, long userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(USER_Co_ID, userId);
        editor.apply();
    }

    public long getUserCoId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getLong(USER_Co_ID, -1);
    }

    public void setExpireDate(Context context, Calendar calendar) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(EXPIRE_DATE, calendar.getTimeInMillis());
        editor.apply();
    }

    public long getExpireDate(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getLong(EXPIRE_DATE, -1);
    }

    public void setloginDate(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(LOGIN_DATE, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    public Calendar getSessionDate(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(prefs.getLong(LOGIN_DATE, -1));
        return calendar;
    }

    public void setUserName(Context context, String userName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public String getUserName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(USER_NAME, "");
    }

    public void setUserType(Context context, long userType) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(USER_TYPE, userType);
        editor.apply();
    }

    public long getUserType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getLong(USER_TYPE, -1);
    }

    public void setRegistrationId(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(REGISTRATION_ID, token);
        editor.apply();
    }

    public String getRegistrationId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(REGISTRATION_ID, "#");
    }

    public void setUserAuthId(Context context, long userid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(USER_AUTH_ID, userid);
        editor.apply();
    }

    public long getUserAuthId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getLong(USER_AUTH_ID, -1);
    }

    public void setUserCategorie(Context context, int userid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(USER_CATEGORIE, userid);
        editor.apply();
    }

    public int getUserCategorie(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(USER_CATEGORIE, -1);
    }

    public void setClientName(Context context, String userName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(CLIENT_NAME, userName);
        editor.apply();
    }

    public String getClientName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Preferences.DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(CLIENT_NAME, "");
    }
}
