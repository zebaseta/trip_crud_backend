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
    Airport airport = new Airport(null, "Aeropuerto de Carrasco");
    Assertions.assertThrows(DomainException.class, airport::throwErrorIfCreationIsNotOk);
}

@Test
void ifNameIsNulThenCreationAirportNotOk() {
    Airport airport = new Airport("A1", null);
    Assertions.assertThrows(DomainException.class, airport::throwErrorIfCreationIsNotOk);
}

@Test
void ifAllParametersAreNullThenUpdatingAirportNotOk() {
    Airport airport = new Airport(null, null);
    Assertions.assertThrows(DomainException.class, airport::throwErrorIfUpdatingIsNotOk);
}

@Test
void ifOneParameterNotNullThenUpdatingAirportIsOk() {
    Airport airport1 = new Airport("A1", null);
    Assertions.assertDoesNotThrow(airport1::throwErrorIfUpdatingIsNotOk);
    Airport airport2 = new Airport(null, "Aeropuerto de Carrasco");
    Assertions.assertDoesNotThrow(airport2::throwErrorIfUpdatingIsNotOk);
}


}
