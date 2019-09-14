package com.example.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl.ITestCallback;
import com.example.aidl.ITestInterface;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String TAG = "Client_MainActivity";
    private ITestCallback mITestCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding remote service using explicit action and package name
        Intent intent = new Intent("com.example.server.TestService");
        intent.setPackage("com.example.server");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    // ServiceConnection to check service connection established or not
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            mITestCallback = ITestCallback.Stub.asInterface(iBinder);
            try {
                mITestCallback.register(new ITestInterface.Stub() {
                    @Override
                    public void testOne(boolean flag) throws RemoteException {
                        Log.d(TAG, "testOne: flag = " + flag);
                    }

                    @Override
                    public void testTwo(int a) throws RemoteException {
                        Log.d(TAG, "testTwo: a = " + a);
                    }

                    @Override
                    public void testThree(String time) throws RemoteException {
                        Log.d(TAG, "testThree: time = " + time);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mITestCallback = null;
        }
    };

}
