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

  @OneToMany(cascade = CascadeType.ALL,
          orphanRemoval = true)
  @JoinColumn(name = "itinerary_outbound_id", referencedColumnName = "id")
  private List<Flight> outboundFlights;

  @OneToMany(cascade = CascadeType.ALL,
          orphanRemoval = true)
  @JoinColumn(name = "itinerary_return_id", referencedColumnName = "id")
  private List<Flight> returnFlights;

  public Itinerary(List<Flight> outboundFlights, List<Flight> returnFlights) {
    this.outboundFlights = outboundFlights;
    this.returnFlights = returnFlights;
  }

  @Override
  public void throwErrorIfCreationIsNotOk() throws DomainException {
    if (outboundFlights == null) throw new DomainException("Outbound fligth cannot be null");
    if (returnFlights == null) throw new DomainException("Return fligth cannot be null");
  }

  @Override
  public void throwErrorIfUpdatingIsNotOk() throws DomainException {
    if (outboundFlights == null && returnFlights == null)
      throw new DomainException("No attributes were modified");
    for(Flight flight:outboundFlights){
      flight.throwErrorIfUpdatingIsNotOk();
    }
    for(Flight flight:returnFlights){
      flight.throwErrorIfUpdatingIsNotOk();
    }
  }

  @Override
  public void updateFromEntity(Itinerary newData) {
    if (newData.getReturnFlights() != null) returnFlights = newData.getReturnFlights();
    if (newData.getOutboundFlights() != null) outboundFlights = newData.getOutboundFlights();
  }

  @Override
  public String toString() {
    return "Itinerary{" +
            "id=" + id +
            ", outboundFlights=" + outboundFlights+
            ", returnFlights=" + returnFlights+
            '}';
  }

  @Override
  public String getSystemIdInStringFormat() {
    return getId().toString();
  }

}
