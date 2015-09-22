package zekisanmobile.petsitter.Model;

/**
 * Created by ezequiel on 20/09/15.
 */
public class Sitter {

    private String name, address;

    public Sitter(String name, String address){
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
