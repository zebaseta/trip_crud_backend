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
    public AirportController aiportController;

    private List<Airport> aiports;
    private Airport aiport1 = new Airport("A1", "aiport1");
    private Airport aiport2 = new Airport("A2", "aiport2");
    private Airport aiport3 = new Airport("A3", "aiport3");
    private Airport aiport4 = new Airport("A4", "aiport4");
    private Airport aiport5 = new Airport("A5", "aiport5");
    private Airport aiportBdCreated;
    private Airport aiportBdUpdated;
    private String nameChanged = "aiport name changed";


    @BeforeEach
    public void init() {
        aiports = Arrays.asList(aiport1, aiport2, aiport3, aiport4, aiport5);
        Mockito.when(abmService.findAll()).thenReturn(aiports);
    }

    @Test
    void findAllOk() {
        List<AirportModel> aiports = aiportController.findAll();
        Assertions.assertEquals(5, aiports.size());
    }
}
