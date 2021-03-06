package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.TripInModel;
import com.otravo.trips.controllers.models.TripOutModel;
import com.otravo.trips.domain.Airline;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.otravo.trips.constants.LogConstants.TRANSACTION_ID_IDENTIFICATION;
import static com.otravo.trips.controllers.models.TripOutModel.buildFromEntity;

@Slf4j
@RestController
@RequestMapping(value = "/trips")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE,RequestMethod.PUT})
public class TripController {
    @Autowired
    private CrudServiceTemplate<Trip, Long> tripService;

    @Autowired
    private CrudServiceTemplate<Passenger, Long> passengerService;

    @Autowired
    private JwtService jwtService;

    @Value("${trips.distance-days-to-return}")
    private int distanceDaysToReturn;

    @Value("${passenger.birth.pattern}")
    private String pattern;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

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
                    return ResponseEntity.ok().body(trips.stream().map(t->TripOutModel.buildFromEntity(t,simpleDateFormat)).collect(Collectors.toList()));
                }
            } else {
                List<Trip> trips = tripService.findAll();
                return ResponseEntity.ok().body(trips.stream().map(t->TripOutModel.buildFromEntity(t,simpleDateFormat)).collect(Collectors.toList()));
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
    public ResponseEntity<Object> create(@RequestHeader("authorization") String token, @RequestBody TripInModel model) {
        try {
            MDC.put("TRANSACTION-ID", TRANSACTION_ID_IDENTIFICATION + UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition create trip from user " + user + " with data " + model.toString());
            Trip resultBD = tripService.create(model.toEntity(pattern,distanceDaysToReturn));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            TripOutModel result = buildFromEntity(resultBD,simpleDateFormat);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader("authorization") String token, @PathVariable Long id) {
        try {
            MDC.put("TRANSACTION-ID", TRANSACTION_ID_IDENTIFICATION + UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition delete trip from user " + user + " with id " + id);
            Trip entityToDelete = new Trip(id);
            tripService.delete(entityToDelete);
            return ResponseEntity.ok().build();
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem with deleting the entity: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("There was a problem with deleting the entity");
        }
    }

}
