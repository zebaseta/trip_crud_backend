package com.otravo.trips.repositories;

import com.otravo.trips.domain.Itinerary;
import com.otravo.trips.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

}