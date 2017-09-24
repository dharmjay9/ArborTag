package in.com.arbortag.registation;

import in.com.arbortag.core.User;
import in.com.arbortag.network.ErrorCodes;
import in.com.arbortag.validator.IValidatorResult;

public class RegistrationPresenterImpl implements RegistrationPresenter{

    private RegistrationView mView;
    public RegistrationPresenterImpl(RegistrationView registrationView) {
        this.mView = registrationView;
    }

    @Override
    public void doRegistration(final User user) {
        if (mView == null) {
            return;
        }
        mView.showProgress();
        RegistrationValidatorImpl registrationValidator = new RegistrationValidatorImpl();
        registrationValidator.validate(user, new IValidatorResult() {
            @Override
            public void onSuccess() {
                registaration(user);
            }

            @Override
            public void onError(int s) {
                mView.onError(ErrorCodes.VALIDATION_ERROR, s);
            }
        });

    }

    private void registaration(User user) {
       /* AmazonArborTagService.getAmazonService().doRegistration(user, new AmazonCallback<String>() {
            @Override
            public void onError(ErrorCodes errorCode, String message) {
                if (mView != null)
                    mView.onError(errorCode, message);
            }

            @Override
            public void onSuccess(String registaration) {
                //Save the phone no to Shared prefresncs
                if (mView == null) {
                    return;
                }
                mView.hideProgress();
                mView.onRegistrationSuccess(registaration);
            }
        });*/
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
