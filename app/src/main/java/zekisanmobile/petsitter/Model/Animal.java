package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "Animal")
public class Animal extends Model implements Serializable{

    @Column(name = "Name")
    public String name;

    public Animal(){
        super();
    }

    public String getName() {
        return name;
    }

    public static void create(String name){
        Animal animal = new Animal();
        animal.name = name;
        animal.save();
    }

    public static List<Animal> all(){
        return new Select()
                .from(Animal.class)
                .orderBy("Name ASC")
                .execute();
    }

    public String toString(){
        return name;
    }
}
