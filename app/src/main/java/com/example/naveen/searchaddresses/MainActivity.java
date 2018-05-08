package com.example.naveen.searchaddresses;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.naveen.searchaddresses.adapters.CustomAdapter;
import com.example.naveen.searchaddresses.core.DatabaseHelper;
import com.example.naveen.searchaddresses.core.LocationsDBAdapter;
import com.example.naveen.searchaddresses.core.SelectedLocation;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<SelectedLocation> selectedLocations;

    ListView listView;
    private static CustomAdapter adapter;
    public static DatabaseHelper dbHelper;
    public static LocationsDBAdapter locationDbAdapter;
    Snackbar snackbar;
    TextView snackbarTextView;
    public static boolean isInternet = false;
    public static String net_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listOfLocations);
        CoordinatorLayout mainLayout = (CoordinatorLayout) findViewById(R.id.mainLayout);
        snackbar = Snackbar.make(mainLayout
                ,
                "Added Location: ",
                Snackbar.LENGTH_LONG);
        final View snackbarView = snackbar.getView();
        snackbarTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        snackbarView.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryDark, null));

        Button addLocation = (Button) findViewById(R.id.addLocation);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable(MainActivity.this)) {
                    Intent locationIntent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(locationIntent);
                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Internet Connection Needed")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Prompt the user once explanation has been shown
                                }
                            })
                            .create()
                            .show();
                }
            }
        });
    }

    private boolean isNetworkAvailable(Context context) {
        if(context == null){
            return false;
        }
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                net_type = "WIFI";
                isInternet = true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                net_type = "MOBILE";
                isInternet = true;
            }
            return isInternet;

        } else {
            isInternet = false;
            return false;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(dbHelper == null){
            dbHelper = DatabaseHelper.getInstance(this);
        }
        if(locationDbAdapter == null){
            locationDbAdapter = new LocationsDBAdapter(this);
            locationDbAdapter = locationDbAdapter.open(dbHelper, dbHelper.db);
        }
        Log.e("saved locations", "=========" + locationDbAdapter.getAllLocations());
        selectedLocations = locationDbAdapter.getAllLocations();
        if(selectedLocations != null && locationDbAdapter.getAllLocations().size() > 0){

            adapter= new CustomAdapter(selectedLocations,getApplicationContext());

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SelectedLocation dataModel= selectedLocations.get(position);
                    snackbar.setDuration(3000);
                    snackbarTextView.setText(dataModel.getTitle()+"\nPick up:"+dataModel.getId()+"\nLatitude: "+dataModel.getLatitude()+"\nLongitude: "+dataModel.getLongitude());
                    snackbarTextView.setMaxLines(5);
                    snackbar.show();
                }
            });
        }
    }
}
