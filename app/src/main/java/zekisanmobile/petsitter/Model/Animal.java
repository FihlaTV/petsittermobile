package zekisanmobile.petsitter.Model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Animal extends RealmObject implements Serializable{

    @PrimaryKey
    private long id;
    private String name;

    public Animal(){}

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
}
