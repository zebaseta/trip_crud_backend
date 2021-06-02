package com.otravo.trips.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Value("${admin.user}")
    private String user;

    @Value("${admin.pass}")
    private String pass;

    @Override
    public boolean validateUserLogin(String email, String password) {
        return email!=null && email.equals(user) && password!= null && password.equals(pass);
    }
}
