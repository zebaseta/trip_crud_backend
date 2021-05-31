package com.otravo.trips.services;

import com.otravo.trips.domain.Flight;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RulesTripValidatorImpl implements RulesTripValidator {

    @Autowired
    private CrudServiceTemplate<Passenger, Long> passengerService;

    @Override
    public void validateTripOrThrowException(Trip trip) throws BusinessLogicException {
        validateDatesOriginAndDestinationOkOrThrowException(trip.getOutboundFligths());
        validateDatesOriginAndDestinationOkOrThrowException(trip.getReturnrFligths());
        validateOutboundFligthBeforeReturnFlightOrThrowException(trip.getOutboundFligths(), trip.getReturnrFligths());
        validateTripIsNotInterceptedWithOtherTripsOrThrowException(trip);
    }

    private void validateTripIsNotInterceptedWithOtherTripsOrThrowException(Trip trip) throws BusinessLogicException {
        //TO DO WITH REFACTOR
        /*List<Trip> olderTrips = trip.getPassenger().getTrips();
        Date new1 = trip.getOutboundFlight().getOriginDate();
        Date new2 = trip.getOutboundFlight().getDestinationDate();
        Date new3 = trip.getReturnFlight().getOriginDate();
        Date new4 = trip.getReturnFlight().getDestinationDate();
        for (Trip olderTrip : olderTrips) {
            Date old1 = olderTrip.getOutboundFlight().getOriginDate();
            Date old2 = olderTrip.getOutboundFlight().getDestinationDate();
            Date old3 = olderTrip.getReturnFlight().getOriginDate();
            Date old4 = olderTrip.getReturnFlight().getDestinationDate();
            boolean isValid1 = old4.before(new1);
            boolean isValid2 = new4.before(old1);
            boolean isValid3 = old2.before(new1) && new4.before(old3);
            boolean isValid4 = new2.before(old1) && old4.before(new3);
            boolean isValid =  isValid1 || isValid2 || isValid3 || isValid4;
            if(!isValid) throw new BusinessLogicException("The trip is intercepted with other user's trips");
        }*/
    }

    private void validateDatesOriginAndDestinationOkOrThrowException(List<Flight> flights) throws BusinessLogicException {
        for(Flight flight:flights){
            if (flight.getOriginDate().after(flight.getDestinationDate()))
                throw new BusinessLogicException("The flight from " + flight.getOriginAirport().getCode() + " airport "
                        + "to " + flight.getDestinationAirport().getCode() + " airport has arrival dates prior to the departure date");
        }
    }

    private void validateOutboundFligthBeforeReturnFlightOrThrowException(List<Flight> outboundFlights,
                                                                          List<Flight> returnFlights) throws BusinessLogicException {
        Flight outboundFlight = outboundFlights.get(0);
        Flight returnFlight = returnFlights.get(returnFlights.size()-1);
        if (outboundFlight.getDestinationDate().after(returnFlight.getOriginDate()))
            throw new BusinessLogicException("The trip has interception between outboundFlights and return flights");
    }


}
