package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    public List<Flight> getOutboundFligths() {
        return itinerary.getOutboundFlights();
    }

    public List<Flight> getReturnrFligths() {
        return itinerary.getReturnFlights();
    }

    public void setOutboundFligths(List<Flight> outboundFligths) {
        itinerary.setOutboundFlights(outboundFligths);
    }

    public void setReturnFligths(List<Flight> returnsFligths) {
        itinerary.setReturnFlights(returnsFligths);
    }
}
