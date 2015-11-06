package zekisanmobile.petsitter.Model;

public class SitterProfileListItem {

    private String name;
    private int id;
    private Sitter sitter;

    public SitterProfileListItem(Sitter sitter){
        this.sitter = sitter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public void setSitter(Sitter sitter) {
        this.sitter = sitter;
    }
}
