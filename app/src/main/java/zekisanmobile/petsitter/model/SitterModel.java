package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Sitter;
import zekisanmobile.petsitter.vo.Sitter_Table;

public class SitterModel {

    public void save(Sitter sitter){
        sitter.validate();
        sitter.save();
    }

    public void saveAll(final List<Sitter> sitters){
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

    public List<Sitter> all(){
        return new SQLite().select()
                .from(Sitter.class)
                .queryList();
    }
}
