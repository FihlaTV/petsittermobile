package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.raizlabs.android.dbflow.StringUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import zekisanmobile.petsitter.model.PetSitterDatabase;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

@Table(database = PetSitterDatabase.class)
@ManyToMany(referencedTable = Contact.class)
public class Animal extends BaseModel implements Validation{

    //region Members
    @PrimaryKey(autoincrement = true)
    @JsonIgnore
    long id;

    @Column
    @JsonProperty("name")
    String name;

    @ColumnIgnore
    @JsonIgnore
    public String created_at;

    @ColumnIgnore
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
        if(!StringUtils.isNotNullOrEmpty(name)){
            throw new ValidationFailedException("Nome inválido.");
        }
    }
    //endregion
}
