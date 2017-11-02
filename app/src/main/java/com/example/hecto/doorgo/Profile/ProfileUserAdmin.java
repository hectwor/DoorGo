package com.example.hecto.doorgo.Profile;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileUserAdmin extends AppCompatActivity {

    private TextView mTextMessage;
    List<User> users = new ArrayList<>();
    User usuarioLogeado;
    TextView username;
    String nombreUsuario;
    Button btnChangeImage, btnCambiarNombre;
    String encoded;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String m_Text = "";
    AlertDialog.Builder builder;
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
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_admin);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        new levantarInfoUsuario().execute(Common.getAddressAPI());
        username = (TextView) findViewById(R.id.nameUser);
        nombreUsuario = (String) getIntent().getExtras().getString("usuario");
        img = (ImageView)this.findViewById(R.id.imagenUser);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        btnCambiarNombre = (Button) findViewById(R.id.btnCambiarNombre);
        btnChangeImage = (Button) findViewById(R.id.btnCambiarImagen);

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                //Lanzamos la aplicacion de la camara con retorno (forResult)*/
                startActivityForResult(cameraIntent, 1);
            }
        });


        // Set up the input
        final EditText input = new EditText(this);
        builder = new AlertDialog.Builder(this);


        btnCambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Ingrese Nuevo Nombre");
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        new PutData(m_Text).execute(Common.getAddressSingle(usuarioLogeado));
                        username.setText(m_Text);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            new PutData(usuarioLogeado.getUsername()).execute(Common.getAddressSingle(usuarioLogeado));
        }
    }
    //funcion progreso de datos
    class levantarInfoUsuario extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(ProfileUserAdmin.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Pre Proceso
            pd.show();
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
            int encontroUsuario = 0;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(nombreUsuario)) {
                    encontroUsuario++;
                    usuarioLogeado = users.get(i);
                }
            }
            if (encontroUsuario > 0) {
                username.setText(usuarioLogeado.getUsername());
                System.out.println(usuarioLogeado.getImageProfile());
                if (usuarioLogeado.getImageProfile()!=null){
                    byte[] decodedString = Base64.decode(usuarioLogeado.getImageProfile(), Base64.DEFAULT);

                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    img.setImageBitmap(decodedByte);
                }
            }
            pd.dismiss();
        }
    }

    //funcion para editar usuarios
    class PutData extends AsyncTask<String, String, String> {
        ProgressDialog pd = new ProgressDialog(ProfileUserAdmin.this);

        String userName;

        public PutData(String userName) {
            this.userName = userName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Espere...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            String json="{\"user\":\"" + userName + "\"," +
                    "\"pass\":\"" + usuarioLogeado.getPass() + "\"," +
                    "\"imageProfile\":\"" + encoded+"\"}";

            // {\"user\":\"userName\",\"pass\":\"pass\",\"imageProfile\":\"photo\"}
            hh.PutHTTPData(urlString, json);
            return "";
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Perfil p1 = new Perfil();
                    System.out.println("111111111111111111111111");
                    return p1;
                case 1:
                    AdminUsuarios p2 = new AdminUsuarios();
                    System.out.println("22222222222222222222222222");
                    return p2;
                case 2:
                    Notificaciones p3 = new Notificaciones();
                    System.out.println("3333333333333333333333333333");
                    return p3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}