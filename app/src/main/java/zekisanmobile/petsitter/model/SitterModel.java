package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.List;

import zekisanmobile.petsitter.util.Formatter;
import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.*;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Sitter;

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
        TransactionManager.getInstance()
                .addTransaction(new SaveModelTransaction(ProcessModelInfo.withModels(sitters)));
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
}
