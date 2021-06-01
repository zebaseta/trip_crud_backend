package com.otravo.trips.controllers.models;


import com.otravo.trips.domain.Flight;
import com.otravo.trips.domain.Itinerary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryInModel {
    private List<FligthInModel> fligths;

    public Itinerary toEntity() {
        Collections.sort(fligths);
        List<Flight> outboundFligths = new ArrayList<>();
        List<Flight> returnFligths = new ArrayList<>();
        for(int i=0;i<(fligths.size()/2);i++){
            outboundFligths.add(fligths.get(i).toEntity());
        }
        for(int i=(fligths.size()/2)+1;i<fligths.size();i++){
            returnFligths.add(fligths.get(i).toEntity());
        }
        return new Itinerary(outboundFligths,returnFligths);
    }

}
