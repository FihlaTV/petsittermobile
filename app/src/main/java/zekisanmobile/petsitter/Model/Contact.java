package zekisanmobile.petsitter.Model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contact extends RealmObject{

    @PrimaryKey
    private long id;

    private Date date_start;
    private Date date_final;
    private String time_start;
    private String time_final;
    private Sitter sitter;
    private Owner owner;

    public Contact(){}

    public Contact(long id, Date date_start, Date date_final, String time_start,
                   String time_final, Sitter sitter, Owner owner){
        this.id = id;
        this.date_start = date_start;
        this.date_final = date_final;
        this.time_start = time_start;
        this.time_final = time_final;
        this.sitter = sitter;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_final() {
        return date_final;
    }

    public void setDate_final(Date date_final) {
        this.date_final = date_final;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_final() {
        return time_final;
    }

    public void setTime_final(String time_final) {
        this.time_final = time_final;
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
}
