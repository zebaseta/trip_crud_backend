package com.otravo.trips.services;
import org.springframework.stereotype.Service;


public interface UserService {
    boolean validateUserLogin(String email, String password);
}
