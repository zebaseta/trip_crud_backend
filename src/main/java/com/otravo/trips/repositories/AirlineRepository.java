package com.otravo.trips.repositories;

import com.otravo.trips.domain.Airline;
import com.otravo.trips.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Optional<Airline> findByCode(String code);
}