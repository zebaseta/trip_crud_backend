package com.otravo.trips.domain;
import com.otravo.trips.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AirlineTest {
@Test
void newAirlineOk() {
    Airline airline = new Airline("AIR1", "Flys Time");
    Assertions.assertDoesNotThrow(airline::throwErrorIfCreationIsNotOk);
}

@Test
void ifCodeIsNullThenCreationAirlineNotOk() {
    Airline airline = new Airline(null, "Super Flys");
    Assertions.assertThrows(DomainException.class, airline::throwErrorIfCreationIsNotOk);
}

@Test
void ifNameIsNulThenCreationAirlineNotOk() {
    Airline airline = new Airline("AIR1", null);
    Assertions.assertThrows(DomainException.class, airline::throwErrorIfCreationIsNotOk);
}

@Test
void ifAllParametersAreNullThenUpdatingAirlineNotOk() {
    Airline airline = new Airline(null, null);
    Assertions.assertThrows(DomainException.class, airline::throwErrorIfUpdatingIsNotOk);
}

@Test
void ifOneParameterNotNullThenUpdatingAirlineIsOk() {
    Airline airline1 = new Airline("AIR1", null);
    Assertions.assertDoesNotThrow(airline1::throwErrorIfUpdatingIsNotOk);
    Airline airline2 = new Airline(null, "Flys Time");
    Assertions.assertDoesNotThrow(airline2::throwErrorIfUpdatingIsNotOk);
}


}
