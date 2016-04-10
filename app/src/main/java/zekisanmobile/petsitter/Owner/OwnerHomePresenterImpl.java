package zekisanmobile.petsitter.Owner;

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
        this.loggedUser = User.getLoggedUser(0);
        new GetOwnerContactsHandler().execute(getStringOwnerApiId());
    }

    @Override
    public String getStringOwnerApiId() {
        return String.valueOf(this.loggedUser.owner.apiId);
    }

    @Override
    public String getLoggedUserName() {
        return loggedUser.name;
    }

    @Override
    public String getLoggedUserEmail() {
        return loggedUser.name;
    }

    @Override
    public String getLoggedUserPhoto() {
        return loggedUser.photo;
    }
}
