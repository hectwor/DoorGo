package com.example.hecto.doorgo.Controller;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hecto.doorgo.Entity.User;
import com.example.hecto.doorgo.Model.MongodbDAOFactoria;
import com.example.hecto.doorgo.Model.MongodbClienteDAO;
import com.example.hecto.doorgo.R;

import java.io.ByteArrayOutputStream;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private Button btnChangeImage, btnCambiarNombre;
    private TextView username;
    private ImageView img;
    private AlertDialog.Builder builder;
    private String m_Text = "";
    private User usuarioLogeado;
    private String encoded;
    private EditText inputnewname;
    public PerfilFragment() {
        // Required empty public constructor
    }
    public void setObject(User user, AlertDialog.Builder build){
        usuarioLogeado = user;
        builder=build;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        username = (TextView) view.findViewById(R.id.nameUser);
        img = (ImageView) view.findViewById(R.id.imagenUser);

        btnCambiarNombre = (Button) view.findViewById(R.id.btnCambiarNombre);
        btnChangeImage = (Button) view.findViewById(R.id.btnCambiarImagen);

        username.setText(usuarioLogeado.getUsername());
        if (usuarioLogeado.getImageProfile()!=null){
            byte[] decodedString = Base64.decode(usuarioLogeado.getImageProfile(), Base64.DEFAULT);

            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedByte);
        }

        btnCambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputnewname = new EditText(getActivity());
                inputnewname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                builder.setTitle("Ingrese Nuevo Nombre");
                builder.setView(inputnewname);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = inputnewname.getText().toString();
                        usuarioLogeado.setUser(m_Text);
                        new PerfilDAO(usuarioLogeado.getUsername()).execute(MongodbDAOFactoria.getAddressSingle(usuarioLogeado));
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
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //Lanzamos la aplicacion de la camara con retorno (forResult)*/
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            usuarioLogeado.setImageProfile(encoded);
            new PerfilDAO(usuarioLogeado.getUsername()).execute(MongodbDAOFactoria.getAddressSingle(usuarioLogeado));
        }
    }

    //funcion para editar usuarios
    class PerfilDAO extends AsyncTask<String, String, String> {
        String userName;

        public PerfilDAO(String userName) {
            this.userName = userName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            MongodbClienteDAO hh = new MongodbClienteDAO();
            String json="{\"user\":\"" + usuarioLogeado.getUsername() + "\"," +
                    "\"pass\":\"" + usuarioLogeado.getPass() + "\"," +
                    "\"imageProfile\":\"" + usuarioLogeado.getImageProfile()+"\"}";

            // {\"user\":\"userName\",\"pass\":\"pass\",\"imageProfile\":\"photo\"}
            hh.PutHTTPData(urlString, json);
            return "";
        }
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
