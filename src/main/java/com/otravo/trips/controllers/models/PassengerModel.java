package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Passenger;
import com.otravo.trips.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerModel {
    private String code;
    private String name;
    private String email;
    private String dateOfBirth;
    private String passport;

    public static PassengerModel buildFromEntity(Passenger airport) {
        return new PassengerModel(airport.getCode(), airport.getName(), airport.getEmail(),airport.getDateOfBirth().toString(),airport.getPassport());
    }

    public Passenger toEntity(String pattern) throws DateTimeParseException {
        return new Passenger(code, name, email, DateUtils.buildLocalDate(dateOfBirth, pattern),passport);
    }


}

