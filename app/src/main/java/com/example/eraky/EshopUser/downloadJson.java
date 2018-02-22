package com.example.eraky.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mine on 10/02/18.
 */

public class downloadJson extends AsyncTask<Void,Void,String> {


    @Override
    protected String doInBackground(Void... voids) {

        try {

            Log.d("ahmed123", "doInBackground: "
                    +run("https://eshopeandroidapp.000webhostapp.com/isRated.php?uemail=\""
                    + FirebaseAuth.getInstance().getCurrentUser().getUid()+"\"&pid=76"));

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ahmed123", "doInBackground: "+e.toString());

        }

        return "a";
    }
    String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
