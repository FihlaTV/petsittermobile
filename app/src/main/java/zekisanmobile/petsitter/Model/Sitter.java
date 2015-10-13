package zekisanmobile.petsitter.Model;

import java.io.Serializable;

/**
 * Created by ezequiel on 20/09/15.
 */
@SuppressWarnings("serial")
public class Sitter implements Serializable{

    private String name, address;
    private int photo, profile_background;

    public Sitter(){}

    public Sitter(String name, String address, int photo, int profile_background){
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.profile_background = profile_background;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getProfile_background() {
        return profile_background;
    }

    public void setProfile_background(int profile_background) {
        this.profile_background = profile_background;
    }
}
