package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

public class Animal extends RealmObject implements Validation, Serializable {

    //region Members
    @JsonProperty("id")
    @PrimaryKey
    long id;

    @JsonProperty("name")
    String name;

    @Ignore
    @JsonIgnore
    public String created_at;

    @Ignore
    @JsonIgnore
    public String updated_at;
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
