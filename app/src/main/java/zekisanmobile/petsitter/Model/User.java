package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "User")
public class User extends Model {

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "photo")
    private String photo;
    @Column(name = "logged")
    private boolean logged;
    @Column(name = "type")
    private int type; // 0 - Owner, 1 - PetSitter
    @Column(name = "owner")
    private Owner owner;
    @Column(name = "sitter")
    private Sitter sitter;

    public static User getLoggedUser(int userType){
        return new Select()
                .from(User.class)
                .where("type = ?", userType)
                .where("logged = ?", true)
                .executeSingle();
    }
}
