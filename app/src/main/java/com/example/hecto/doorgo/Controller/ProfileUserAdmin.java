package com.example.hecto.doorgo.Controller;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.hecto.doorgo.Entity.User;
import com.example.hecto.doorgo.Model.MongodbDAOFactoria;
import com.example.hecto.doorgo.Model.MongodbClienteDAO;
import com.example.hecto.doorgo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ProfileUserAdmin extends AppCompatActivity {

    private List<User> users = new ArrayList<>();
    private User usuarioLogeado;
    private String nombreUsuario;
    protected String encoded;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder build1, build2, build3, build4, build5, build6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_admin);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        builder = new AlertDialog.Builder(this);
        build1 = new AlertDialog.Builder(this);
        build2 = new AlertDialog.Builder(this);
        build3 = new AlertDialog.Builder(this);
        build4 = new AlertDialog.Builder(this);
        build5 = new AlertDialog.Builder(this);
        build6 = new AlertDialog.Builder(this);

        new ProfileAdminDAO().execute(MongodbDAOFactoria.getAddressAPI());
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
    class ProfileAdminDAO extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //Proceso ejecutandose
            String stream = null;
            String urlString = params[0];

            MongodbClienteDAO http = new MongodbClienteDAO();
            stream = http.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
        }
    }

    private void levantarfragmentProfile(){
        PerfilFragment perfilFragment = new PerfilFragment();
        perfilFragment.setObject(usuarioLogeado, builder);
        android.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.contentLayout, perfilFragment, perfilFragment.getTag()).commit();
    }
    private void levantarfragmentUserAdmin(){
        UsersAdminFragment useradmi= new UsersAdminFragment();
        useradmi.setObject(usuarioLogeado,build1, build2, build3, build4, build5, build6);
        android.app.FragmentManager manager1 = getFragmentManager();
        manager1.beginTransaction().replace(R.id.contentLayout, useradmi, useradmi.getTag()).commit();
    }
    private void levantarfragmentNotifications(){
        NotificacionesFragment notificacionesFragment = new NotificacionesFragment();
        android.app.FragmentManager manager2 = getFragmentManager();
        manager2.beginTransaction().replace(R.id.contentLayout,
                notificacionesFragment, notificacionesFragment.getTag()).commit();
    }
}