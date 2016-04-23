package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

public class Owner extends RealmObject implements Validation, Serializable {

    //region Members
    @PrimaryKey
    @JsonIgnore
    long id;

    @JsonProperty("id")
    long apiId;

    @JsonProperty("name")
    String name;

    @JsonProperty("address")
    String address;

    @JsonProperty("district")
    String district;

    @JsonProperty("latitude")
    float latitude;

    @JsonProperty("longitude")
    float longitude;

    @JsonProperty("photo")
    String photo;

    @JsonProperty("user")
    User user;

    @JsonIgnore
    @Ignore
    public int user_id;

    @Ignore
    @JsonIgnore
    public String updated_at;

    @Ignore
    @JsonIgnore
    public String created_at;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
