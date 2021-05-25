package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "trips")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip implements CrudEntity<Trip> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "passenger_id", nullable = false)
  private Passenger passenger;

  @OneToOne
  @JoinColumn(nullable = false, unique = true, name = "itinerary_id")
  private Itinerary itinerary;

  public Trip(Passenger passenger, Itinerary itinerary) {
    this.passenger = passenger;
    this.itinerary = itinerary;
  }

  public Trip(Passenger passenger) {
    this.passenger = passenger;
  }

    @Override
  public void throwErrorIfCreationIsNotOk() throws DomainException {
    if (itinerary == null) throw new DomainException("Passenger cannot be null");
    if (passenger == null) throw new DomainException("Itinerary cannot be null");
  }

  @Override
  public void throwErrorIfUpdatingIsNotOk() throws DomainException {
    if (itinerary == null && passenger == null)
      throw new DomainException("No attributes were modified");
  }

  @Override
  public void updateFromEntity(Trip newData) {
    if (newData.getItinerary() != null) itinerary = newData.getItinerary();
    if (newData.getPassenger() != null) passenger = newData.getPassenger();
  }

  @Override
  public String toString() {
    return "Trip{" +
            "id=" + id +
            ", passenger=" + passenger +
            ", itinerary=" + itinerary +
            '}';
  }

  @Override
  public String getSystemIdInStringFormat() {
    return getId().toString();
  }

  public Airport getOutbundOriginAirport() {
    return itinerary.getOutbundOriginAirport();
  }

  public void setOutbundOriginAirport(Airport airport) {
    itinerary.setOutbundOriginAirport(airport);
  }

  public Airport getOutbundDestinationAirport() {
    return itinerary.getOutbundDestinationAirport();
  }

  public void setOutbundDestinationAirport(Airport airport) {
    itinerary.setOutbundDestinationAirport(airport);
  }

  public Airport getReturnOriginAirport() {
    return itinerary.getReturnOriginAirport();
  }

  public void setReturnOriginAirport(Airport airport) {
    itinerary.setReturnOriginAirport(airport);
  }

  public Airport getReturnDestinationAirport() {
    return itinerary.getReturnDestinationAirport();
  }

  public void setReturnDestinationAirport(Airport airport) {
    itinerary.setReturnDestinationAirport(airport);
  }

  public Airline getOutbundAirline() {
    return itinerary.getOutbundAirline();
  }

  public void setOutbundAirline(Airline outbundAirline) {
    itinerary.setOutbundAirline(outbundAirline);
  }

  public Airline getReturnAirline() {
    return itinerary.getReturnAirline();
  }

  public void setReturnAirline(Airline returnAirline) {
    itinerary.setReturnAirline(returnAirline);
  }

  public Flight getOutboundFlight() {
    return itinerary.getOutboundFlight();
  }

  public void setOutboundFlight(Flight outboundFligth) {
    itinerary.setOutboundFlight(outboundFligth);
  }

  public Flight getReturnFlight() {
    return itinerary.getReturnFlight();
  }

  public void setReturnFlight(Flight returnFligth) {
    itinerary.setReturnFlight(returnFligth);
  }
}
