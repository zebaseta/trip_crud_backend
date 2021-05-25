package com.otravo.trips.services;

import com.otravo.trips.domain.Airline;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.repositories.AirlineRepository;
import com.otravo.trips.repositories.AirportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class CrudAirlineService extends CrudServiceTemplate<Airline, Long> {
    private AirlineRepository airlineRepository;

    public CrudAirlineService(AirlineRepository airlineRepository) {
        super(airlineRepository);
        this.airlineRepository = airlineRepository;
    }

    @Override
    @CacheEvict(value = "airlines", allEntries = true)
    public Airline create(Airline entityToCreate) throws BusinessLogicException, DomainException {
        return super.create(entityToCreate);
    }

    @Override
    @Cacheable("airlines")
    public List<Airline> findAll(Example<Airline> airline) {
        return airlineRepository.findAll(airline);
    }

    @Override
    protected Optional<Airline> findInBDBySystemId(Airline airline) {
        if (airline != null && airline.getCode() != null)
            return airlineRepository.findByCode(airline.getCode());
        else return Optional.empty();
    }



    @Override
    @CacheEvict(value = "airlines", allEntries = true)
    public Airline update(Airline entityDataToUpdate) throws BusinessLogicException, DomainException {
        return super.update(entityDataToUpdate);
    }

    @Override
    @Cacheable("airlines")
    public List<Airline> findAll() {
        return super.findAll();
    }

}

