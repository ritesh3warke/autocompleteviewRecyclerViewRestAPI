package com.riteshwarke.dawaibox.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.riteshwarke.dawaibox.Fragments.LoginFragment;
import com.riteshwarke.dawaibox.Fragments.SearchFragment;
import com.riteshwarke.dawaibox.Fragments.SplashFragment;
import com.riteshwarke.dawaibox.Helpers.AppConstants;
import com.riteshwarke.dawaibox.Helpers.General;
import com.riteshwarke.dawaibox.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.brightinventions.slf4android.FileLogHandlerConfiguration;
import pl.brightinventions.slf4android.LoggerConfiguration;
import pl.brightinventions.slf4android.NotifyDeveloperHandler;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 20;
    private static final Logger LOG = LoggerFactory.getLogger(MainActivity.class.getSimpleName());
    @BindView(R.id.my_container)
    FrameLayout my_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            *//*requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);*//*
        }*/
        if(General.getSharedPreferences(this, AppConstants.IS_LOGGED_IN_USER).equalsIgnoreCase("")){
            General.setSharedPreferences(this,AppConstants.USERNAME,"Ritesh");
            General.setSharedPreferences(this,AppConstants.PASSWORD,"123");
        }
        init();
        getRunTimePermissions();
    }

    private void getRunTimePermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE},
                                        REQUEST_PERMISSIONS);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            setLogger();
        }
    }

    private void setLogger() {
        FileLogHandlerConfiguration fileHandler = LoggerConfiguration.fileLogHandler(this);
        fileHandler.setFullFilePathPattern(AppConstants.LOG_PATH);
        LoggerConfiguration.configuration().addHandlerToRootLogger(fileHandler);
        NotifyDeveloperHandler handler = LoggerConfiguration.configuration().notifyDeveloperHandler(getApplication(), "ritesh3warke@gmail.com");
        handler.notifyWhenDeviceIsShaken();
        LoggerConfiguration.configuration().addHandlerToRootLogger(handler);
    }

    private void init() {
        if(General.getSharedPreferences(this, AppConstants.IS_LOGGED_IN_USER).equalsIgnoreCase("")){
            General.setSharedPreferences(this,AppConstants.USERNAME,"Ritesh");
            General.setSharedPreferences(this,AppConstants.PASSWORD,"123");
            LoginFragment loginFragment = new LoginFragment();
            loadFragment(loginFragment, null, R.id.my_container, "null");
        }else{
            SplashFragment splashFragment = new SplashFragment();
            loadFragment(splashFragment, null, R.id.my_container, "null");
        }

    }
    public void loadFragment(Fragment fragment, Bundle args, int containerId, String title)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(title);
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }
    @Override
    public void onBackPressed()
    {
        if(AppConstants.ISSEARCHING){
            ((SearchFragment) getSupportFragmentManager().findFragmentById(R.id.my_container)).backToInventory();
        }
        else
        this.finishAffinity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if ((grantResults.length > 0) && (grantResults[0]) == PackageManager.PERMISSION_GRANTED) {
                    setLogger();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_LONG).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                }
                            }).show();
                }
                return;
            }
        }
    }
}
