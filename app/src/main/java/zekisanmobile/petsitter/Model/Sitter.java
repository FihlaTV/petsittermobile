package zekisanmobile.petsitter.Model;

/**
 * Created by ezequiel on 20/09/15.
 */
public class Sitter {

    private String name, address;
    private int photo;

    public Sitter(){}

    public Sitter(String name, String address, int photo){
        this.name = name;
        this.address = address;
        this.photo = photo;
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
}
