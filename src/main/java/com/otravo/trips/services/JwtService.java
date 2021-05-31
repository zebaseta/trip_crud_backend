package com.otravo.trips.services;

import com.otravo.trips.exceptions.BusinessLogicException;

public interface JwtService {
    String createToken(String id, String name, String email) throws BusinessLogicException;

    String verifyTokenAndGetUser(String token) throws BusinessLogicException;
}
