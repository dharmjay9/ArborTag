package in.com.arbortag.validator;

/**
 * Created by Dell_PC on 5/27/2017.
 */

public interface IValidatorResult {
    void onSuccess();

    void onError(int s);
}
