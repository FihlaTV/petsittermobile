package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "AnimalContact")
public class AnimalContact extends Model{

    @Column(name = "animal")
    private Animal animal;

    @Column(name = "contact")
    private Contact contact;

    public AnimalContact(){
        super();
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
