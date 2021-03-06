package com.otravo.trips.controllers;

import com.otravo.trips.controllers.api.AirlineController;
import com.otravo.trips.domain.Airline;
import com.otravo.trips.services.CrudServiceTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class AirlineControllerTest {

    @Mock
    public CrudServiceTemplate<Airline, Long> abmService;
    @InjectMocks
    public AirlineController airlineController;

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
    public void init() {
        airlines = Arrays.asList(airline1, airline2, airline3, airline4, airline5);
        Mockito.when(abmService.findAll()).thenReturn(airlines);
    }

   /* @Test
    void findAllOk() {
        List<AirlineModel> airlines = airlineController.findAll(null);
        Assertions.assertEquals(5, airlines.size());
    }*/
}
