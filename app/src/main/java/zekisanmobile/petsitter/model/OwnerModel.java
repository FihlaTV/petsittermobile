package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Owner;
import zekisanmobile.petsitter.vo.Owner_Table;

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

}
