package com.otravo.trips.services;

import com.otravo.trips.domain.Airline;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.ServiceException;
import com.otravo.trips.repositories.AirlineRepository;
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
public class CrudAirlineService extends CrudServiceTemplate<Airline, Long> {
    public CrudAirlineService(AirlineRepository airlineRepository) {
        super(airlineRepository);
    }

    @Override
    @CacheEvict(value = "airlines", allEntries = true)
    public Airline create(Airline entityToCreate) throws ServiceException, DomainException {
        return super.create(entityToCreate);
    }

    @Override
    protected Optional<Airline> findInBDBySystemId(Airline airline) {
        if (airline != null && airline.getCode() != null)
            return ((AirlineRepository) repository).findByCode(airline.getCode());
        else return Optional.empty();
    }

    @Override
    @CacheEvict(value = "airlines", allEntries = true)
    public Airline update(Airline entityDataToUpdate) throws ServiceException, DomainException {
        return super.update(entityDataToUpdate);
    }

    @Override
    @Cacheable("airlines")
    public List<Airline> findAll() {
        return super.findAll();
    }

}

