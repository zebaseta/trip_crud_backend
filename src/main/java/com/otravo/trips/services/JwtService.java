package com.otravo.trips.services;

import com.otravo.trips.exceptions.BusinessLogicException;

public interface JwtService {
    String createToken(String user, String pass) throws BusinessLogicException;

    String verifyTokenAndGetUser(String token) throws BusinessLogicException;
}
