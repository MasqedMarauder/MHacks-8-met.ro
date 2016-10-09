package me.cheenar.backendconnection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView id;

    public static User user;

    public MainActivity() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        username = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);
        login = (Button) findViewById(R.id.button);
        id = (TextView) findViewById(R.id.personID);

        requestPermissions(
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.INTERNET
                }, 0
        );

        //if(!hasPerm(Manifest.permission.INTERNET)) {
          //  requestPermissions(new String[] {Manifest.permission.INTERNET}, 42);
        //}

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasPerm(Manifest.permission.INTERNET))
                {
                    try {
                        String s = "http://66.175.213.218/p/get_profile_id?username=" + username.getText().toString()
                                + "&password=" + password.getText().toString();
                        JSONObject object = null;
                        try {
                            object = getJSONObjectFromURL(s);
                            System.out.println(object.get("PROFILE_ID"));
                            id.setText(object.get("PROFILE_ID").toString());

                            user = new User();
                            user.name = object.get("NAME").toString();
                            user.profileID = object.get("PROFILE_ID").toString();
                            user.profileImage = object.get("PROFILE_PICTURE").toString();

                            Intent intent = new Intent(getApplicationContext(), PortalActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You need to enable the internet permission!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean hasPerm(String perm)
    {
        return ActivityCompat.checkSelfPermission(getApplicationContext(), perm) == PackageManager.PERMISSION_GRANTED;
    }

    public static JSONArray getJSONArrayFromURL(String urlString) throws Exception
    {


        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONArray(jsonString);
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws Exception {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

}
