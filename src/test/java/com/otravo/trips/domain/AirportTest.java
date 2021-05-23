package com.otravo.trips.domain;
import com.otravo.trips.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

    public class AirportTest {
    @Test
    void newAirportOk() {
        Airport airport = new Airport("A1", "Aeropuerto de Carrasco");
        Assertions.assertDoesNotThrow(airport::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifCodeIsNullThenCreationAirportNotOk() {
        Airport Airport = new Airport(null, "Aeropuerto de Carrasco");
        Assertions.assertThrows(DomainException.class, Airport::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifNameIsNulThenCreationAirportNotOk() {
        Airport Airport = new Airport("A1", null);
        Assertions.assertThrows(DomainException.class, Airport::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifAllParametersAreNullThenUpdatingAirportNotOk() {
        Airport Airport = new Airport(null, null);
        Assertions.assertThrows(DomainException.class, Airport::throwErrorIfUpdatingIsNotOk);
    }

    @Test
    void ifOneParameterNotNullThenUpdatingAirportIsOk() {
        Airport Airport1 = new Airport("A1", null);
        Assertions.assertDoesNotThrow(Airport1::throwErrorIfUpdatingIsNotOk);
        Airport Airport2 = new Airport(null, "Aeropuerto de Carrasco");
        Assertions.assertDoesNotThrow(Airport2::throwErrorIfUpdatingIsNotOk);
    }


}
