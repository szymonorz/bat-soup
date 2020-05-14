package app.services;

import app.model.CustomUserDetails;
import app.model.RegisterForm;
import app.model.User;

public interface CustomUserService {
    User registerNewAccount(RegisterForm rf)
            throws Exception;
}
