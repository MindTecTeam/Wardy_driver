package com.invictos.ssid.wardy_driver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private EditText mUserView;
    private EditText mPasswordView;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private Button mLogin;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.i(TAG,"M"+intent.getExtras().get("latitude")+" "+intent.getExtras().get("longitude"));
                    //textview.append("\n" +intent.getExtras().get("coordinates"));
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserView = (EditText) findViewById(R.id.editText_user);
        mPasswordView = (EditText) findViewById(R.id.editText_password);
        mLogin = (Button) findViewById(R.id.button_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String request = "{\"id\": \""+ mUserView.getText().toString()+"\", \"password\": \""+ mPasswordView.getText().toString()+"\"}";
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, request);
                Request req = new Request.Builder()
                        .url("https://w5nikh46ic.execute-api.sa-east-1.amazonaws.com/dev/login")
                        .post(body)
                        .build();
                client.newCall(req).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String respuesta = response.body().string();
                        if (respuesta.equals("{\"message\":\"Permitido\"}")  || respuesta.equals("{\"message\":\"Usuario Logeado\"}") ){
                            Log.i(TAG,"logeado");
                            startRequest();
                            goPrincipalView();
                        }
                        else {
                            Log.i(TAG,respuesta);
                        }
                    }
                });
            }
        });
    }

    private void startRequest() {
        Intent i = new Intent(getApplicationContext(), Request_Service.class);
        startService(i);
    }

    private void goPrincipalView() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
    }
}
