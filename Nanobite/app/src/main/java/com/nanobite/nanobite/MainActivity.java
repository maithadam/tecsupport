package com.nanobite.nanobite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Internet_Connections intrconnect=new Internet_Connections();
         switch (item.getItemId()) {

                case R.id.navigation_home:
                    transaction.replace(R.id.content,new NanoVisionFragment());
                    transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();
                    return true;

                case R.id.navigation_dashboard:
                    boolean chkconnect=intrconnect.checkConnection(getApplicationContext());
                    if (chkconnect) {
                        transaction.replace(R.id.content, new NanoFragment());
                        transaction.addToBackStack(null);
                        // Commit the transaction
                        transaction.commit();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                    return true;

                case R.id.navigation_notifications:
                    transaction.replace(R.id.content, new NanoSupportFragment());
                    transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Nanobite Technologies LLC");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, new NanoVisionFragment()).commit();
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }




}
