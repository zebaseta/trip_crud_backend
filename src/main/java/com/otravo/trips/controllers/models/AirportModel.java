package com.otravo.trips.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.otravo.trips.domain.Airport;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportModel {
    private String code;
    private String name;

    public static AirportModel buildFromEntity(Airport airport) {
        return new AirportModel(airport.getCode(), airport.getName());
    }

    public Airport toEntity() {
        return new Airport(code, name);
    }

}
