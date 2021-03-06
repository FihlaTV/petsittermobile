package zekisanmobile.petsitter.Model;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

@SuppressWarnings("serial")
public class Sitter extends RealmObject implements Serializable{

    @PrimaryKey
    private long id;
    private long apiId;
    private String name;
    private String address;
    private String district;
    private String photo;
    private String profile_background;
    private float latitude;
    private float longitude;
    private double value_hour;
    private double value_shift;
    private double value_day;
    private String about_me;
    @Ignore
    private List<Animal> animals;

    public Sitter(){}

    public Sitter(long apiId, String name, String address, String photo, String profile_background, float latitude, float longitude,
                  String district, double value_hour, double value_shift, double value_day, String about_me,
                  List<Animal> animals){
        this.apiId = apiId;
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.profile_background = profile_background;
        this.latitude = latitude;
        this.longitude = longitude;
        this.district = district;
        this.value_hour = value_hour;
        this.value_shift = value_shift;
        this.value_day = value_day;
        this.about_me = about_me;
        this.animals = animals;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProfile_background() {
        return profile_background;
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

    public void setProfile_background(String profile_background) {
        this.profile_background = profile_background;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}
