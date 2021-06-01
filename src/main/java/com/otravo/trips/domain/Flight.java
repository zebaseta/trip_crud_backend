package com.otravo.trips.domain;

import com.otravo.trips.controllers.models.FligthInModel;
import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flights")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight implements CrudEntity<Flight>, Comparable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String code;

  @Column(nullable = false, name = "origin_date")
  private Date originDate;

  @ManyToOne
  @JoinColumn(nullable = false, name = "origin_airport_id")
  private Airport originAirport;

  @Column(nullable = false, name = "destination_date")
  private Date destinationDate;

  @ManyToOne
  @JoinColumn(nullable = false, name = "destination_airport_id")
  private Airport destinationAirport;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Airline airline;

  public Flight(
      String code,
      Date originDate,
      Airport originAirport,
      Date destinationDate,
      Airport destinationAirport,
      Airline airline) {
    this.code = code;
    this.originDate = originDate;
    this.originAirport = originAirport;
    this.destinationDate = destinationDate;
    this.destinationAirport = destinationAirport;
    this.airline = airline;
  }

  @Override
  public void throwErrorIfCreationIsNotOk() throws DomainException {
    if (code == null) throw new DomainException("Code cannot be null");
    if (airline == null) throw new DomainException("Airline point cannot be null");
    if (originDate == null) throw new DomainException("Origin date cannot be null");
    if (destinationDate == null) throw new DomainException("Destination date cannot be null");
    if (originAirport == null) throw new DomainException("Origin airport cannot be null");
    if (destinationAirport == null) throw new DomainException("Destination airport cannot be null");
    originAirport.throwErrorIfCreationIsNotOk();
    destinationAirport.throwErrorIfCreationIsNotOk();
  }

  @Override
  public void throwErrorIfUpdatingIsNotOk() throws DomainException {
    if (code == null && airline == null
            && originDate == null && destinationDate == null
            && originAirport == null && destinationAirport == null)
      throw new DomainException("No attributes were modified");
  }

  @Override
  public void updateFromEntity(Flight newData) {
    if (newData.getCode() != null) code = newData.getCode();
    if (newData.getOriginAirport() != null) originAirport = newData.getOriginAirport();
    if (newData.getDestinationAirport() != null)
      destinationAirport = newData.getDestinationAirport();
    if (newData.getOriginDate() != null) originDate = newData.getOriginDate();
    if (newData.getDestinationDate() != null) destinationDate = newData.getDestinationDate();
    if (newData.getAirline() != null) airline = newData.getAirline();
  }

  @Override
  public String getSystemIdInStringFormat() {
    return getCode();
  }

  @Override
  public int compareTo(Object o) {
    Flight fligthToCompare = (Flight) o;
    if(this.getDestinationDate().before(fligthToCompare.getOriginDate())) return -1;
    else if (fligthToCompare.getDestinationDate().before(this.getOriginDate())) return 1;
    return 0;
  }
}
