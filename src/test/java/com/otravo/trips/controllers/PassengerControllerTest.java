package com.otravo.trips.controllers;

import com.otravo.trips.controllers.api.PassengerController;
import com.otravo.trips.controllers.models.PassengerModel;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.services.CrudServiceTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PassengerControllerTest {

    @Mock
    public CrudServiceTemplate<Passenger, Long> abmService;
    @InjectMocks
    public PassengerController passengerController;

    private List<Passenger> passengers;
    private Passenger passenger1 = new Passenger("PASS1", "Jhon Doe`1", "jondoe1@mail.com", LocalDate.now(), "XXX-DDF441");
    private Passenger passenger2 = new Passenger("PASS2", "Jhon Doe2", "jondoe2@mail.com", LocalDate.now(), "XXX-DDF442");
    private Passenger passenger3 = new Passenger("PASS3", "Jhon Doe3", "jondoe3@mail.com", LocalDate.now(), "XXX-DDF443");
    private Passenger passenger4 = new Passenger("PASS4", "Jhon Doe4", "jondoe4@mail.com", LocalDate.now(), "XXX-DDF444");
    private Passenger passenger5 = new Passenger("PASS4", "Jhon Doe5", "jondoe5@mail.com", LocalDate.now(), "XXX-DDF445");
    private Passenger passengerBdCreated;
    private Passenger passengerBdUpdated;
    private String nameChanged = "passenger name changed";


    @BeforeEach
    public void init() {
        passengers = Arrays.asList(passenger1, passenger2, passenger3, passenger4, passenger5);
        Mockito.when(abmService.findAll()).thenReturn(passengers);
    }

    @Test
    void findAllOk() {
        List<PassengerModel> passengers = passengerController.findAll();
        Assertions.assertEquals(5, passengers.size());
    }
}
