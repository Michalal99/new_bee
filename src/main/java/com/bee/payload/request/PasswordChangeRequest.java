package com.bee.payload.request;

import com.bee.security.constraint.ValidPassword;

public class PasswordChangeRequest {
    @ValidPassword
    private String password;

    private String password_confirm;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }
}
