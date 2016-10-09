package me.cheenar.backendconnection;

/**
 * Created by cheen on 10/8/2016.
 */

public class User
{

    public String name;
    public String profileID;
    public String profileImage;

    public User(){ }

    public User(String name, String profileID, String profileImage) {
        this.name = name;
        this.profileID = profileID;
        this.profileImage = profileImage;
    }

}
