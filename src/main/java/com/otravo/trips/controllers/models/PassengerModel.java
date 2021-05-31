package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Passenger;
import com.otravo.trips.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeParseException;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerModel {
    private String name;
    private String email;
    private String dateOfBirth;
    private String passport;

    public static PassengerModel buildFromEntity(Passenger airport) {
        return new PassengerModel(airport.getName(), airport.getEmail(),airport.getDateOfBirth().toString(),airport.getPassport());
    }

    public Passenger toEntity(String pattern) throws DateTimeParseException {
        return new Passenger(name, email, DateUtils.buildLocalDate(dateOfBirth, pattern),passport);
    }


}

