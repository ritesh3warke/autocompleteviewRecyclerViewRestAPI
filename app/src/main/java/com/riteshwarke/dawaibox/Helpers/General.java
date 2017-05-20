package com.riteshwarke.dawaibox.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonElement;
import com.riteshwarke.dawaibox.Models.DrugSearch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ritesh Warke on 18/05/17.
 */

public class General {

    public static void setSharedPreferences(Context context, String prefName, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(prefName, value);
        editor.commit();
    }

    public static String getSharedPreferences(Context context, String prefName) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(prefName, "");

    }

}
