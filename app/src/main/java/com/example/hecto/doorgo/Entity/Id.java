package com.example.hecto.doorgo.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hecto on 26/09/2017.
 */

public class Id {
    @SerializedName("$oid")
    private String old;

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }
}
