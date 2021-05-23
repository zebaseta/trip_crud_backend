package com.otravo.trips.services;

import com.otravo.trips.domain.Airline;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.repositories.AirlineRepository;
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
public class CrudAirlineServiceTest {
    @Mock
    private AirlineRepository repository;

    @InjectMocks
    private CrudAirlineService crudService;

    private List<Airline> airlines;
    private Airline airline1 = new Airline("A1", "airline1");
    private Airline airline2 = new Airline("A2", "airline2");
    private Airline airline3 = new Airline("A3", "airline3");
    private Airline airline4 = new Airline("A4", "airline4");
    private Airline airline5 = new Airline("A5", "airline5");
    private Airline airlineBdCreated;
    private Airline airlineBdUpdated;
    private String nameChanged = "airline name changed";

    @BeforeEach
    void setUp() {
        airlines = Arrays.asList(airline1, airline2, airline3, airline4, airline5);
        airlineBdCreated = new Airline();
        airlineBdCreated.updateFromEntity(airline1);
        airlineBdCreated.setId(1L);
        airlineBdUpdated = new Airline();
        airlineBdUpdated.updateFromEntity(airline1);
        airlineBdUpdated.setId(1L);
        airlineBdUpdated.setName(nameChanged);
    }

    @Test
    void notFindAnyAirlineOk() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        List<Airline> airlines = crudService.findAll();
        Assertions.assertEquals(airlines.size(), 0);
    }

    @Test
    void findAllAirlinesOk() {
        when(repository.findAll()).thenReturn(airlines);
        List<Airline> airlines = crudService.findAll();
        Assertions.assertEquals(airlines.size(), 5);
    }

    @Test
    void findOneAirlineByExample() {
        when(repository.findAll(Example.of(airline1))).thenReturn(Collections.singletonList(airline1));
        Example<Airline> example = Example.of(airline1);
        List<Airline> airlinesWithExample = crudService.findAll(example);
        Assertions.assertEquals(airlinesWithExample.size(), 1);
        Assertions.assertEquals(airlinesWithExample.get(0).getName(), airline1.getName());
    }

    @Test
    void createAirlineOk() {
        when(repository.saveAndFlush(airline1)).thenReturn(airlineBdCreated);
        Assertions.assertDoesNotThrow(() -> {
            Airline airlineBD = crudService.create(airline1);
            Assertions.assertEquals(airlineBD.getName(), airline1.getName());
            Assertions.assertEquals(airlineBD.getId(), 1L);
        });
    }

    @Test
    void ifNameIsNullcreateThenAirlineIsNotOk() {
        airline1.setName(null);
        Assertions.assertThrows(DomainException.class, () -> crudService.create(airline1));
    }

    @Test
    void ifEmailIsNullcreateThenAirlineIsNotOk() {
        airline1.setName(null);
        Assertions.assertThrows(DomainException.class, () -> crudService.create(airline1));
    }

    @Test
    void updateAirlineOk() {
        airline1.setName(nameChanged);
        when(repository.save(airlineBdUpdated)).thenReturn(airlineBdUpdated);
        when(repository.findByCode("A1")).thenReturn(Optional.of(airlineBdUpdated));
        Assertions.assertDoesNotThrow(() -> {
            Airline airlineBDUpdated = crudService.update(airline1);
            Assertions.assertEquals(airlineBDUpdated.getName(), nameChanged);
            Assertions.assertEquals(airlineBDUpdated.getId(), 1L);
        });
    }

    @Test
    void ifDataAirlineIsNullThenUpdateAirlineIsNotOk() {
        Airline airline = new Airline();
        Assertions.assertThrows(DomainException.class, () -> crudService.update(airline));
    }

}

