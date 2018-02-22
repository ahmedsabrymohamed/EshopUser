package com.example.eraky.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import retrofit2.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    static  final int GOOGLE_SIGN_IN=1;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.google_sign_in).setOnClickListener(this);
        if( isNetworkAvailable()) {

            if (mAuth.getCurrentUser() != null) {
                finish();
                startActivity(new Intent(this, AllCategoryActivity.class));
            }
        }



    }

    public void setGoogleSignIn(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false)
                        .build(),
                GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GOOGLE_SIGN_IN&&resultCode==RESULT_OK){

            startActivity(new Intent(this, AllCategoryActivity.class));
        }
        else
        {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.main_layout),status, Snackbar.LENGTH_LONG);
            snackbar.show();
            Log.d("ahmed123", "onActivityResult: "+requestCode);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.google_sign_in:
                updateStatus();
                setGoogleSignIn();
                break;
        }

    }
    private void updateStatus(){

        if( isNetworkAvailable()) {

            status = String.valueOf(getResources().getText(R.string.sign_in_request_connected));
        }
        else {
            status=String.valueOf(getResources().getText(R.string.sign_in_request_not_connected));
        }
    }
}
