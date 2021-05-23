package com.otravo.trips.services;

import com.otravo.trips.domain.Flight;
import com.otravo.trips.repositories.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CrudFlightService extends CrudServiceTemplate<Flight, Long> {
  private FlightRepository fligthRepository;

  public CrudFlightService(FlightRepository fligthRepository) {
    super(fligthRepository);
    this.fligthRepository = fligthRepository;
  }

  @Override
  protected Optional<Flight> findInBDBySystemId(Flight fligth) {
    if (fligth != null && fligth.getCode() != null)
      return fligthRepository.findByCode(fligth.getCode());
    else return Optional.empty();
  }
}
