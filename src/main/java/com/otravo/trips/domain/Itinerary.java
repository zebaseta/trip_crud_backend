package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "itineraries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Itinerary implements CrudEntity<Itinerary> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "outbound_fligth_id", referencedColumnName = "id")
  private Flight outboundFlight;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "return_fligth_id", referencedColumnName = "id")
  private Flight returnFlight;

  public Itinerary(Flight outboundFlight, Flight returnFlight) {
    this.outboundFlight = outboundFlight;
    this.returnFlight = returnFlight;
  }

  @Override
  public void throwErrorIfCreationIsNotOk() throws DomainException {
    if (outboundFlight == null) throw new DomainException("Outbound fligth cannot be null");
    if (returnFlight == null) throw new DomainException("Return fligth cannot be null");
  }

  @Override
  public void throwErrorIfUpdatingIsNotOk() throws DomainException {
    if (outboundFlight == null && returnFlight == null)
      throw new DomainException("No attributes were modified");
    outboundFlight.throwErrorIfUpdatingIsNotOk();
    returnFlight.throwErrorIfUpdatingIsNotOk();
  }

  @Override
  public void updateFromEntity(Itinerary newData) {
    if (newData.getReturnFlight() != null) returnFlight = newData.getReturnFlight();
    if (newData.getOutboundFlight() != null) outboundFlight = newData.getOutboundFlight();
  }

  @Override
  public String toString() {
    return "Itinerary{" +
            "id=" + id +
            ", outboundFlight=" + outboundFlight.getAirline() +
            ", returnFlight=" + returnFlight.getCode() +
            '}';
  }

  @Override
  public String getSystemIdInStringFormat() {
    return getId().toString();
  }

  public Airport getOutbundOriginAirport() {
    return outboundFlight.getOriginAirport();
  }

  public Airport getOutbundDestinationAirport() {
    return outboundFlight.getDestinationAirport();
  }

  public Airport getReturnOriginAirport() {
    return returnFlight.getOriginAirport();
  }

  public Airport getReturnDestinationAirport() {
    return returnFlight.getDestinationAirport();
  }

  public Airline getOutbundAirline() {
    return outboundFlight.getAirline();
  }

  public Airline getReturnAirline() {
    return returnFlight.getAirline();
  }

  public void setOutbundAirline(Airline outbundAirline) {
    outboundFlight.setAirline(outbundAirline);
  }

  public void setReturnAirline(Airline returnAirline) {
    returnFlight.setAirline(returnAirline);
  }

  public void setOutbundOriginAirport(Airport airport) {
    outboundFlight.setOriginAirport(airport);
  }

  public void setOutbundDestinationAirport(Airport airport) {
    outboundFlight.setDestinationAirport(airport);
  }

  public void setReturnOriginAirport(Airport airport) {
    returnFlight.setOriginAirport(airport);
  }

  public void setReturnDestinationAirport(Airport airport) {
    returnFlight.setDestinationAirport(airport);
  }
}
