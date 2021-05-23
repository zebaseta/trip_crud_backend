package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PassengerTest {
    @Test
    void newPassengerOk() {
        Passenger passenger = new Passenger("PASS1", "Jhon Doe", "jondoe@mail.com", LocalDate.now(), "XXX-DDF448");
        Assertions.assertDoesNotThrow(passenger::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifCodeIsNullThenCreationPassengerNotOk() {
        Passenger passenger = new Passenger(null, "Jhon Doe", "jondoe@mail.com", LocalDate.now(), "XXX-DDF448");
        Assertions.assertThrows(DomainException.class, passenger::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifNameIsNulThenCreationPassengerNotOk() {
        Passenger passenger = new Passenger("PASS1", null, "jondoe@mail.com", LocalDate.now(), "XXX-DDF448");
        Assertions.assertThrows(DomainException.class, passenger::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifEmailIsNulThenCreationPassengerNotOk() {
        Passenger passenger = new Passenger("PASS1", "Jhon Doe", null, LocalDate.now(), "XXX-DDF448");
        Assertions.assertThrows(DomainException.class, passenger::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifBirthIsNulThenCreationPassengerNotOk() {
        Passenger passenger = new Passenger("PASS1", "Jhon Doe", "jondoe@mail.com", null, "XXX-DDF448");
        Assertions.assertThrows(DomainException.class, passenger::throwErrorIfCreationIsNotOk);
    }

    @Test
    void ifPassportIsNulThenCreationPassengerNotOk() {
        Passenger passenger = new Passenger("PASS1", "Jhon Doe", "jondoe@mail.com", LocalDate.now(), null);
        Assertions.assertThrows(DomainException.class, passenger::throwErrorIfCreationIsNotOk);
    }


    @Test
    void ifAllParametersAreNullThenUpdatingPassengerNotOk() {
        Passenger passenger = new Passenger(null, null, null, null, null);
        Assertions.assertThrows(DomainException.class, passenger::throwErrorIfUpdatingIsNotOk);
    }

    @Test
    void ifOneParameterNotNullThenUpdatingPassengerIsOk() {
        Passenger passenger1 = new Passenger("Pass", null, null, null, null);
        Assertions.assertDoesNotThrow(passenger1::throwErrorIfUpdatingIsNotOk);
        Passenger passenger2 = new Passenger(null, "Jhon Doe", null, null, null);
        Assertions.assertDoesNotThrow(passenger2::throwErrorIfUpdatingIsNotOk);
        Passenger passenger3 = new Passenger(null, null, "jhondoe@mail.com", null, null);
        Assertions.assertDoesNotThrow(passenger3::throwErrorIfUpdatingIsNotOk);
        Passenger passenger4 = new Passenger(null, null, null, LocalDate.now(), null);
        Assertions.assertDoesNotThrow(passenger4::throwErrorIfUpdatingIsNotOk);
        Passenger passenger5 = new Passenger(null, null, null,null, "XADDF-DF555");
        Assertions.assertDoesNotThrow(passenger5::throwErrorIfUpdatingIsNotOk);
    }


}
