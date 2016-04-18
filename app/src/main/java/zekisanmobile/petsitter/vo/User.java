package zekisanmobile.petsitter.vo;

import com.raizlabs.android.dbflow.StringUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import zekisanmobile.petsitter.model.PetSitterDatabase;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

@Table(database = PetSitterDatabase.class)
public class User extends BaseModel implements Validation, Serializable {

    //region Members
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name;

    @Column
    String email;

    @Column
    String photo;

    @Column
    boolean logged;

    @Column
    int type; // 0 - Owner, 1 - PetSitter
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    //endregion

    //region Inherited Methods
    @Override
    public void validate() {
        if(!StringUtils.isNotNullOrEmpty(name)){
            throw new ValidationFailedException("Nome inválido.");
        }
    }
    //endregion
}
