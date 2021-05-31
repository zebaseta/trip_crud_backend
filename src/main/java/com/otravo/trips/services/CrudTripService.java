package com.otravo.trips.services;


import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.repositories.TripRepository;
import com.otravo.trips.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
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

  @Autowired
  private RulesTripValidator rulesTripValidator;

  private TripRepository tripRepository;

  public CrudTripService(TripRepository tripRepository) {
    super(tripRepository);
    this.tripRepository = tripRepository;
  }

  @Override
  public Trip create(Trip trip) throws BusinessLogicException, DomainException {
    addPassengerOrCreateIfExist(trip);
    addAirlinesAndAirportsFromBDOrThrowExceptionIfNotExits(trip.getOutboundFligths());
    addAirlinesAndAirportsFromBDOrThrowExceptionIfNotExits(trip.getReturnrFligths());
    rulesTripValidator.validateTripOrThrowException(trip);
    List<Flight> outboundFligthsBd = createFligths(trip.getOutboundFligths());
    trip.setOutboundFligths(outboundFligthsBd);
    List<Flight> returnsFligthsBd = createFligths(trip.getReturnrFligths());
    trip.setReturnFligths(returnsFligthsBd);
    Itinerary itineraryBD = itineraryService.create(trip.getItinerary());
    trip.setItinerary(itineraryBD);
    return super.create(trip);
  }

  private List<Flight> createFligths(List<Flight> flights) throws DomainException, BusinessLogicException {
    List<Flight> resultBD = new ArrayList<>();
    for(Flight flight:flights){
      Flight flightBD = flightService.create(flight);
      resultBD.add(flightBD);
    }
    return resultBD;
  }

  private Airport getAirportFromBdOrThrowExceptionIfNotExists(Airport airport) throws BusinessLogicException {
    List<Airport> aiportsBD =  airportService.findAll(Example.of(airport));
    if(aiportsBD==null || aiportsBD.isEmpty()) throw new BusinessLogicException("Airport with code "+airport.getCode()+" does not exists");
    else return aiportsBD.get(0);
  }

  private void addAirlinesAndAirportsFromBDOrThrowExceptionIfNotExits(List<Flight> flights) throws BusinessLogicException {
    for(Flight fligth:flights){
      Airline airline = getAirlineFromBdOrThowExceptionIfNotExist(fligth.getAirline());
      fligth.setAirline(airline);
      Airport originAirport = getAirportFromBdOrThrowExceptionIfNotExists(fligth.getOriginAirport());
      fligth.setOriginAirport(originAirport);
      Airport destinationAirport = getAirportFromBdOrThrowExceptionIfNotExists(fligth.getDestinationAirport());
      fligth.setDestinationAirport(destinationAirport);
    }
  }

  private Airline getAirlineFromBdOrThowExceptionIfNotExist(Airline airline) throws BusinessLogicException {
    List<Airline> airlines = airlineService.findAll(Example.of(airline));
    if(airlines==null|| airlines.isEmpty()) throw new BusinessLogicException("Airline with code "+ airline.getCode()+" does not exists");
    else return airlines.get(0);
  }

  private void addPassengerOrCreateIfExist(Trip trip) throws BusinessLogicException, DomainException {
    Passenger passenger = trip.getPassenger();
    List<Passenger> passengersBD = passengerService.findAll(Example.of(passenger));
    if(passengersBD == null || passengersBD.isEmpty()) {
      Passenger newPassengerBD = passengerService.create(passenger);
      trip.setPassenger(newPassengerBD);
    }
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

