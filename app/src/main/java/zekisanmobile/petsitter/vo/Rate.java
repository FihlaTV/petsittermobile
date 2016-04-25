package zekisanmobile.petsitter.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Rate extends RealmObject{

    @JsonProperty("id")
    @PrimaryKey
    long id;

    @JsonProperty("positive")
    boolean positive;

    @JsonProperty("pet_owner_comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    OwnerComment ownerComment;

    @JsonProperty("sitter_comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    SitterComment sitterComment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OwnerComment getOwnerComment() {
        return ownerComment;
    }

    public void setOwnerComment(OwnerComment ownerComment) {
        this.ownerComment = ownerComment;
    }

    public SitterComment getSitterComment() {
        return sitterComment;
    }

    public void setSitterComment(SitterComment sitterComment) {
        this.sitterComment = sitterComment;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }
}
