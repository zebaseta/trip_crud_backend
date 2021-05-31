package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripOutModel {
    private Long id;
    private PassengerModel passenger;
    private ItineraryOutModel itinerary;

    public static TripOutModel buildFromEntity(Trip trip) {
        return new TripOutModel(trip.getId(),PassengerModel.buildFromEntity(trip.getPassenger()), ItineraryOutModel.buildFromEntity(trip.getItinerary()));
    }

}
