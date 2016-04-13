package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import zekisanmobile.petsitter.util.Formatter;

@SuppressWarnings("serial")
@Table(name = "Sitter")
public class Sitter extends Model implements Serializable{

    @Column(name = "api_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long apiId;
    @Column(name = "name")
    public String name;
    @Column(name = "address")
    public String address;
    @Column(name = "district")
    public String district;
    @Column(name = "photo")
    public String photo;
    @Column(name = "profile_background")
    public String profileBackground;
    @Column(name = "latitude")
    public float latitude;
    @Column(name = "longitude")
    public float longitude;
    @Column(name = "value_hour")
    public double value_hour;
    @Column(name = "value_shift")
    public double value_shift;
    @Column(name = "value_day")
    public double value_day;
    @Column(name = "about_me")
    public String about_me;

    public Sitter(){
        super();
    }

    public static List<Sitter> all(){
        return new Select().from(Sitter.class).execute();
    }

    public List<Animal> getAnimals() {
        return new Select()
                .from(Animal.class)
                .innerJoin(AnimalSitter.class).on("Animal.id = AnimalSitter.animal")
                .where("AnimalSitter.sitter = ?", getId())
                .execute();
    }

    public List<Contact> getContacts() {
        return getMany(Contact.class, "sitter");
    }

    public static List<Contact> getNewContacts(long id){
        return new Select()
                .from(Contact.class)
                .where("sitter = ?", id)
                .where("status = ?", 10)
                .where("dateStart>= ?", Formatter.formattedDateToSQL(new Date()))
                .execute();
    }

    public static List<Contact> getCurrentContacts(long id){
        return new Select()
                .from(Contact.class)
                .where("sitter = ?", id)
                .where("status = ?", 30)
                .where("dateFinal >= ?", Formatter.formattedDateToSQL(new Date()))
                .execute();
    }

    public List<Contact> getNextContacts(long id) {
        return new Select()
                .from(Contact.class)
                .where("sitter = ?", id)
                .where("status = ?", 30)
                .where("dateStart < ?", Formatter.formattedDateTimeToSQL(new Date()))
                .execute();
    }

    public List<Contact> getFinishedContacts(long id) {
        return new Select()
                .from(Contact.class)
                .where("sitter = ?", id)
                .where("status = ?", 40)
                .execute();
    }

    public static Sitter insertOrUpdate(long apiId, String name, String address, String photo,
                                              String profile_background, float latitude, float longitude,
                                              String district, double value_hour, double value_shift,
                                              double value_day, String about_me){
        Sitter newSitter;

        if((newSitter = new Select().from(Sitter.class).where("api_id = ?", apiId).executeSingle()) == null) {
            newSitter = new Sitter();
        }

        newSitter.apiId = apiId;
        newSitter.name = name;
        newSitter.address = address;
        newSitter.photo = photo;
        newSitter.profileBackground = profile_background;
        newSitter.latitude = latitude;
        newSitter.longitude = longitude;
        newSitter.district = district;
        newSitter.value_hour = value_hour;
        newSitter.value_shift = value_shift;
        newSitter.value_day = value_day;
        newSitter.about_me = about_me;
        newSitter.save();

        return newSitter;
    }
}
