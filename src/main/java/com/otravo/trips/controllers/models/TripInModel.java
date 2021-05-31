package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Flight;
import com.otravo.trips.domain.Itinerary;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripInModel {
    private String name;
    private String email;
    private String dateOfBirth;
    private String passport;
    private List<FligthModel> itinerary;

    public Itinerary createItinerary() {
        Collections.sort(itinerary);
        List<Flight> outboundFligths = new ArrayList<>();
        List<Flight> returnFligths = new ArrayList<>();
        for(int i = 0; i<(itinerary.size()/2); i++){
            outboundFligths.add(itinerary.get(i).toEntity());
        }
        for(int i = (itinerary.size()/2)+1; i< itinerary.size(); i++){
            returnFligths.add(itinerary.get(i).toEntity());
        }
        return new Itinerary(outboundFligths,returnFligths);
    }

    public Trip toEntity(String pattern) {
        Passenger passenger =  new Passenger(name,email, DateUtils.buildLocalDate(dateOfBirth, pattern), passport);
        return new Trip(passenger, createItinerary());
    }
}
