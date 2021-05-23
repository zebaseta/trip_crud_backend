package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Airline;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripModel {
    private String passengerCode;
    private ItineraryModel itinerary;

    public static TripModel buildFromEntity(Trip trip) {
        return new TripModel(trip.getPassenger().getCode(), ItineraryModel.buildFromEntity(trip.getItinerary()));
    }

    public Trip toEntity() {
        return new Trip(new Passenger(passengerCode), itinerary.toEntity());
    }

}
