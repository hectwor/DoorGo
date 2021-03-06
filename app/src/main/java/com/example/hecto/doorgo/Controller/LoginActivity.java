package com.example.hecto.doorgo.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hecto.doorgo.Model.MongodbDAOFactoria;
import com.example.hecto.doorgo.Entity.User;
import com.example.hecto.doorgo.Model.MongodbClienteDAO;
import com.example.hecto.doorgo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ListView lstView;
    private Button btnAdd, btnEdit, btnDelete, btnLogin;
    private EditText edtUser, edtPass;
    private User userSelected=null;
    private List<User> users = new ArrayList<User>();
    private final Context context = this;
    private User userLogeado=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        //btnEdit = (Button)findViewById(R.id.btnEdit);

        edtUser = (EditText)findViewById(R.id.edtUsername);
        edtPass = (EditText)findViewById(R.id.edtPass);

        //Evento de boton añadir
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(edtUser.getText().toString())){
                    edtUser.setError("Usuario en blanco");
                    edtUser.requestFocus();
                }else {
                    if ("".equals(edtPass.getText().toString())) {
                        edtPass.setError("Contraseña en blanco");
                        edtPass.requestFocus();
                    } else{
                        new GetData().execute(MongodbDAOFactoria.getAddressAPI());
                        int encontroUsuario=0;
                        for (int i=0;i<users.size();i++){
                            if (users.get(i).getUsername().equals(edtUser.getText().toString())){
                                encontroUsuario++;
                            }
                        }
                        if(encontroUsuario>0){
                            edtUser.setError("Usuario ya registrado");
                            edtUser.requestFocus();
                        }else{
                            if (isOnlineNet()){
                                new PostData(edtUser.getText().toString(), edtPass.getText().toString())
                                        .execute(MongodbDAOFactoria.getAddressAPI());
                            }else{
                                AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
                                builder3.setTitle("Fallo en Conexión a Red")
                                        .setMessage("No se encontró señal de internet")
                                        .setNeutralButton("Aceptar",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alert = builder3.create();
                                alert.show();
                            }
                        }

                    }
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnlineNet()){
                    if ("".equals(edtUser.getText().toString())){
                        edtUser.setError("Usuario en blanco");
                        edtUser.requestFocus();
                    }else{
                        if ("".equals(edtPass.getText().toString())){
                            edtPass.setError("Contraseña en blanco");
                            edtPass.requestFocus();
                        }else{
                            new GetData().execute(MongodbDAOFactoria.getAddressAPI());
                            new LoginUser(edtUser.getText().toString(), edtPass.getText().toString())
                                    .execute(MongodbDAOFactoria.getAddressAPI());
                        }
                    }
                }else{
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
                    builder3.setTitle("Fallo en Conexión a Red")
                            .setMessage("No se encontró señal de internet")
                            .setNeutralButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder3.create();
                    alert.show();
                }
            }
        });
    }
    //funcion progreso de datos
    class GetData extends AsyncTask<String,Void,String>{
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Pre Proceso
            pd.setTitle("Espere un momento...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Proceso ejecutandose
            String stream= null;
            String urlString = params[0];

            MongodbClienteDAO http = new MongodbClienteDAO();
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
            pd.dismiss();
        }
    }

    //funcion para añadir usuarios
    class PostData extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);

        String userName;
        String pass;

        public PostData(String userName, String pass) {
            this.userName = userName;
            this.pass = pass;
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
            MongodbClienteDAO hh = new MongodbClienteDAO();

            String json="{\"user\":\"" + userName + "\"," +
                    "\"pass\":\"" + pass + "\"}"; // {\"user\":\"userName\",\"pass\":\"pass\"}
            hh.PostHTTPData(urlString,json);

            return "";
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);

            AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
            builder3.setTitle("Usuario Registrado")
                    .setMessage("Username : "+edtUser.getText().toString()+
                        "\nPass : "+edtPass.getText().toString())
                    .setNeutralButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder3.create();
            alert.show();

            pd.dismiss();
        }
    }
    //Loggin de Cuenta
    class LoginUser extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        String userName, pass;

        public LoginUser(String userName, String pass) {
            this.userName = userName;
            this.pass = pass;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Comprobando...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            //Proceso ejecutandose
            String stream= null;
            String urlString = strings[0];

            MongodbClienteDAO http = new MongodbClienteDAO();
            stream = http.GetHTTPData(urlString);

            return stream;
        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>(){}.getType();
            users=gson.fromJson(s,listType);


            int encontroUsuario=0;
            for (int i=0;i<users.size();i++){
                if (users.get(i).getUsername().equals(edtUser.getText().toString())){
                    encontroUsuario++;
                    userLogeado=users.get(i);
                }
            }
            if(encontroUsuario>0){
                    if (userLogeado.getPass().equals(edtPass.getText().toString())){
                        saltoActivity();

                    }else {
                        edtPass.setError("Contraseña incorrecta");
                        edtPass.requestFocus();
                    }
            }else{
                edtUser.setError("Usuario No Encontrado");
                edtUser.requestFocus();
            }
            pd.dismiss();
        }
    }

    public void saltoActivity(){
        Intent intent = new Intent(this, ProfileUserAdmin.class);
        intent.putExtra("usuario", userLogeado.getUsername());
        startActivity(intent);
    }
    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}

