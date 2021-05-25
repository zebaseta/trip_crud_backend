package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.TripModel;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.otravo.trips.constans.LogConstants.TRANSACTION_ID_IDENTIFICATION;

@Slf4j
@RestController
@RequestMapping(value = "/trips")
public class TripController {
    @Autowired
    private CrudServiceTemplate<Trip, Long> tripService;

    @Autowired
    private CrudServiceTemplate<Passenger, Long> passengerService;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public ResponseEntity<Object> findAll(@RequestHeader("authorization") String token, @RequestParam(required = false) String passengerEmail, @RequestParam(required = false) String passengerPassport) {
        try {
            MDC.put("TRANSACTION-ID", TRANSACTION_ID_IDENTIFICATION + UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition find all trips from user " + user);
            boolean hasQueryParams = passengerEmail != null || passengerPassport != null;
            if (hasQueryParams) {
                List<Passenger> passengers = passengerService.findAll(Example.of(new Passenger(passengerEmail, passengerPassport)));
                if (passengers == null || passengers.isEmpty())
                    return ResponseEntity.badRequest().body("Do not exists passenger with those email and passport");
                else {
                    Trip tripExample = new Trip(passengers.get(0));
                    List<Trip> trips = tripService.findAll(Example.of(tripExample));
                    return ResponseEntity.ok().body(trips.stream().map(TripModel::buildFromEntity).collect(Collectors.toList()));
                }
            } else {
                List<Trip> trips = tripService.findAll();
                return ResponseEntity.ok().body(trips.stream().map(TripModel::buildFromEntity).collect(Collectors.toList()));
            }
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("There was a problem: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> create(
            @RequestHeader("authorization") String token, @RequestBody TripModel model) {
        try {
            MDC.put("TRANSACTION-ID", TRANSACTION_ID_IDENTIFICATION + UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition create trip from user " + user + " with data " + model.toString());
            Trip resultBD = tripService.create(model.toEntity());
            TripModel result = TripModel.buildFromEntity(resultBD);
            return ResponseEntity.ok().body(result);
        } catch (DomainException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("The entity is not ok: " + e.getMessage());
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem creating the entity: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("There was a problem creating the entity");
        }
    }
}
