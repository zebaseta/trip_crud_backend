package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fligths")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight implements AbmEntity<Flight> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String code;

  @Column(nullable = false, unique = true)
  private FligthPoint startingPoint;

  @Column(nullable = false, unique = true)
  private FligthPoint arrivalPoint;

  public Flight(String code, FligthPoint startingPoint, FligthPoint arrivalPoint) {
    this.code = code;
    this.startingPoint = startingPoint;
    this.arrivalPoint = arrivalPoint;
  }

  @Override
  public void throwErrorIfCreationIsNotOk() throws DomainException {
    if (code == null) throw new DomainException("Code cannot be null");
    if (startingPoint == null) throw new DomainException("Starting point cannot be null");
    if (arrivalPoint == null) throw new DomainException("Arribal point cannot be null");
    startingPoint.throwErrorIfCreationIsNotOk();
    arrivalPoint.throwErrorIfCreationIsNotOk();
  }

  @Override
  public void throwErrorIfUpdatingIsNotOk() throws DomainException {
    if (code == null && startingPoint == null && arrivalPoint == null) throw new DomainException("No attributes were modified");
  }

  @Override
  public void updateFromEntity(Flight newData) {
    if (newData.getCode() != null) code = newData.getCode();
    if (newData.getStartingPoint() != null) startingPoint = newData.getStartingPoint();
    if (newData.getArrivalPoint() != null) arrivalPoint = newData.getArrivalPoint();
  }

  @Override
  public String getSystemIdInStringFormat() {
    return getCode();
  }
}
