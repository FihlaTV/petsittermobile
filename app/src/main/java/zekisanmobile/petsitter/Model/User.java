package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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

    public static User getLoggedUser(int userType){
        return new Select()
                .from(User.class)
                .where("type = ?", userType)
                .where("logged = ?", true)
                .executeSingle();
    }
}
