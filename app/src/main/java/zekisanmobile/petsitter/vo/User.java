package zekisanmobile.petsitter.vo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

public class User extends RealmObject implements Validation, Serializable {

    //region Members
    @PrimaryKey
    long id;

    String name;

    String email;

    String photo;

    boolean logged;

    int category; // 0 - Owner, 1 - PetSitter
    //endregion

    //region Accessors
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
//endregion

    //region Inherited Methods
    @Override
    public void validate() {
        if(name == null){
            throw new ValidationFailedException("Nome inválido.");
        }
    }
    //endregion
}
