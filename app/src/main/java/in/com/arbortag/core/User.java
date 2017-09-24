package in.com.arbortag.core;

import in.com.arbortag.IBaseModel;

/**
 * Created by Dell_PC on 5/27/2017.
 */

public class User implements IBaseModel{

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private String confirm_password;
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirm_password;
    }

    public void setConfirmPassword(String confirm_password) {
        this.confirm_password = confirm_password;
    }


}
