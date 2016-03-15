package zekisanmobile.petsitter.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Owner extends RealmObject {

    @PrimaryKey
    private long id;
    private long apiId;
    private String nome;
    private String address;
    private String district;
    private float latitude;
    private float longitude;

    public Owner(){}

    public Owner(String nome, String address, String district, float latitude, float longitude){
        this.nome = nome;
        this.address = address;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
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
}
