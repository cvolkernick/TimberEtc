package com.example.cvolk.timberetc;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements PermissionManager.IPermissionManager, ZXingScannerView.ResultHandler {

    private static final int PERMISSIONS_REQUEST_CAMERA = 10;
    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private ZXingScannerView zXingScannerView;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionManager = new PermissionManager(this);
        permissionManager.checkPermission();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.d("Initialized");
    }

    public void scan(View view) {

        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        zXingScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onPermissionResult(boolean isGranted) {
        Log.d(TAG, "onPermissionResult: ");
    }

    public void runCanary(View view) {
        Log.d(TAG, "runCanary: ");
    }
}
