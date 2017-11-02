package com.example.hecto.doorgo.Profile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hecto.doorgo.R;

/**
 * Created by hector on 01/11/17.
 */

public class AdminUsuarios extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_admin_users, container, false);

        return rootView;
    }

}
