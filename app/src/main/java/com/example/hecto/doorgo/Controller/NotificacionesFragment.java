package com.example.hecto.doorgo.Controller;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hecto.doorgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificacionesFragment extends Fragment {


    public NotificacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notificaciones, container, false);
    }

}
