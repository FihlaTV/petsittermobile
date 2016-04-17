package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.runtime.DBTransactionInfo;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;

import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Contact;
import zekisanmobile.petsitter.vo.Contact_Table;

public class ContactModel {

    public void save(Contact contact){
        contact.validate();
        contact.save();
    }

    public void saveAll(final List<Contact> contacts){
        ValidationUtil.pruneInvalid(contacts);
        if (contacts.isEmpty()) {
            return;
        }
        TransactionManager.getInstance()
                .addTransaction(new SaveModelTransaction(ProcessModelInfo.withModels(contacts)));
    }

    public Contact find(long id) {
        return new SQLite().select()
                .from(Contact.class)
                .where(Contact_Table.id.is(id))
                .querySingle();
    }

    public List<Contact> all(){
        return new SQLite().select()
                .from(Contact.class)
                .queryList();
    }

    public void updateStatus(long apiId, int status) {
        Where<Contact> update = SQLite.update(Contact.class)
                .set(Contact_Table.status.eq(status))
                .where(Contact_Table.apiId.eq(apiId));
        TransactionManager.getInstance().addTransaction(new QueryTransaction(
                DBTransactionInfo.create(BaseTransaction.PRIORITY_UI), update));
    }

    public void delete(long id) {
        SQLite.delete(Contact.class)
                .where(Contact_Table.id.is(id))
                .query();
    }
}
