package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SitterComment extends RealmObject{

    @JsonProperty("id")
    @PrimaryKey
    long id;

    @JsonProperty("text")
    String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
