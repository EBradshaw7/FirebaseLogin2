package com.example.eoghan.firebaselogin2;

/**
 * Created by Eoghan on 20/03/2017.
 */

public class UserInformation {
    private String name;
    private String address;

    public UserInformation(){

    }

    public UserInformation(String name, String address){

        this.name = name;
        this.address = address;
    }


    public String getName() {
        return name;
    }



    public String getAddress() {
        return address;
    }


}
