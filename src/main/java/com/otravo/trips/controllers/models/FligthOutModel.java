package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Airline;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FligthOutModel {
    private String code;
    private String originDate;
    private String originAirport;
    private String destinationDate;
    private String destinationAirport;
    private String airline;


    public static FligthOutModel buildFromEntity(Flight flight, SimpleDateFormat simpleDateFormat) {

        return new FligthOutModel(flight.getCode(), simpleDateFormat.format(flight.getOriginDate()), flight.getOriginAirport().getName(),
                simpleDateFormat.format(flight.getDestinationDate()), flight.getDestinationAirport().getName(), flight.getAirline().getName());
    }

}
