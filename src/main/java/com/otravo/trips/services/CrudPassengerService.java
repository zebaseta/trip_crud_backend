package com.otravo.trips.services;

import com.otravo.trips.domain.Passenger;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.ServiceException;
import com.otravo.trips.repositories.PassengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class CrudPassengerService extends CrudServiceTemplate<Passenger, Long> {
    public CrudPassengerService(PassengerRepository passengerRepository) {
        super(passengerRepository);
    }

    @Override
    protected Optional<Passenger> findInBDBySystemId(Passenger passenger) {
        if (passenger != null && passenger.getCode() != null)
            return ((PassengerRepository) repository).findByCode(passenger.getCode());
        else return Optional.empty();
    }

}

