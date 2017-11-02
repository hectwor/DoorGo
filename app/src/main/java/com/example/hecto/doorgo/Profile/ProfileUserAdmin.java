package com.example.hecto.doorgo.Profile;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hecto.doorgo.Class.User;
import com.example.hecto.doorgo.Common;
import com.example.hecto.doorgo.HTTPDataHandler;
import com.example.hecto.doorgo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileUserAdmin extends AppCompatActivity {

    private TextView mTextMessage;
    List<User> users = new ArrayList<User>();
    User usuarioLogeado;
    TextView username;
    String nombreUsuario;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_admin);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        username = (TextView) findViewById(R.id.nameUser);
        nombreUsuario = (String)getIntent().getExtras().getString("usuario");

        new GetData().execute(Common.getAddressAPI());

    }

    //funcion progreso de datos
    class GetData extends AsyncTask<String,Void,String> {
        ProgressDialog pd = new ProgressDialog(ProfileUserAdmin.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Pre Proceso
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Proceso ejecutandose
            String stream= null;
            String urlString = params[0];

            HTTPDataHandler http = new HTTPDataHandler();
            stream = http.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            //Acabar proceso

            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>(){}.getType();
            users=gson.fromJson(s,listType);
            int encontroUsuario=0;
            for (int i=0;i<users.size();i++){
                if (users.get(i).getUsername().equals(nombreUsuario)){
                    encontroUsuario++;
                    usuarioLogeado=users.get(i);
                }
            }
            if(encontroUsuario>0){
                    username.setText(usuarioLogeado.getUsername());
            }
            pd.dismiss();
        }
    }

    //funcion para editar usuarios
    class PutData extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(ProfileUserAdmin.this);

        String userName;

        public PutData(String userName) {
            this.userName = userName;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Espere...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            String json="{\"user\":\""+userName+"\"}";
            hh.PutHTTPData(urlString,json);
            return "";
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);
            pd.dismiss();
        }
    }

}
