package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "AnimalSitter")
public class AnimalSitter extends Model{

    @Column(name = "Animal")
    private Animal animal;

    @Column(name = "Sitter")
    private Sitter sitter;

    public AnimalSitter(){
        super();
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public void setSitter(Sitter sitter) {
        this.sitter = sitter;
    }
}
