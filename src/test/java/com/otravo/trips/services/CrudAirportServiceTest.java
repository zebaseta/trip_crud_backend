package com.otravo.trips.services;

import com.otravo.trips.domain.Airport;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.repositories.AirportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrudAirportServiceTest {
    @Mock
    private AirportRepository repository;

    @InjectMocks
    private CrudAiportService crudService;

    private List<Airport> airports;
    private Airport airport1 = new Airport("A1", "airport1");
    private Airport airport2 = new Airport("A2", "airport2");
    private Airport airport3 = new Airport("A3", "airport3");
    private Airport airport4 = new Airport("A4", "airport4");
    private Airport airport5 = new Airport("A5", "airport5");
    private Airport airportBdCreated;
    private Airport airportBdUpdated;
    private String nameChanged = "airport name changed";

    @BeforeEach
    void setUp() {
        airports = Arrays.asList(airport1, airport2, airport3, airport4, airport5);
        airportBdCreated = new Airport();
        airportBdCreated.updateFromEntity(airport1);
        airportBdCreated.setId(1L);
        airportBdUpdated = new Airport();
        airportBdUpdated.updateFromEntity(airport1);
        airportBdUpdated.setId(1L);
        airportBdUpdated.setName(nameChanged);
    }

    @Test
    void notFindAnyAirportOk() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        List<Airport> airports = crudService.findAll();
        Assertions.assertEquals(airports.size(), 0);
    }

    @Test
    void findAllAirportsOk() {
        when(repository.findAll()).thenReturn(airports);
        List<Airport> airports = crudService.findAll();
        Assertions.assertEquals(airports.size(), 5);
    }

    @Test
    void findOneAirportByExample() {
        when(repository.findAll(Example.of(airport1))).thenReturn(Collections.singletonList(airport1));
        Example<Airport> example = Example.of(airport1);
        List<Airport> airportsWithExample = crudService.findAll(example);
        Assertions.assertEquals(airportsWithExample.size(), 1);
        Assertions.assertEquals(airportsWithExample.get(0).getName(), airport1.getName());
    }

    @Test
    void createAirportOk() {
        when(repository.saveAndFlush(airport1)).thenReturn(airportBdCreated);
        Assertions.assertDoesNotThrow(() -> {
            Airport airportBD = crudService.create(airport1);
            Assertions.assertEquals(airportBD.getName(), airport1.getName());
            Assertions.assertEquals(airportBD.getId(), 1L);
        });
    }

    @Test
    void ifNameIsNullcreateThenAirportIsNotOk() {
        airport1.setName(null);
        Assertions.assertThrows(DomainException.class, () -> crudService.create(airport1));
    }

    @Test
    void ifEmailIsNullcreateThenAirportIsNotOk() {
        airport1.setName(null);
        Assertions.assertThrows(DomainException.class, () -> crudService.create(airport1));
    }

    @Test
    void updateAirportOk() {
        airport1.setName(nameChanged);
        when(repository.save(airportBdUpdated)).thenReturn(airportBdUpdated);
        when(repository.findByCode("A1")).thenReturn(Optional.of(airportBdUpdated));
        Assertions.assertDoesNotThrow(() -> {
            Airport airportBDUpdated = crudService.update(airport1);
            Assertions.assertEquals(airportBDUpdated.getName(), nameChanged);
            Assertions.assertEquals(airportBDUpdated.getId(), 1L);
        });
    }

    @Test
    void ifDataAirportIsNullThenUpdateAirportIsNotOk() {
        Airport airport = new Airport();
        Assertions.assertThrows(DomainException.class, () -> crudService.update(airport));
    }

}

