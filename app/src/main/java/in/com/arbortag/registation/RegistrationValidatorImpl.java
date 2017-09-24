package in.com.arbortag.registation;

import android.text.TextUtils;

import in.com.arbortag.R;
import in.com.arbortag.Utils.ArborTagUtils;
import in.com.arbortag.core.User;
import in.com.arbortag.validator.AbstValidator;
import in.com.arbortag.validator.IValidatorResult;


public class RegistrationValidatorImpl  extends AbstValidator<User> {
    @Override
    public boolean validate(User user, IValidatorResult iValidatorResult) {
        if (!super.validate(user, iValidatorResult)) {
            return false;
        }
        // iValidatorResult.onSuccess();
        if (TextUtils.isEmpty(user.getFirstName())) {
            iValidatorResult.onError(R.string.ERR_EMPTY_FIRST_NAME);
        } else if (TextUtils.isEmpty(user.getLastName())) {
            iValidatorResult.onError(R.string.ERR_EMPTY_LAST_NAME);
        } else if (user.getEmail().isEmpty()) {
            iValidatorResult.onError(R.string.ERR_CRT_EMAIL);
        } else if (!ArborTagUtils.isValidEmail(user.getEmail().toString())) {
            iValidatorResult.onError(R.string.ERR_EMPTY_EMAIL);
        } else if (user.getMobile().isEmpty()) {
            iValidatorResult.onError(R.string.ERR_EMPTY_PHONE_NO);
        } else if (user.getMobile().length() != 10) {
            iValidatorResult.onError(R.string.ERR_EMPTY_PHONE_NO);
        } else {
            iValidatorResult.onSuccess();
            return true;
        }
        return false;
    }
}
