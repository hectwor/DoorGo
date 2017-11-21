package com.example.hecto.doorgo.Model;

import com.example.hecto.doorgo.Entity.User;

/**
 * Created by hecto on 26/09/2017.
 */

public class MongodbDAOFactoria {
    private static String DB_NAME = "mydb";
    private static String COLLECTION_NAME = "user";
    public static String API_KEY="iJi2WSW1jBLsPEeimRjEOBubrNKu4Tyl";

    public static String getAddressSingle(User user){
        String baseURL = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s", DB_NAME, COLLECTION_NAME);
        StringBuilder stringbuilder = new StringBuilder(baseURL);
        stringbuilder.append("/"+user.get_id().getOld()+"?apiKey="+API_KEY);
        return stringbuilder.toString();
    }

    public static String getAddressAPI(){
        String baseURL = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s", DB_NAME, COLLECTION_NAME);
        StringBuilder stringbuilder = new StringBuilder(baseURL);
        stringbuilder.append("?apiKey="+API_KEY);
        return stringbuilder.toString();
    }

}
