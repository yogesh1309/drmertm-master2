package com.drmertm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    private SharedPreferences pref;
    private Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "login";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERID = "userid";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_FCM_TOKEN = "fcmtoken";
    private static final String KEY_DEVICE_ID = "deviceid";
    private static final String KEY_FCM_TOKEN_ID = "fcmtokenid";
    private static final String KEY_EMP_NO = "empno";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setSavedEmpNo(String userid) {
        editor.putString(KEY_EMP_NO, userid);
        editor.commit();
    }


    public String getSavedEmpno() {
        return pref.getString(KEY_EMP_NO, "");
    }


    public void setSavedFcmTokenId(Integer userid) {
        editor.putInt(KEY_FCM_TOKEN_ID, userid);
        editor.commit();
    }


    public Integer getSavedFcmTokenId() {
        return pref.getInt(KEY_FCM_TOKEN_ID, 0);
    }



    public void setSavedDeviceid(String userid) {
        editor.putString(KEY_DEVICE_ID, userid);
        editor.commit();
    }


    public String getSavedStringDeviceid() {
        return pref.getString(KEY_DEVICE_ID, "");
    }

    public void setSavedFcmtoken(String userid) {
        editor.putString(KEY_FCM_TOKEN, userid);
        editor.commit();
    }


    public String getSavedStringFcmtoken() {
        return pref.getString(KEY_FCM_TOKEN, "");
    }


    //-----------------------------------USERID-------------------------------------------

    //save user name to SharedPref
    public void setSavedUserid(String userid) {
        editor.putString(KEY_USERID, userid);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedUserid() {
        return pref.getString(KEY_USERID, "");
    }


    //-----------------------------------TOKEN-------------------------------------------

    //save user name to SharedPref
    public void setSavedToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedToken() {
        return pref.getString(KEY_TOKEN, "");
    }



    //-----------------------------------------------------------------------------------




    //save user name to SharedPref
    public void setSavedUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }


    public boolean isUserLogin() {
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setUserLoggedIn(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}
