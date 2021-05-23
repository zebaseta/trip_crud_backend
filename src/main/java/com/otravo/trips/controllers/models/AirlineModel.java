package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Airline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirlineModel {
    private String code;
    private String name;

    public static AirlineModel buildFromEntity(Airline airline) {
        return new AirlineModel(airline.getCode(), airline.getName());
    }

    public Airline toEntity() {
        return new Airline(code, name);
    }

}
