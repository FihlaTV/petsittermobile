package zekisanmobile.petsitter.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

import zekisanmobile.petsitter.Util.Formatter;

@Table(name = "Owner")
public class Owner extends Model {

    @Column(name = "api_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long apiId;
    @Column(name = "name")
    public String name;
    @Column(name = "address")
    public String address;
    @Column(name = "district")
    public String district;
    @Column(name = "latitude")
    public float latitude;
    @Column(name = "longitude")
    public float longitude;

    public Owner(){
        super();
    }
    
    public List<Contact> getContacts() {
        return getMany(Contact.class, "owner");
    }

    public static List<Contact> getNewContacts(long id){
        return new Select()
                .from(Contact.class)
                .where("owner = ?", id)
                .where("status = ?", 10)
                .where("dateStart>= ?", Formatter.formattedDateToSQL(new Date()))
                .orderBy("dateStart DESC")
                .execute();
    }

    public static List<Contact> getCurrentContacts(long id){
        return new Select()
                .from(Contact.class)
                .where("owner = ?", id)
                .where("status = ?", 30)
                .where("dateFinal >= ?", Formatter.formattedDateToSQL(new Date()))
                .orderBy("dateStart DESC")
                .execute();
    }

    public List<Contact> getNextContacts(long id) {
        return new Select()
                .from(Contact.class)
                .where("owner = ?", id)
                .where("status = ?", 30)
                .where("dateStart < ?", Formatter.formattedDateTimeToSQL(new Date()))
                .execute();
    }

    public List<Contact> getFinishedContacts(long id) {
        return new Select()
                .from(Contact.class)
                .where("owner = ?", id)
                .where("status = ?", 40)
                .orderBy("dateFinal DESC")
                .execute();
    }

    public List<Contact> getRejectContacts(long id) {
        return new Select()
                .from(Contact.class)
                .where("owner = ?", id)
                .where("status = ?", 20)
                .orderBy("dateFinal DESC")
                .execute();
    }

    public static List<Owner> all(){
        return new Select().from(Owner.class).execute();
    }

    public static Owner insertOrUpdate(long apiId, String name, String address, String district,
                                            float latitude, float longitude){
        Owner newOwner;

        if((newOwner = new Select().from(Owner.class).where("api_id = ?", apiId).executeSingle()) == null) {
            newOwner = new Owner();
        }

        newOwner.apiId = apiId;
        newOwner.name = name;
        newOwner.address = address;
        newOwner.district = district;
        newOwner.latitude = latitude ;
        newOwner.longitude = longitude;
        newOwner.save();

        return newOwner;
    }
}
