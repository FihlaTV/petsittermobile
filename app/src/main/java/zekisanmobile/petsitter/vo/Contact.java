package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import zekisanmobile.petsitter.util.Validation;

public class Contact extends RealmObject implements Validation {

    //region Members
    @PrimaryKey
    @JsonIgnore
    long id;

    @JsonProperty("id")
    long apiId;

    @JsonProperty("date_start")
    String dateStart;

    @JsonProperty("date_final")
    String dateFinal;

    @JsonProperty("time_start")
    String timeStart;

    @JsonProperty("time_final")
    String timeFinal;

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("sitter")
    Sitter sitter;

    @JsonProperty("owner")
    Owner owner;

    @JsonProperty("total_value")
    double totalValue;

    @JsonProperty("status")
    int status;

    @Ignore
    @JsonIgnore
    public String updated_at;

    @JsonProperty("animals")
    RealmList<Animal> animals;
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(String dateFinal) {
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
    //endregion

    @Override
    public void validate() {

    }
}
