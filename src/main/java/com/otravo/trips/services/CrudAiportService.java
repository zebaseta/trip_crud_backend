package com.otravo.trips.services;

import com.otravo.trips.repositories.AirportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class CrudAiportService extends CrudServiceTemplate<Airport, Long> {
    public CrudAiportService(AirportRepository airportRepository) {
        super(airportRepository);
    }

    @Override
    @CacheEvict(value = "airports", allEntries = true)
    public Airport create(Airport entityToCreate) throws ServiceException, DomainException {
        return super.create(entityToCreate);
    }

    @Override
    protected Optional<Airport> findInBDBySystemId(Airport airport) {
        if (airport != null && airport.getCode() != null)
            return ((AirportRepository) repository).findByCode(airport.getCode());
        else return Optional.empty();
    }

    @Override
    @CacheEvict(value = "airports", allEntries = true)
    public Airport update(Airport entityDataToUpdate) throws ServiceException, DomainException {
        return super.update(entityDataToUpdate);
    }

    @Override
    @Cacheable("airports")
    public List<Airport> findAll() {
        return super.findAll();
    }

}

