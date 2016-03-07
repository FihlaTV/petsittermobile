package zekisanmobile.petsitter.Util;

public class SitterMarker {

    private String title;
    private String snippet;
    private int badge;

    public SitterMarker(String title, String snippet, int badge){
        this.title = title;
        this.snippet = snippet;
        this.badge = badge;
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
}
