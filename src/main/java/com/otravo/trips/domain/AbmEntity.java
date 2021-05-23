package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;

public interface AbmEntity<T> {
    void throwErrorIfCreationIsNotOk() throws DomainException;
    void throwErrorIfUpdatingIsNotOk() throws DomainException;
    void updateFromEntity(T entity);
    String getSystemIdInStringFormat();
}
