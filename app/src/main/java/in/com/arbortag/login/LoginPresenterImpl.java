package in.com.arbortag.login;


import in.com.arbortag.core.User;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;

    public LoginPresenterImpl(LoginView loginView) {
        this.mView = loginView;
    }

    @Override
    public void doLogin(final User login) {
        if (mView == null) {
            return;
        }
        mView.showProgress();
       /* AmazonArborTagService.getAmazonService().doLogin(login, new AmazonCallback<String>() {
            @Override
            public void onError(ErrorCodes errorCode, String message) {
                if (mView != null)
                    mView.onError(errorCode, message);
            }

            @Override
            public void onSuccess(String s) {
                if (mView == null) {
                    return;
                }
                mView.hideProgress();
                mView.onLoginSuccess();

            }
        });*/

    }

    @Override
    public void onDestroy() {
        mView = null;
    }


}
