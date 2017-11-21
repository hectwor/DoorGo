package com.example.hecto.doorgo.Entity;

/**
 * Created by hecto on 26/09/2017.
 */

public class User {

    private Id _id;
    private String user;
    private String pass;
    private String imageProfile;
    private String nameuser1,Nameuser2, nameuser3,nameuser4, nameuser5, nameuser6;
    private String photo1, photo2, photo3, photo4, photo5, photo6;


    public Id get_id() {
        return _id;
    }

    public String getUsername() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNameuser1() {
        return nameuser1;
    }

    public void setNameuser1(String nameuser1) {
        this.nameuser1 = nameuser1;
    }

    public String getNameuser2() {
        return Nameuser2;
    }

    public void setNameuser2(String nameuser2) {
        Nameuser2 = nameuser2;
    }

    public String getNameuser3() {
        return nameuser3;
    }

    public void setNameuser3(String nameuser3) {
        this.nameuser3 = nameuser3;
    }

    public String getNameuser4() {
        return nameuser4;
    }

    public void setNameuser4(String nameuser4) {
        this.nameuser4 = nameuser4;
    }

    public String getNameuser5() {
        return nameuser5;
    }

    public void setNameuser5(String nameuser5) {
        this.nameuser5 = nameuser5;
    }

    public String getNameuser6() {
        return nameuser6;
    }

    public void setNameuser6(String nameuser6) {
        this.nameuser6 = nameuser6;
    }

    public String getphoto1() {
        return photo1;
    }

    public void setphoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getphoto2() {
        return photo2;
    }

    public void setphoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getphoto3() {
        return photo3;
    }

    public void setphoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getphoto4() {
        return photo4;
    }

    public void setphoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getphoto5() {
        return photo5;
    }

    public void setphoto5(String photo5) {
        this.photo5 = photo5;
    }

    public String getphoto6() {
        return photo6;
    }

    public void setphoto6(String photo6) {
        this.photo6 = photo6;
    }
}
