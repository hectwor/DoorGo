package com.example.hecto.doorgo.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.hecto.doorgo.Class.User;
import com.example.hecto.doorgo.MainActivity;
import com.example.hecto.doorgo.R;

public class ProfileUserAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        User usuarioLogeado;

        String nombreUsuario = (String)getIntent().getExtras().getString("usuario");

        
    }

}
