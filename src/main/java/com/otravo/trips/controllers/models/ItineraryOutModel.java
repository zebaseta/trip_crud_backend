package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Itinerary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryOutModel {

    private List<FligthModel> outboundFlights;
    private List<FligthModel> returnFlights;

    public static ItineraryOutModel buildFromEntity(Itinerary itinerary) {
        return new ItineraryOutModel(itinerary.getOutboundFlights().stream().map(flight -> FligthModel.buildFromEntity(flight)).collect(Collectors.toList()),
                itinerary.getReturnFlights().stream().map(flight -> FligthModel.buildFromEntity(flight)).collect(Collectors.toList()));
    }

    public Itinerary toEntity() {
        return new Itinerary(outboundFlights.stream().map(f->f.toEntity()).collect(Collectors.toList()),
                returnFlights.stream().map(f->f.toEntity()).collect(Collectors.toList()));
    }
}
