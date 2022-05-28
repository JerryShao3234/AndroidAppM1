package com.example.m1;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Server extends AppCompatActivity {

    TextView serverText;
    private GoogleSignInClient mGoogleSignIn;
    private int RC_SIGN_IN = 1;
    private String clientIP = ""; //get via Android API...done
    private String serverIP = ""; //get via REST api..done
    private String servertime = ""; //get via REST api...done
    private String clienttime = ""; //get via Android API...done
    private String backendName = ""; //get via REST api...store this in database
    private String googleName = ""; //get with Google...done

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        serverText = findViewById(R.id.server_info);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        clienttime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        mGoogleSignIn = GoogleSignIn.getClient(this, gso);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        signIn(); //remember, no button necessary!
//        while(googleName.equals("")) {}
        serverText.setText(googleName);
        Log.d("Server","asdasdasdasdasdsadasdasdsadasdagoog");
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignIn.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account == null) {
            Log.d("Server", "There is no signed in account!");
        }
        else {
            Log.d("Server","Signed In, now you can get info");
            googleName = account.getDisplayName();
            //serverText.setText(googleName);
            //print googleName somewhere
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Server", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void testGet() {
        RequestQueue queue = Volley.newRequestQueue(Server.this);
        String url = "http://20.24.196.152:8081/";
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            public void onResponse(String response) {
                serverText.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serverText.setText("Failed to retrieve info :(");
                Log.d("Server error",error.toString());
            }
        } );
        queue.add(req);
    }

    private void testPut() {
        RequestQueue queue = Volley.newRequestQueue(Server.this);
        String url = "http://20.24.196.152:8081/todolist";
        JSONObject item = new JSONObject();
        try {
            item.put("task", "Finish this tutorial");
            item.put("status", "bruh1");
        }
        catch (Exception e) {
            Log.d("Server error", "JSON object failure");
        }

        //Slight issue: can't see String response since this only listens for JSON
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, item, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                serverText.setText("Put: "+response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Server Error", error.toString());
            }
        });


        queue.add(jsonObjectRequest);


    }

    private void getServerIP() {
        RequestQueue queue = Volley.newRequestQueue(Server.this);
        String url = "http://20.24.196.152:8081/getServerIP";
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            public void onResponse(String response) {
                serverText.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serverText.setText("Failed to retrieve info :(");
                Log.d("Server error",error.toString());
            }
        } );
        queue.add(req);
    }

    private void getServerTime() {
        RequestQueue queue = Volley.newRequestQueue(Server.this);
        String url = "http://20.24.196.152:8081/getServerTime";
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            public void onResponse(String response) {
                serverText.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serverText.setText("Failed to retrieve info :(");
                Log.d("Server error",error.toString());
            }
        } );
        queue.add(req);
    }
}

