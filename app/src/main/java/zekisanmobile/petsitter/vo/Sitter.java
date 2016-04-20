package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SitterRealmProxy;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import zekisanmobile.petsitter.util.RealmListParcelConverter;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

@Parcel(implementations = {SitterRealmProxy.class},
        value = Parcel.Serialization.FIELD,
        analyze = {Sitter.class})
public class Sitter extends RealmObject implements Validation, Serializable {

    //region Members
    @PrimaryKey
    @JsonProperty("id")
    long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("address")
    String address;

    @JsonProperty("district")
    String district;

    @JsonProperty("photo")
    String photo;

    @JsonProperty("header_background")
    String profileBackground;

    @JsonProperty("latitude")
    float latitude;

    @JsonProperty("longitude")
    float longitude;

    @JsonProperty("value_hour")
    double value_hour;

    @JsonProperty("value_shift")
    double value_shift;

    @JsonProperty("value_day")
    double value_day;

    @JsonProperty("about_me")
    String about_me;

    @JsonIgnore
    User user;

    @JsonProperty("animals")
    @ParcelPropertyConverter(RealmListParcelConverter.class)
    RealmList<Animal> animals;

    @JsonIgnore
    @Ignore
    public List<String> cares;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProfileBackground() {
        return profileBackground;
    }

    public void setProfileBackground(String profileBackground) {
        this.profileBackground = profileBackground;
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

    public double getValue_hour() {
        return value_hour;
    }

    public void setValue_hour(double value_hour) {
        this.value_hour = value_hour;
    }

    public double getValue_shift() {
        return value_shift;
    }

    public void setValue_shift(double value_shift) {
        this.value_shift = value_shift;
    }

    public double getValue_day() {
        return value_day;
    }

    public void setValue_day(double value_day) {
        this.value_day = value_day;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //endregion

    //region Inherited Methods
    @Override
    public void validate() {
        if (name == null) {
            throw new ValidationFailedException("Nome inválido.");
        }
    }

    public RealmList<Animal> getAnimals() {
        return animals;
    }
    //endregion
}
