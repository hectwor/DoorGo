package com.example.hecto.doorgo.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hecto.doorgo.Class.User;
import com.example.hecto.doorgo.Common;
import com.example.hecto.doorgo.HTTPDataHandler;
import com.example.hecto.doorgo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileUserAdmin extends AppCompatActivity {

    List<User> users = new ArrayList<>();
    private User usuarioLogeado;
    private String nombreUsuario;
    protected String encoded;
    private AlertDialog.Builder builder;
    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_admin);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        input = new EditText(this);
        builder = new AlertDialog.Builder(this);

        new levantarInfoUsuario().execute(Common.getAddressAPI());
        nombreUsuario = (String) getIntent().getExtras().getString("usuario");

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =   new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    levantarfragmentProfile();
                    return true;
                case R.id.navigation_dashboard:
                    levantarfragmentUserAdmin();
                    return true;
                case R.id.navigation_notifications:
                    levantarfragmentNotifications();
                    return true;
            }
            return false;
        }
    };

    //funcion progreso de datos
    class levantarInfoUsuario extends AsyncTask<String, Void, String> {
        //ProgressDialog pd = new ProgressDialog(ProfileUserAdmin.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Pre Proceso
            //pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Proceso ejecutandose
            String stream = null;
            String urlString = params[0];

            HTTPDataHandler http = new HTTPDataHandler();
            stream = http.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Acabar proceso

            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            users = gson.fromJson(s, listType);
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(nombreUsuario)){
                    usuarioLogeado = users.get(i);
                    levantarfragmentProfile();
                }

            }
            //pd.dismiss();
        }
    }

    private void levantarfragmentProfile(){
        Perfil perfil= new Perfil();
        perfil.setObject(usuarioLogeado, builder, input);
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.contentLayout, perfil, perfil.getTag()).commit();
    }
    private void levantarfragmentUserAdmin(){
        UsersAdminFragment useradmi= new UsersAdminFragment();
        android.app.FragmentManager manager1 = getFragmentManager();
        manager1.beginTransaction().replace(R.id.contentLayout, useradmi, useradmi.getTag()).commit();
    }
    private void levantarfragmentNotifications(){
        Notificaciones notificaciones= new Notificaciones();
        android.app.FragmentManager manager2 = getFragmentManager();
        manager2.beginTransaction().replace(R.id.contentLayout,
                notificaciones, notificaciones.getTag()).commit();
    }
}