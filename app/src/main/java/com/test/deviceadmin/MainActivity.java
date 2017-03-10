package com.test.deviceadmin;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.test.deviceadmin.Receivers.DeviceAdminSampleReceiver;


public class MainActivity extends Activity {

    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    private static final String TAG = "DeviceAdmin";
    DevicePolicyManager mDPM;
    ComponentName mDeviceAdmin;
    Button btnDeviceAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdmin = new ComponentName(this, DeviceAdminSampleReceiver.class);

        btnDeviceAdmin = (Button) findViewById(R.id.btnDeviceAdmin);
        btnDeviceAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! mDPM.isAdminActive(mDeviceAdmin) ) { // Without permission
                    Log.i(TAG, "App not yet active a Device Admin");
                    getDeviceAdmin();
                }else{
                    Log.i(TAG, "App is already active a Device Admin");
                }
            }
        });
    }

    private void getDeviceAdmin(){
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, R.string.device_admin_explain);
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ENABLE_ADMIN:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "Device Admin enabled!");
                } else {
                    Log.i(TAG, "Device Admin enabling FAILED!");
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
