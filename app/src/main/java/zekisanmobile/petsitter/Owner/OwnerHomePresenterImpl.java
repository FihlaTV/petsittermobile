package zekisanmobile.petsitter.Owner;

import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Handlers.GetOwnerContactsHandler;
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
        new GetOwnerContactsHandler().execute(getStringOwnerApiId());
    }

    @Override
    public String getStringOwnerApiId() {
        return String.valueOf(this.loggedUser.getOwner().getApiId());
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
