package com.otravo.trips.services;

import com.otravo.trips.domain.Passenger;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.repositories.PassengerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrudPassengerServiceTest {
    @Mock
    private PassengerRepository repository;

    @InjectMocks
    private CrudPassengerService crudService;

    private List<Passenger> passengers;
    private Passenger passenger1  = new Passenger("PASS1", "Jhon Doe", "jondoe@mail.com", LocalDate.now(), "XXX1-DDF441");
    private Passenger passenger2  = new Passenger("PASS2", "Juan Doe", "jondoe@mail.com", LocalDate.now(), "XXX2-DDF442");
    private Passenger passenger3  = new Passenger("PASS3", "Jhon Don", "jondoe@mail.com", LocalDate.now(), "XXX3-DDF443");
    private Passenger passenger4  = new Passenger("PASS4", "Jhon Jhon", "jondoe@mail.com", LocalDate.now(), "XXX4-DDF444");
    private Passenger passenger5  = new Passenger("PASS5", "Doe Doe", "jondoe@mail.com", LocalDate.now(), "XXX5-DDF445");
    private Passenger passengerBdCreated;
    private Passenger passengerBdUpdated;
    private String nameChanged = "passenger name changed";

    @BeforeEach
    void setUp() {
        passengers = Arrays.asList(passenger1, passenger2, passenger3, passenger4, passenger5);
        passengerBdCreated = new Passenger();
        passengerBdCreated.setId(1l);
        passengerBdCreated.updateFromEntity(passenger1);
        passengerBdUpdated = new Passenger();
        passengerBdUpdated.setId(2l);
        passengerBdUpdated.updateFromEntity(passenger1);
        passengerBdUpdated.setName(nameChanged);
    }

    @Test
    void notFindAnyPassengerOk() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        List<Passenger> passengers = crudService.findAll();
        Assertions.assertEquals(passengers.size(), 0);
    }

    @Test
    void findAllPassengersOk() {
        when(repository.findAll()).thenReturn(passengers);
        List<Passenger> passengers = crudService.findAll();
        Assertions.assertEquals(passengers.size(), 5);
    }

    @Test
    void findOnePassengerByExample() {
        when(repository.findAll(Example.of(passenger1))).thenReturn(Collections.singletonList(passenger1));
        Example<Passenger> example = Example.of(passenger1);
        List<Passenger> passengersWithExample = crudService.findAll(example);
        Assertions.assertEquals(passengersWithExample.size(), 1);
        Assertions.assertEquals(passengersWithExample.get(0).getName(), passenger1.getName());
    }

    @Test
    void createPassengerOk() {
        when(repository.saveAndFlush(passenger1)).thenReturn(passengerBdCreated);
        Assertions.assertDoesNotThrow(() -> {
            Passenger passengerBD = crudService.create(passenger1);
            Assertions.assertEquals(passengerBD.getName(), passenger1.getName());
            Assertions.assertEquals(passengerBD.getCode(), "PASS1");
            Assertions.assertEquals(passengerBD.getId(),1l);
        });
    }

    @Test
    void ifNameIsNullcreateThenPassengerIsNotOk() {
        passenger1.setName(null);
        Assertions.assertThrows(DomainException.class, () -> crudService.create(passenger1));
    }

    @Test
    void ifEmailIsNullcreateThenPassengerIsNotOk() {
        passenger1.setName(null);
        Assertions.assertThrows(DomainException.class, () -> crudService.create(passenger1));
    }

    @Test
    void updatePassengerOk() {
        passenger1.setName(nameChanged);
        when(repository.save(passengerBdUpdated)).thenReturn(passengerBdUpdated);
        when(repository.findByCode("PASS1")).thenReturn(Optional.of(passengerBdUpdated));
        Assertions.assertDoesNotThrow(() -> {
            Passenger passengerBDUpdated = crudService.update(passenger1);
            Assertions.assertEquals(passengerBDUpdated.getName(), nameChanged);
            Assertions.assertEquals(passengerBDUpdated.getCode(), "PASS1");
            Assertions.assertEquals(passengerBDUpdated.getId(), 2l);
        });
    }

    @Test
    void ifDataPassengerIsNullThenUpdatePassengerIsNotOk() {
        Passenger passenger = new Passenger();
        Assertions.assertThrows(DomainException.class, () -> crudService.update(passenger));
    }

}

