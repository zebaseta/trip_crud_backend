package com.otravo.trips.repositories;

import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

}