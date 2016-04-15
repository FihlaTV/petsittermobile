package zekisanmobile.petsitter.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "AnimalSitter")
public class AnimalSitter extends Model{

    @Column(name = "Animal")
    public Animal animal;

    @Column(name = "Sitter")
    public Sitter sitter;

    public AnimalSitter(Animal animal, Sitter sitter){
        super();
        this.animal = animal;
        this.sitter = sitter;
    }

    public List<Animal> animals(){
        return getMany(Animal.class, "AnimalSitter");
    }

    public List<Sitter> sitters(){
        return getMany(Sitter.class, "AnimalSitter");
    }
}
