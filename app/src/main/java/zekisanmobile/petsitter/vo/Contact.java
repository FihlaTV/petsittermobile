package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import zekisanmobile.petsitter.util.Validation;

public class Contact extends RealmObject implements Validation, Serializable {

    //region Members
    @PrimaryKey
    @JsonProperty("app_id")
    long id;

    @JsonProperty("id")
    long apiId;

    @JsonProperty("date_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="EST")
    Date dateStart;

    @JsonProperty("date_final")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="EST")
    Date dateFinal;

    @JsonProperty("time_start")
    String timeStart;

    @JsonProperty("time_final")
    String timeFinal;

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("sitter")
    Sitter sitter;

    @JsonProperty("pet_owner")
    Owner owner;

    @JsonProperty("total_value")
    double totalValue;

    @JsonProperty("status_cd")
    int status;

    @Ignore
    @JsonIgnore
    public String updated_at;

    @JsonProperty("animals")
    RealmList<Animal> animals;

    @Ignore
    @JsonProperty("sitter_id")
    long sitter_id;

    @Ignore
    @JsonProperty("pet_owner_id")
    long pet_owner_id;
    //endregion

    //region Accessors
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getApiId() {
        return apiId;
    }

    public void setApiId(long apiId) {
        this.apiId = apiId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeFinal() {
        return timeFinal;
    }

    public void setTimeFinal(String timeFinal) {
        this.timeFinal = timeFinal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public void setSitter(Sitter sitter) {
        this.sitter = sitter;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAnimals(RealmList<Animal> animals) {
        this.animals = animals;
    }

    public RealmList<Animal> getAnimals() {
        return animals;
    }

    public long getSitter_id() {
        return sitter_id;
    }

    public void setSitter_id(long sitter_id) {
        this.sitter_id = sitter_id;
    }

    public long getPet_owner_id() {
        return pet_owner_id;
    }

    public void setPet_owner_id(long pet_owner_id) {
        this.pet_owner_id = pet_owner_id;
    }

    //endregion

    @Override
    public void validate() {

    }
}
