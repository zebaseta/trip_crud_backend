package com.otravo.trips.controllers.models;

import com.otravo.trips.domain.Itinerary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryModel {

    private FligthModel outboundFlight;
    private FligthModel returnFlight;

    public static ItineraryModel buildFromEntity(Itinerary itinerary) {
        return new ItineraryModel(FligthModel.buildFromEntity(itinerary.getOutboundFlight()),FligthModel.buildFromEntity(itinerary.getReturnFlight()));
    }

    public Itinerary toEntity() {
        return new Itinerary(outboundFlight.toEntity(),returnFlight.toEntity());
    }
}
