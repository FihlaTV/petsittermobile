package zekisanmobile.petsitter.util;

import java.io.Serializable;

import zekisanmobile.petsitter.model.Sitter;

public class SitterMarker implements Serializable{

    private String title;
    private String snippet;
    private int badge;
    private Sitter sitter;

    public SitterMarker(String title, String snippet, int badge, Sitter sitter){
        this.title = title;
        this.snippet = snippet;
        this.badge = badge;
        this.sitter = sitter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public void setSitter(Sitter sitter) {
        this.sitter = sitter;
    }
}
