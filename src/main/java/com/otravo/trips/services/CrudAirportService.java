package com.otravo.trips.services;

import com.otravo.trips.repositories.AirportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class CrudAirportService extends CrudServiceTemplate<Airport, Long> {

    private AirportRepository airportRepository;
    public CrudAirportService(AirportRepository airportRepository) {
        super(airportRepository);
        this.airportRepository = airportRepository;
    }

    @Override
    @CacheEvict(value = "airports", allEntries = true)
    public Airport create(Airport entityToCreate) throws BusinessLogicException, DomainException {
        return super.create(entityToCreate);
    }

    @Override
    @CacheEvict(value = "airports", allEntries = true)
    public Optional<Airport> findInBDBySystemId(Airport airport) {
        if (airport != null && airport.getCode() != null)
            return airportRepository.findByCode(airport.getCode());
        else return Optional.empty();
    }

    @Override
    @Cacheable("airports")
    public List<Airport> findAll(Example<Airport> airport) {
        return airportRepository.findAll(airport);
    }


    @Override
    @CacheEvict(value = "airports", allEntries = true)
    public Airport update(Airport entityDataToUpdate) throws BusinessLogicException, DomainException {
        return super.update(entityDataToUpdate);
    }

    @Override
    @Cacheable("airports")
    public List<Airport> findAll() {
        return super.findAll();
    }

}

