package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.TripModel;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.otravo.trips.constans.LogConstants.TRANSACTION_ID_IDENTIFICATION;

@Slf4j
@RestController
@RequestMapping(value = "/trips")
public class TripController {
    @Autowired
    private CrudServiceTemplate<Trip, Long> crudService;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public ResponseEntity<Object> findAll(@RequestHeader("authorization") String token) {
        try {
            MDC.put("TRANSACTION-ID", TRANSACTION_ID_IDENTIFICATION + UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition find all trips from user " + user);
            List<Trip> trips = crudService.findAll();
            return ResponseEntity.ok().body(trips.stream().map(TripModel::buildFromEntity).collect(Collectors.toList()));
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem: " + e.getMessage());
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
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
            Trip resultBD = crudService.create(model.toEntity());
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
