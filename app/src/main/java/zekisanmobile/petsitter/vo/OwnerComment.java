package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class OwnerComment extends RealmObject {

    @JsonProperty("id")
    @PrimaryKey
    long id;

    @JsonProperty("text")
    String text;

    @JsonIgnore
    @Ignore
    public long rate_id;

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
