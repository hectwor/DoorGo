package com.example.hecto.doorgo.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.example.hecto.doorgo.Entity.User;
import com.example.hecto.doorgo.Model.MongodbClienteDAO;
import com.example.hecto.doorgo.Model.MongodbDAOFactoria;
import com.example.hecto.doorgo.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class UsersAdminFragment extends Fragment {
    private Button user1, user2, user3, user4, user5, user6;
    private User usuarioLogeado;
    private AlertDialog.Builder build1, build2, build3, build4, build5, build6;
    private EditText nameuser;
    private String m_Text = "";
    private String encoded;
    private int seleccion;
    public UsersAdminFragment() {

    }
    public void setObject(User user, AlertDialog.Builder b1, AlertDialog.Builder b2
            , AlertDialog.Builder b3, AlertDialog.Builder b4
            , AlertDialog.Builder b5, AlertDialog.Builder b6){
        usuarioLogeado = user;
        build1=b1;
        build2=b2;
        build3=b3;
        build4=b4;
        build5=b5;
        build6=b6;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_admin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        user1 = (Button)view.findViewById(R.id.btnUser1);
        user2 = (Button)view.findViewById(R.id.btnUser2);
        user3 = (Button)view.findViewById(R.id.btnUser3);
        user4 = (Button)view.findViewById(R.id.btnUser4);
        user5 = (Button)view.findViewById(R.id.btnUser5);
        user6 = (Button)view.findViewById(R.id.btnUser6);


        user2.setText(usuarioLogeado.getNameuser2());
        user3.setText(usuarioLogeado.getNameuser3());
        user4.setText(usuarioLogeado.getNameuser4());
        user5.setText(usuarioLogeado.getNameuser5());
        user6.setText(usuarioLogeado.getNameuser6());

        if (usuarioLogeado.getphoto1()!="null" && usuarioLogeado.getNameuser1()!="null"){
            System.out.println("111111111111111111111");
            user1.setText(usuarioLogeado.getNameuser1());
            byte[] decodedString = Base64.decode(usuarioLogeado.getphoto1(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            user1.setBackground(new BitmapDrawable(getResources(), decodedByte));
        }
        if (usuarioLogeado.getphoto2()!="null" && usuarioLogeado.getNameuser2()!="null"){
            System.out.println("222222222222");
            user2.setText(usuarioLogeado.getNameuser2());
            byte[] decodedString1 = Base64.decode(usuarioLogeado.getphoto2(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
            user2.setBackground(new BitmapDrawable(getResources(), decodedByte));
        }
        if (usuarioLogeado.getphoto3()!="null" && usuarioLogeado.getNameuser3()!="null"){
            System.out.println("333333333333333");
            user3.setText(usuarioLogeado.getNameuser3());
            byte[] decodedString2 = Base64.decode(usuarioLogeado.getphoto3(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            user3.setBackground(new BitmapDrawable(getResources(), decodedByte));
        }
        if (usuarioLogeado.getphoto4()!="null" && usuarioLogeado.getNameuser4()!="null"){
            System.out.println("44444444444444444");
            user4.setText(usuarioLogeado.getNameuser4());
            byte[] decodedString3 = Base64.decode(usuarioLogeado.getphoto4(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString3, 0, decodedString3.length);
            user4.setBackground(new BitmapDrawable(getResources(), decodedByte));
        }
        if (usuarioLogeado.getphoto5()!="null" && usuarioLogeado.getNameuser5()!="null"){
            System.out.println("555555555555555555555");
            user5.setText(usuarioLogeado.getNameuser5());
            byte[] decodedString4 = Base64.decode(usuarioLogeado.getphoto6(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString4, 0, decodedString4.length);
            user5.setBackground(new BitmapDrawable(getResources(), decodedByte));
        }
        if (usuarioLogeado.getphoto6()!="null" && usuarioLogeado.getNameuser6()!="null"){
            System.out.println("666666666666666666");
            user6.setText(usuarioLogeado.getNameuser6());
            byte[] decodedString5 = Base64.decode(usuarioLogeado.getphoto6(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString5, 0, decodedString5.length);
            user6.setBackground(new BitmapDrawable(getResources(), decodedByte));
        }

        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnSelect(1, build1);
            }
        });
        user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelect(2, build2);
            }
        });
        user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelect(3, build3);
            }
        });
        user4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelect(4, build4);
            }
        });
        user5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelect(5, build5);
            }
        });
        user6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelect(6, build6);
            }
        });
    }
    private void btnSelect(final Integer select, AlertDialog.Builder build){
        nameuser = new EditText(getActivity());
        nameuser.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        build.setTitle("Ingrese Usuario "+select);
        build.setView(nameuser);
        build.setPositiveButton("Añadir/editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = nameuser.getText().toString();

                switch (select){
                    case 1:
                        usuarioLogeado.setNameuser1(m_Text);
                        user1.setText(m_Text);
                        break;
                    case 2:
                        usuarioLogeado.setNameuser2(m_Text);
                        user2.setText(m_Text);
                        break;
                    case 3:
                        usuarioLogeado.setNameuser3(m_Text);
                        user3.setText(m_Text);
                        break;
                    case 4:
                        usuarioLogeado.setNameuser4(m_Text);
                        user4.setText(m_Text);
                        break;
                    case 5:
                        usuarioLogeado.setNameuser5(m_Text);
                        user5.setText(m_Text);
                        break;
                    case 6:
                        usuarioLogeado.setNameuser6(m_Text);
                        user6.setText(m_Text);
                        break;
                }
                new UsersAdminDAO(usuarioLogeado.getUsername()).execute(MongodbDAOFactoria.getAddressSingle(usuarioLogeado));
            }
        });
        build.setNeutralButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        build.setNegativeButton("Tomar Foto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.cancel();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //Lanzamos la aplicacion de la camara con retorno (forResult)*/
                startActivityForResult(cameraIntent, 1);
                seleccion=select;
            }
        });
        AlertDialog dialog = build.create();
        dialog.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            System.out.println("11111111");
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Drawable d = new BitmapDrawable(getResources(), photo);

            user1.setBackground(d);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            switch (seleccion){
                case 1:
                    usuarioLogeado.setphoto1(encoded);
                    break;
                case 2:
                    usuarioLogeado.setphoto2(encoded);
                    break;
                case 3:
                    usuarioLogeado.setphoto3(encoded);
                    break;
                case 4:
                    usuarioLogeado.setphoto4(encoded);
                    break;
                case 5:
                    usuarioLogeado.setphoto5(encoded);
                    break;
                case 6:
                    usuarioLogeado.setphoto6(encoded);
                    break;
            }
        }
    }
    class UsersAdminDAO extends AsyncTask<String, String, String> {
        String userName;

        public UsersAdminDAO(String userName) {
            this.userName = userName;
        }
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            MongodbClienteDAO hh = new MongodbClienteDAO();
            /*String json="{\"user\":\""+usuarioLogeado.getUsername()+
                    "\",\"pass\":\""+usuarioLogeado.getPass()+
                    "\",\"imageProfile\":\""+usuarioLogeado.getImageProfile()+
                    "\",\"users\":[ {\"nameuser1\":\""+usuarioLogeado.getNameuser1()+
                    "\",\"photo1\":\""+usuarioLogeado.getphoto1()+
                    "\"},{\"nameuser2\":\""+usuarioLogeado.getNameuser2()+
                    "\",\"photo2\":\""+usuarioLogeado.getphoto2()+
                    "\"},{\"nameuser3\":\""+usuarioLogeado.getNameuser3()+
                    "\",\"photo3\":\""+usuarioLogeado.getphoto3()+
                    "\"},{\"nameuser4\":\""+usuarioLogeado.getNameuser4()+
                    "\",\"photo4\":\""+usuarioLogeado.getphoto4()+
                    "\"},{\"nameuser5\":\""+usuarioLogeado.getNameuser5()+
                    "\",\"photo5\":\""+usuarioLogeado.getphoto5()+
                    "\"},{\"nameuser6\":\""+usuarioLogeado.getNameuser6()+
                    "\",\"photo6\":\""+usuarioLogeado.getphoto6()+"\"}]}";*/

            String json="{\"user\":\""+usuarioLogeado.getUsername()+
                    "\",\"pass\":\""+usuarioLogeado.getPass()+
                    "\",\"imageProfile\":\""+usuarioLogeado.getImageProfile()+
                    "\",\"nameuser1\":\""+usuarioLogeado.getNameuser1()+
                    "\",\"photo1\":\""+usuarioLogeado.getphoto1()+
                    "\",\"nameuser2\":\""+usuarioLogeado.getNameuser2()+
                    "\",\"photo2\":\""+usuarioLogeado.getphoto2()+
                    "\",\"nameuser3\":\""+usuarioLogeado.getNameuser3()+
                    "\",\"photo3\":\""+usuarioLogeado.getphoto3()+
                    "\",\"nameuser4\":\""+usuarioLogeado.getNameuser4()+
                    "\",\"photo4\":\""+usuarioLogeado.getphoto4()+
                    "\",\"nameuser5\":\""+usuarioLogeado.getNameuser5()+
                    "\",\"photo5\":\""+usuarioLogeado.getphoto5()+
                    "\",\"nameuser6\":\""+usuarioLogeado.getNameuser6()+
                    "\",\"photo6\":\""+usuarioLogeado.getphoto6()+"\"}";
            hh.PutHTTPData(urlString, json);
            return "";
        }
    }
}
