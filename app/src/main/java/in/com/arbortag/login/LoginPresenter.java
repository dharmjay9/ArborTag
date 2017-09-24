package in.com.arbortag.login;

import in.com.arbortag.BasePresenter;
import in.com.arbortag.core.User;

public interface LoginPresenter extends BasePresenter {
    void doLogin(User login);

}
