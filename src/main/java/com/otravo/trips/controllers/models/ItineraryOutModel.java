package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Flight;
import com.otravo.trips.domain.Itinerary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryOutModel {

    private List<FligthOutModel> outboundFlights;
    private List<FligthOutModel> returnFlights;

    public static ItineraryOutModel buildFromEntity(Itinerary itinerary, SimpleDateFormat simpleDateFormat) {
        Collections.sort(itinerary.getOutboundFlights());
        Collections.sort(itinerary.getReturnFlights());
        return new ItineraryOutModel(itinerary.getOutboundFlights().stream().map(flight -> FligthOutModel.buildFromEntity(flight,simpleDateFormat)).collect(Collectors.toList()),
                itinerary.getReturnFlights().stream().map(flight -> FligthOutModel.buildFromEntity(flight,simpleDateFormat)).collect(Collectors.toList()));
    }

}
