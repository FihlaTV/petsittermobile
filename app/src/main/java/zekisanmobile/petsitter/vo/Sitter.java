package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.raizlabs.android.dbflow.StringUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import zekisanmobile.petsitter.model.PetSitterDatabase;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

@Table(database = PetSitterDatabase.class)
public class Sitter extends BaseModel implements Validation {

    //region Members
    @PrimaryKey(autoincrement = true)
    @JsonIgnore
    long id;

    @JsonProperty("id")
    @Column
    long apiId;

    @JsonProperty("name")
    @Column
    String name;

    @JsonProperty("address")
    @Column
    String address;

    @JsonProperty("district")
    @Column
    String district;

    @JsonProperty("photo")
    @Column(name = "photo")
    String photo;

    @JsonProperty("header_background")
    @Column
    String profileBackground;

    @JsonProperty("latitude")
    @Column
    float latitude;

    @JsonProperty("longitude")
    @Column
    float longitude;

    @JsonProperty("value_hour")
    @Column
    double value_hour;

    @JsonProperty("value_shift")
    @Column
    double value_shift;

    @JsonProperty("value_day")
    @Column
    double value_day;

    @JsonProperty("about_me")
    @Column
    String about_me;

    @JsonIgnore
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    User user;

    @JsonProperty("animals")
    @ColumnIgnore
    public List<Animal> animals;

    @JsonIgnore
    @ColumnIgnore
    public List<String> cares;

    @ColumnIgnore
    @JsonIgnore
    public String updated_at;

    @ColumnIgnore
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
        if (!StringUtils.isNotNullOrEmpty(name)) {
            throw new ValidationFailedException("Nome inválido.");
        }
    }
    //endregion
}
