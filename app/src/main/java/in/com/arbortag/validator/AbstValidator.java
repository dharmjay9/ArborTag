package in.com.arbortag.validator;

import in.com.arbortag.ArborTagApp;
import in.com.arbortag.R;
import in.com.arbortag.network.NetworkConnector;


public abstract class AbstValidator <T> implements IValidator<T> {

    @Override
    public boolean validate(T t, IValidatorResult iValidatorResult) {
        if (!NetworkConnector.isNetworkAvailable(ArborTagApp.getAppContext())) {
            iValidatorResult.onError(R.string.no_internet);
            return false;
        }
        return true;
    }


}
