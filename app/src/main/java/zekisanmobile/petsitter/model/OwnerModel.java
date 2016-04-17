package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.List;

import zekisanmobile.petsitter.util.Formatter;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Contact_Table;
import zekisanmobile.petsitter.vo.Owner;
import zekisanmobile.petsitter.vo.Owner_Table;
import zekisanmobile.petsitter.vo.User;
import zekisanmobile.petsitter.vo.User_Table;

public class OwnerModel {

    public void save(Owner owner){
        owner.validate();
        owner.save();
    }

    public void saveAll(final List<Owner> owners){
        ValidationUtil.pruneInvalid(owners);
        if (owners.isEmpty()) {
            return;
        }
        TransactionManager.getInstance()
                .addTransaction(new SaveModelTransaction(ProcessModelInfo.withModels(owners)));
    }

    public Owner find(long id) {
        return new SQLite().select()
                .from(Owner.class)
                .where(Owner_Table.id.is(id))
                .querySingle();
    }

    public List<Owner> all(){
        return new SQLite().select()
                .from(Owner.class)
                .queryList();
    }

    public Owner getLoggedOwnerUser() {
        User user = new SQLite().select()
                .from(User.class)
                .where(User_Table.logged.is(true), User_Table.type.is(0))
                .querySingle();

        return new SQLite().select()
                .from(Owner.class)
                .where(Owner_Table.user_id.is(user.getId()))
                .querySingle();
    }

    public static List<Contact> getCurrentContacts(long id){
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.owner_id.is(id),
                        Contact_Table.status.is(30),
                        Contact_Table.dateFinal.greaterThanOrEq(
                                Formatter.formattedDateToSQL(new Date())))
                .orderBy(Contact_Table.dateStart, false)
                .queryList();
    }

    public static List<Contact> getNewContacts(long id){
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.owner_id.is(id),
                        Contact_Table.status.is(10),
                        Contact_Table.dateStart.greaterThanOrEq(
                                Formatter.formattedDateToSQL(new Date())))
                .orderBy(Contact_Table.dateStart, false)
                .queryList();
    }

    public List<Contact> getFinishedContacts(long id) {
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.owner_id.is(id),
                        Contact_Table.status.is(40))
                .orderBy(Contact_Table.dateFinal, false)
                .queryList();
    }

    public List<Contact> getRejectContacts(long id) {
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.owner_id.is(id),
                        Contact_Table.status.is(20))
                .orderBy(Contact_Table.dateFinal, false)
                .queryList();
    }
}
