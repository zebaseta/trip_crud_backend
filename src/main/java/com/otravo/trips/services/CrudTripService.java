package com.otravo.trips.services;

import com.otravo.trips.domain.*;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.repositories.TripRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CrudTripService extends CrudServiceTemplate<Trip, Long> {

  @Autowired
  private CrudServiceTemplate<Passenger,Long> passengerService;

  @Autowired
  private CrudServiceTemplate<Airline,Long> airlineService;

  @Autowired
  private CrudServiceTemplate<Airport,Long> airportService;

  @Autowired
  private CrudServiceTemplate<Flight,Long> flightService;

  @Autowired
  private CrudServiceTemplate<Itinerary,Long> itineraryService;

  private TripRepository tripRepository;

  public CrudTripService(TripRepository tripRepository) {
    super(tripRepository);
    this.tripRepository = tripRepository;
  }

  @Override
  public Trip create(Trip trip) throws BusinessLogicException, DomainException {
    addPassengerFromBdOrThrowExceptionIfNotExist(trip);
    addAirlinesFromBDOrThrowExceptionIfNotExits(trip);
    addAirportsFromBdOrThrowExceptionIfNotExists(trip);
    Flight outboundFligthBD = flightService.create(trip.getOutboundFlight());
    trip.setOutboundFlight(outboundFligthBD);
    Flight returnFligthBD = flightService.create(trip.getReturnFlight());
    trip.setReturnFlight(returnFligthBD);
    Itinerary itineraryBD = itineraryService.create(trip.getItinerary());
    trip.setItinerary(itineraryBD);
    return super.create(trip);
  }

  private void addAirportsFromBdOrThrowExceptionIfNotExists(Trip trip) throws BusinessLogicException {
    Airport outbundOriginAirport = getAirportFromBdOrThrowExceptionIfNotExists(trip.getOutbundOriginAirport());
    trip.setOutbundOriginAirport(outbundOriginAirport);

    Airport outbundDestinationAirport = getAirportFromBdOrThrowExceptionIfNotExists(trip.getOutbundDestinationAirport());
    trip.setOutbundDestinationAirport(outbundDestinationAirport);

    Airport returnOriginAirport = getAirportFromBdOrThrowExceptionIfNotExists(trip.getReturnOriginAirport());
    trip.setReturnOriginAirport(returnOriginAirport);

    Airport returnDestinationAirport = getAirportFromBdOrThrowExceptionIfNotExists(trip.getReturnDestinationAirport());
    trip.setReturnDestinationAirport(returnDestinationAirport);
  }

  private Airport getAirportFromBdOrThrowExceptionIfNotExists(Airport airport) throws BusinessLogicException {
    List<Airport> aiportsBD =  airportService.findAll(Example.of(airport));
    if(aiportsBD==null || aiportsBD.isEmpty()) throw new BusinessLogicException("Airport with code "+airport.getCode()+" does not exists");
    else return aiportsBD.get(0);
  }

  private void addAirlinesFromBDOrThrowExceptionIfNotExits(Trip trip) throws BusinessLogicException {
    Airline outbundAirlineBD = getAirlineFromBdOrThowExceptionIfNotExist(trip.getOutbundAirline());
    trip.setOutbundAirline(outbundAirlineBD);
    Airline returnAirlineBD = getAirlineFromBdOrThowExceptionIfNotExist(trip.getReturnAirline());
    trip.setReturnAirline(returnAirlineBD);
  }

  private Airline getAirlineFromBdOrThowExceptionIfNotExist(Airline airline) throws BusinessLogicException {
    List<Airline> airlines = airlineService.findAll(Example.of(airline));
    if(airlines==null|| airlines.isEmpty()) throw new BusinessLogicException("Airline with code "+ airline.getCode()+" does not exists");
    else return airlines.get(0);
  }

  private void addPassengerFromBdOrThrowExceptionIfNotExist(Trip trip) throws BusinessLogicException {
    Passenger passenger = trip.getPassenger();
    List<Passenger> passengersBD = passengerService.findAll(Example.of(passenger));
    if(passengersBD == null || passengersBD.isEmpty()) throw new BusinessLogicException("The passenger does not exists");
    else {
      trip.setPassenger(passengersBD.get(0));
    }
  }

  @Override
  protected Optional<Trip> findInBDBySystemId(Trip trip) {
    if (trip != null && trip.getId() != null)
      return tripRepository.findById(trip.getId());
    else return Optional.empty();
  }
}

