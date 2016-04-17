package zekisanmobile.petsitter.model;

import com.activeandroid.query.Select;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import zekisanmobile.petsitter.util.ValidationUtil;
import zekisanmobile.petsitter.vo.*;
import zekisanmobile.petsitter.vo.User;

public class UserModel {

    public void save(User user){
        user.validate();
        user.save();
    }

    public void saveAll(final List<User> users){
        ValidationUtil.pruneInvalid(users);
        if (users.isEmpty()) {
            return;
        }
        TransactionManager.getInstance()
                .addTransaction(new SaveModelTransaction(ProcessModelInfo.withModels(users)));
    }

    public User find(long id) {
        return new SQLite().select()
                .from(User.class)
                .where(User_Table.id.is(id))
                .querySingle();
    }

    public List<User> all(){
        return new SQLite().select()
                .from(User.class)
                .queryList();
    }
}
