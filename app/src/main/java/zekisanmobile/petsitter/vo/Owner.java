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

import zekisanmobile.petsitter.model.PetSitterDatabase;
import zekisanmobile.petsitter.util.Validation;
import zekisanmobile.petsitter.util.ValidationFailedException;

@Table(database = PetSitterDatabase.class)
public class Owner extends BaseModel implements Validation {

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

    @JsonProperty("latitude")
    @Column
    float latitude;

    @JsonProperty("longitude")
    @Column
    float longitude;

    @JsonIgnore
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    User user;

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
