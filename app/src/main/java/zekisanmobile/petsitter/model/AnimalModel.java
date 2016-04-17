package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.vo.Animal_Table;

public class AnimalModel {

    public void save(Animal animal){
        animal.validate();
        animal.save();
    }

    public void saveAll(final List<Animal> animals){
        ValidationUtil.pruneInvalid(animals);
        if (animals.isEmpty()) {
            return;
        }
        TransactionManager.getInstance()
                .addTransaction(new SaveModelTransaction(ProcessModelInfo.withModels(animals)));
    }

    public Animal find(long id) {
        return new SQLite().select()
                .from(Animal.class)
                .where(Animal_Table.id.is(id))
                .querySingle();
    }

    public List<Animal> all(){
        return new SQLite().select()
                .from(Animal.class)
                .queryList();
    }

    public Animal findByName(String name) {
        return new SQLite().select()
                .from(Animal.class)
                .where(Animal_Table.name.eq(name))
                .querySingle();
    }
}
