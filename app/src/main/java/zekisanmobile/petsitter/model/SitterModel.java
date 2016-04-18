package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zekisanmobile.petsitter.util.Formatter;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Contact_Table;
import zekisanmobile.petsitter.vo.Sitter;
import zekisanmobile.petsitter.vo.Sitter_Animal;
import zekisanmobile.petsitter.vo.Sitter_Animal_Table;
import zekisanmobile.petsitter.vo.Sitter_Table;
import zekisanmobile.petsitter.vo.User_Table;

public class SitterModel {

    public void save(Sitter sitter) {
        sitter.validate();
        sitter.save();
    }

    public void saveAll(final List<Sitter> sitters) {
        ValidationUtil.pruneInvalid(sitters);
        if (sitters.isEmpty()) {
            return;
        }
        List<Sitter> sittersToSave = new ArrayList<>();
        List<Sitter> sittersToUpdate = new ArrayList<>();

        for (Sitter sitter : sitters) {
            if (findByApiId(sitter.getApiId()) == null){
                sittersToSave.add(sitter);
            } else {
                sittersToUpdate.add(sitter);
                new SQLite().update(Sitter.class)
                        .set(Sitter_Table.apiId.eq(sitter.getApiId()),
                                Sitter_Table.name.eq(sitter.getName()),
                                Sitter_Table.address.eq(sitter.getAddress()),
                                Sitter_Table.district.eq(sitter.getDistrict()),
                                Sitter_Table.profileBackground.eq(sitter.getProfileBackground()),
                                Sitter_Table.latitude.eq(sitter.getLatitude()),
                                Sitter_Table.longitude.eq(sitter.getLongitude()),
                                Sitter_Table.value_hour.eq(sitter.getValue_hour()),
                                Sitter_Table.value_day.eq(sitter.getValue_day()),
                                Sitter_Table.value_shift.eq(sitter.getValue_shift()),
                                Sitter_Table.about_me.eq(sitter.getAbout_me())
                                )
                        .where(Sitter_Table.id.eq(sitter.getId()))
                        .execute();
            }
        }

        TransactionManager.getInstance()
                .addTransaction(new SaveModelTransaction(ProcessModelInfo
                        .withModels(sittersToSave)));
    }

    public Sitter findByApiId(long apiId) {
        return new SQLite().select()
                .from(Sitter.class)
                .where(Sitter_Table.apiId.is(apiId))
                .querySingle();
    }

    public Sitter find(long id) {
        return new SQLite().select()
                .from(Sitter.class)
                .where(Sitter_Table.id.is(id))
                .querySingle();
    }

    public List<Sitter> all() {
        return new SQLite().select()
                .from(Sitter.class)
                .queryList();
    }

    public List<Contact> getNextContacts(long id) {
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.sitter_id.is(id),
                        Contact_Table.status.is(30),
                        Contact_Table.dateStart.lessThan(
                                Formatter.formattedDateToSQL(new Date())))
                .orderBy(Contact_Table.dateStart, false)
                .queryList();
    }

    public List<Contact> getFinishedContacts(long id) {
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.sitter_id.is(id),
                        Contact_Table.status.is(40))
                .queryList();
    }

    public static List<Contact> getNewContacts(long id){
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.sitter_id.is(id),
                        Contact_Table.status.is(10),
                        Contact_Table.dateStart.greaterThanOrEq(
                                Formatter.formattedDateToSQL(new Date())))
                .queryList();
    }

    public static List<Contact> getCurrentContacts(long id){
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.sitter_id.is(id),
                        Contact_Table.status.is(30),
                        Contact_Table.dateFinal.greaterThanOrEq(
                                Formatter.formattedDateToSQL(new Date())))
                .orderBy(Contact_Table.dateStart, false)
                .queryList();
    }

    public Sitter getLoggedSitterUser() {
        zekisanmobile.petsitter.vo.User user = new SQLite().select()
                .from(zekisanmobile.petsitter.vo.User.class)
                .where(User_Table.logged.is(true), User_Table.type.is(1))
                .querySingle();

        return new SQLite().select()
                .from(Sitter.class)
                .where(Sitter_Table.user_id.is(user.getId()))
                .querySingle();
    }

    public List<Animal> getAnimals(Sitter sitter) {
        List<Sitter_Animal> list = new SQLite().select()
                .from(Sitter_Animal.class)
                .where(Sitter_Animal_Table.sitter_id.eq(sitter.getId()))
                .queryList();

        List<Animal> animals = new ArrayList<>();
        for (Sitter_Animal sitter_animal : list) {
            animals.add(sitter_animal.getAnimal());
        }
        return animals;
    }
}
