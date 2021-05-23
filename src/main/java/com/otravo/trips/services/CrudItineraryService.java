package com.otravo.trips.services;

import com.otravo.trips.domain.Itinerary;
import com.otravo.trips.repositories.ItineraryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CrudItineraryService extends CrudServiceTemplate<Itinerary, Long> {
  private ItineraryRepository itineraryRepository;
  public CrudItineraryService(ItineraryRepository itineraryRepository) {
    super(itineraryRepository);
    this.itineraryRepository = itineraryRepository;
  }

  @Override
  protected Optional<Itinerary> findInBDBySystemId(Itinerary itinerary) {
    if (itinerary != null && itinerary.getId() != null)
      return itineraryRepository.findById(itinerary.getId());
    else return Optional.empty();
  }
}
