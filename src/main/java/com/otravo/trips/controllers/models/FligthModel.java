package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Airline;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FligthModel implements Comparable {
    private String code;
    private Date originDate;
    private String originAirportCode;
    private Date destinationDate;
    private String destinationAirportCode;
    private String airlineCode;

    public static FligthModel buildFromEntity(Flight flight) {
        return new FligthModel(flight.getCode(), flight.getOriginDate(), flight.getOriginAirport().getCode(),
                flight.getDestinationDate(), flight.getDestinationAirport().getCode(), flight.getAirline().getCode());
    }

    public Flight toEntity() {
        return new Flight(code, originDate, new Airport(originAirportCode),
                destinationDate, new Airport(destinationAirportCode), new Airline(airlineCode));
    }

    @Override
    public int compareTo(Object o) {
        FligthModel fligthToCompare = (FligthModel) o;
        if(this.getDestinationDate().before(fligthToCompare.getOriginDate())) return -1;
        else if (fligthToCompare.getDestinationDate().before(this.getOriginDate())) return 1;
        return 0;
    }
}
