package zekisanmobile.petsitter.Owner;

import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Model.User;

public class OwnerHomePresenterImpl implements OwnerHomePresenter{

    private User loggedUser;
    private OwnerHomeView view;

    public OwnerHomePresenterImpl(OwnerHomeView view){
        this.view = view;
        getLoggedUser();
    }

    @Override
    public void getLoggedUser() {
        this.loggedUser = UserDAO.getLoggedUser(0);
    }

    @Override
    public String getLoggedUserName() {
        return loggedUser.getName();
    }

    @Override
    public String getLoggedUserEmail() {
        return loggedUser.getEmail();
    }

    @Override
    public String getLoggedUserPhoto() {
        return loggedUser.getPhoto();
    }
}
