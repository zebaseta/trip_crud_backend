package com.otravo.trips.controllers;

import com.otravo.trips.controllers.api.AirportController;
import com.otravo.trips.controllers.models.AirportModel;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.services.CrudServiceTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AirportControllerTest {

    @Mock
    public CrudServiceTemplate<Airport, Long> abmService;
    @InjectMocks
    public AirportController airportController;

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
    public void init() {
        airports = Arrays.asList(airport1, airport2, airport3, airport4, airport5);
        Mockito.when(abmService.findAll()).thenReturn(airports);
    }

    @Test
    void findAllOk() {
        List<AirportModel> airports = airportController.findAll();
        Assertions.assertEquals(5, airports.size());
    }
}
