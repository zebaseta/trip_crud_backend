package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flightpoints")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FligthPoint implements AbmEntity<FligthPoint> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private Date date;

  @OneToOne
  @Column(nullable = false, unique = true)
  private Airport airport;

  public FligthPoint(Date date, Airport airport) {
    this.date = date;
    this.airport = airport;
  }

  @Override
  public void throwErrorIfCreationIsNotOk() throws DomainException {
    if (date == null) throw new DomainException("Date cannot be null");
    if (airport == null) throw new DomainException("Airport cannot be null");
  }

  @Override
  public void throwErrorIfUpdatingIsNotOk() throws DomainException {
    if (date == null && airport == null)
      throw new DomainException("No attributes were modified");
  }

  @Override
  public void updateFromEntity(FligthPoint newData) {
    if (newData.getDate() != null) date = newData.getDate();
    if (newData.getAirport() != null) airport = newData.getAirport();
  }

  @Override
  public String getSystemIdInStringFormat() {
    return getId().toString();
  }
}
