package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.model.PetSitterDatabase;
import zekisanmobile.petsitter.util.Validation;

@Table(database = PetSitterDatabase.class)
public class Contact extends BaseModel implements Validation {

    //region Members
    @PrimaryKey(autoincrement = true)
    @JsonIgnore
    long id;

    @Column
    @JsonProperty("id")
    long apiId;

    @JsonProperty("date_start")
    @Column
    String dateStart;

    @JsonProperty("date_final")
    @Column
    String dateFinal;

    @JsonProperty("time_start")
    @Column
    String timeStart;

    @JsonProperty("time_final")
    @Column
    String timeFinal;

    @JsonProperty("created_at")
    @Column
    String createdAt;

    @JsonProperty("sitter")
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    Sitter sitter;

    @JsonProperty("owner")
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    Owner owner;

    @JsonProperty("total_value")
    @Column
    double totalValue;

    @JsonProperty("status")
    @Column
    int status;

    @ColumnIgnore
    @JsonIgnore
    public String updated_at;

    @Column
    @JsonProperty("animals")
    List<Animal> animals;
    //endregion

    @OneToMany
    public List<Animal> getAnimals() {
        if (animals == null) {
            List<Animal_Contact> list = SQLite.select()
                    .from(Animal_Contact.class)
                    .where()
                    .queryList();
            animals = new ArrayList<>();
            for(Animal_Contact item : list) {
                animals.add(item.getAnimal());
            }
        }
        return animals;
    }

    @Override
    public void validate() {

    }
}
