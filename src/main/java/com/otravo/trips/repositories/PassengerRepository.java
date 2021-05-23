package com.otravo.trips.repositories;

import com.otravo.trips.domain.Airport;
import com.otravo.trips.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByCode(String code);
}