package com.otravo.trips.services;

import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;

public interface RulesTripValidator {
    void validateTripOrThrowException(Trip trip) throws BusinessLogicException;
}
