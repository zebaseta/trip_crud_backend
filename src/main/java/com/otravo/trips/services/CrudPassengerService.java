package com.otravo.trips.services;

import com.otravo.trips.domain.Passenger;
import com.otravo.trips.repositories.PassengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CrudPassengerService extends CrudServiceTemplate<Passenger, Long> {
  private PassengerRepository passengerRepository;
  public CrudPassengerService(PassengerRepository passengerRepository) {
    super(passengerRepository);
    this.passengerRepository = passengerRepository;
  }

  @Override
  protected Optional<Passenger> findInBDBySystemId(Passenger passenger) {
    if (passenger != null && passenger.getId() != null)
      return passengerRepository.findById(passenger.getId());
    else return Optional.empty();
  }
}
