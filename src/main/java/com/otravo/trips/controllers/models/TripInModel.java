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
    private List<FligthInModel> itinerary;

    public Itinerary createItinerary(int distanceDaysToReturn) {
        Collections.sort(itinerary);
        List<Flight> outboundFligths = new ArrayList<>();
        List<Flight> returnFligths = new ArrayList<>();
        boolean foundReturnFligth = false;
        boolean finishIterator = false;
        int iterator = 0;
        Flight previusFligth = null;
        while (!(foundReturnFligth || finishIterator)){
            Flight currentFlight = itinerary.get(iterator).toEntity();
            if(previusFligth !=null){
                int milisecondsByDay = 86400000;
                int days = (int) ((currentFlight.getOriginDate().getTime()-previusFligth.getDestinationDate().getTime()) / milisecondsByDay);
                if(days > distanceDaysToReturn){
                    foundReturnFligth = true;
                    returnFligths.add(currentFlight);
                }
                else{
                    outboundFligths.add(currentFlight);
                }
            }
            else{
                outboundFligths.add(currentFlight);
            }
            previusFligth = currentFlight;
            iterator++;
            if(iterator==itinerary.size()-1){
                finishIterator = true;
            }
        }
        for(int i=iterator;i<itinerary.size();i++){
            Flight flight = itinerary.get(i).toEntity();
            returnFligths.add(flight);
        }

        return new Itinerary(outboundFligths,returnFligths);
    }

    public Trip toEntity(String pattern, int distanceDaysToReturn) {
        Passenger passenger =  new Passenger(name,email, DateUtils.buildLocalDate(dateOfBirth, pattern), passport);
        return new Trip(passenger, createItinerary(distanceDaysToReturn));
    }
}
