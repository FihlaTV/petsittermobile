package zekisanmobile.petsitter.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "User")
public class User extends Model {

    @Column(name = "name")
    public String name;
    @Column(name = "email")
    public String email;
    @Column(name = "photo")
    public String photo;
    @Column(name = "logged")
    public boolean logged;
    @Column(name = "type")
    public int type; // 0 - Owner, 1 - PetSitter
    @Column(name = "owner")
    public Owner owner;
    @Column(name = "sitter")
    public Sitter sitter;

    public static void create(String name, String email, String photo, boolean logged, int type,
                              Owner owner, Sitter sitter){
        User user = new User();
        user.name = name;
        user.email = email;
        user.photo = photo;
        user.logged = logged;
        user.type = type;
        user.owner = owner;
        user.sitter = sitter;
        user.save();
    }

    public static List<User> all(){
        return new Select()
                .from(User.class)
                .execute();
    }

    public static User getLoggedUser(int userType){
        return new Select()
                .from(User.class)
                .where("type = ?", userType)
                .where("logged = ?", true)
                .executeSingle();
    }
}
