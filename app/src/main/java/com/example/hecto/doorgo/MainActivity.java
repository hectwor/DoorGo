package com.example.hecto.doorgo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hecto.doorgo.Class.User;
import com.example.hecto.doorgo.Profile.ProfileUserAdmin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.hecto.doorgo.Profile.ProfileUserAdmin;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    ListView lstView;
    Button btnAdd, btnEdit, btnDelete, btnLogin;
    EditText edtUser, edtPass;
    User userSelected=null;
    List<User> users = new ArrayList<User>();
    final Context context = this;
    User userLogeado=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstView = (ListView)findViewById(R.id.lstView);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        //btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        edtUser = (EditText)findViewById(R.id.edtUsername);
        edtPass = (EditText)findViewById(R.id.edtPass);


        //Levantar data cuando abre la app
        new GetData().execute(Common.getAddressAPI());

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userSelected = users.get(i);

                edtUser.setText(userSelected.getUsername());
                edtPass.setText(userSelected.getPass());
            }
        });
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
                        int encontroUsuario=0;
                        for (int i=0;i<users.size();i++){
                            if (users.get(i).getUsername().equals(edtUser.getText().toString())){
                                encontroUsuario++;
                            }
                        }
                        if(encontroUsuario>0){
                            edtUser.setError("Usuario ya registrado");
                            edtUser.requestFocus();
                        }else
                            new PostData(edtUser.getText().toString(), edtPass.getText().toString())
                                    .execute(Common.getAddressAPI());

                    }

                }
            }
        });
        //Evento de boton editar
        /*btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PutData(edtUser.getText().toString()).execute(Common.getAddressSingle(userSelected));
            }
        });*/

        //Evento de boton eliminar
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteData(userSelected).execute(Common.getAddressSingle(userSelected));
            }
        });
        //Evento de boton login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(edtUser.getText().toString())){
                    edtUser.setError("Usuario en blanco");
                    edtUser.requestFocus();
                }else{
                    if ("".equals(edtPass.getText().toString())){
                        edtPass.setError("Contraseña en blanco");
                        edtPass.requestFocus();
                    }else
                        new LoginUser(edtUser.getText().toString(), edtPass.getText().toString())
                                .execute(Common.getAddressAPI());
                }

            }
        });
    }
    //funcion progreso de datos
    class GetData extends AsyncTask<String,Void,String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

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

            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), users);

            lstView.setAdapter(adapter);
            pd.dismiss();
        }
    }

    //funcion para añadir usuarios
    class PostData extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

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
            HTTPDataHandler hh = new HTTPDataHandler();

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
            //Refresh data
            new GetData().execute(Common.getAddressAPI());
            pd.dismiss();
        }
    }

    //funcion para editar usuarios
    class PutData extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

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

            //Refresh data
            new GetData().execute(Common.getAddressAPI());
            pd.dismiss();
        }
    }

    //funcion para eliminar usuarios
    class DeleteData extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        User user;

        public DeleteData(User user) {
            this.user = user;
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
            String json="{\"user\":\""+user+"\"}";
            hh.DeleteHTTPData(urlString,json);
            return "";
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);
            //Refresh data
            new GetData().execute(Common.getAddressAPI());
            pd.dismiss();
        }
    }

    //Loggin de Cuenta
    class LoginUser extends AsyncTask<String, String, String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
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

            HTTPDataHandler http = new HTTPDataHandler();
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
                        /*AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
                        builder3.setTitle("Usuario Logeado")
                                .setNeutralButton("Aceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder3.create();
                        alert.show();*/
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
}

