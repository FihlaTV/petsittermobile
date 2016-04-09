package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "Contact")
public class Contact extends Model {

    @Column(name = "api_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long apiId;
    @Column(name = "dateStart")
    public Date dateStart;
    @Column(name = "dateFinal")
    public Date dateFinal;
    @Column(name = "timeStart")
    public String timeStart;
    @Column(name = "time_final")
    public String timeFinal;
    @Column(name = "createdAt")
    public String createdAt;
    @Column(name = "sitter")
    public Sitter sitter;
    @Column(name = "owner")
    public Owner owner;
    @Column(name = "totalValue")
    public double totalValue;
    @Column(name = "status")
    public int status;

    public Contact(){
        super();
    }

    public List<Animal> getAnimals() {
        return getMany(Animal.class, "AnimalContact");
    }

    public static List<Contact> all(){
        return new Select()
                .from(Contact.class)
                .execute();
    }

    public static Contact insertOrUpdate(long apiId, Date date_start, Date date_final,
                                                String time_start, String time_final, String created_at,
                                                Sitter sitter, Owner owner,
                                                double totalValue, int status, List<Animal> animals) {
        Contact newContact;

        if ((newContact = new Select().from(Contact.class).where("api_id = ?", apiId).executeSingle()) == null) {
            newContact = new Contact();
        }

        newContact.apiId = apiId;
        newContact.dateStart = date_start;
        newContact.dateFinal = date_final;
        newContact.timeStart = time_start;
        newContact.timeFinal = time_final;
        newContact.createdAt = created_at;
        newContact.sitter = sitter;
        newContact.owner = owner;
        newContact.totalValue = totalValue;
        newContact.status = status;
        if (newContact.getAnimals().size() > 0) newContact.getAnimals().clear();
        newContact.getAnimals().addAll(animals);
        newContact.save();

        return newContact;
    }

    public static void delete(long id){
        new Delete().from(Contact.class).where("id = ?", id).execute();
    }

    public static void updateStatus(long id, int status) {
        Contact contact = new Select().from(Contact.class).where("id = ?", id).executeSingle();
        contact.status = status;
        contact.save();
    }
}
