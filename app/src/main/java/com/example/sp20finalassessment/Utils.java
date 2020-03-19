package com.example.sp20finalassessment;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by hp on 3/14/2017.
 */

public class Utils {
    /* TODO Part 5
     * implement getRandomPhrase on a thread pool of size 1. Use a callable to make a GET request on
     * this urlString: "https://api.whatdoestrumpthink.com/api/v1/quotes/random". You'll probably
     * need to actually go to the URL to see the JSON structure to know what String you want (don't
     * worry, it's a very simple JSON file.)
     *
     * convertStreamToString has been provided
     *
     * Note: if you can't remember how to use a Callable, you can get partial credit without one!
     */
    static String getRandomPhrase() throws Exception {
        ExecutorService executorService= Executors.newFixedThreadPool(1);
        final String[] randomPhrase = {""};
        RequestQueue mQueue = Volley.newRequestQueue(GameActivity.getAppContext());
        String URL = "https://api.whatdoestrumpthink.com/api/v1/quotes/random";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            randomPhrase[0] = response.getString("message");
                            System.out.println(randomPhrase[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        return randomPhrase[0];
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
