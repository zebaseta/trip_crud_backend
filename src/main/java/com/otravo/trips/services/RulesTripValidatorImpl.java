package com.otravo.trips.services;
import com.otravo.trips.domain.Flight;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesTripValidatorImpl implements RulesTripValidator{

    @Autowired
    private CrudServiceTemplate<Passenger, Long> passengerService;

    @Override
    public void validateTripOrThrowException(Trip trip) throws BusinessLogicException{
        validateDatesOriginAndDestinyOkOrThrowException(trip.getOutboundFlight());
        validateDatesOriginAndDestinyOkOrThrowException(trip.getReturnFlight());
        validateOutboundFligthBeforeReturnFlightOrThrowException(trip.getOutboundFlight(),trip.getReturnFlight());
    }

    private void validateDatesOriginAndDestinyOkOrThrowException(Flight flight) throws BusinessLogicException {
        if(flight.getOriginDate().after(flight.getDestinationDate()))
            throw new BusinessLogicException("The flight from "+flight.getOriginAirport().getCode()+" airport "
                    + "to "+flight.getDestinationAirport().getCode()+" airport has arrival dates prior to the departure date");
    }

    private void validateOutboundFligthBeforeReturnFlightOrThrowException(Flight outboundFlight, Flight returnFlight) throws BusinessLogicException {
        if(outboundFlight.getDestinationDate().after(returnFlight.getOriginDate()))
            throw new BusinessLogicException("The trip has interception between outbounFlight and return flight");
    }


}
