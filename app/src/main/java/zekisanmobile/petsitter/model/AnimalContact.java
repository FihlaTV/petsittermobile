package zekisanmobile.petsitter.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "AnimalContact")
public class AnimalContact extends Model{

    @Column(name = "animal")
    public Animal animal;

    @Column(name = "contact")
    public Contact contact;

    public AnimalContact(Animal animal, Contact contact){
        super();
        this.animal = animal;
        this.contact = contact;
    }

    public List<Animal> animals(){
        return getMany(Animal.class, "AnimalContact");
    }

    public List<Contact> contacts(){
        return getMany(Contact.class, "AnimalContact");
    }

}
