package in.com.arbortag.validator;


public interface IValidator<T> {
    boolean validate(T t, IValidatorResult iValidatorResult);
}
