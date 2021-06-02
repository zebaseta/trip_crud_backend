package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.AirlineModel;
import com.otravo.trips.controllers.models.AirportModel;
import com.otravo.trips.domain.Airline;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.otravo.trips.constants.LogConstants.TRANSACTION_ID_IDENTIFICATION;

@Slf4j
@RestController
@RequestMapping(value = "/airports")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE,RequestMethod.PUT})
public class AirportController {
    @Autowired
    private CrudServiceTemplate<Airport, Long> crudService;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public ResponseEntity<Object>  findAll(@RequestHeader("authorization") String token) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition find airports from user "+user);
            jwtService.verifyTokenAndGetUser(token);
            List<Airport> aiports = crudService.findAll();
            return ResponseEntity.ok().body(aiports.stream().map(AirportModel::buildFromEntity).collect(Collectors.toList()));
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
    public ResponseEntity<Object> create(@RequestHeader("authorization") String token, @RequestBody AirportModel model) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition create airport from user "+user+" with data "+model.toString());
            Airport resultBD = crudService.create(model.toEntity());
            return ResponseEntity.ok().body(AirportModel.buildFromEntity(resultBD));
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

    @PutMapping("/{code}")
    public ResponseEntity<Object> update(@PathVariable String code, @RequestHeader("authorization") String token, @RequestBody AirportModel model) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition update airline from user "+user+" with data "+model.toString());
            Airport airportToUpdate = model.toEntity();
            airportToUpdate.setCode(code);
            Airport resultBD = crudService.update(airportToUpdate);
            return ResponseEntity.ok().body(AirportModel.buildFromEntity(resultBD));
        } catch (DomainException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("The entity is not ok: " + e.getMessage());
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem updating the entity: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("There was a problem updating the entity");
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Object> delete(@RequestHeader("authorization") String token, @PathVariable String code) {
        try {
            MDC.put("TRANSACTION-ID", TRANSACTION_ID_IDENTIFICATION + UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition delete airport from user " + user + " with code " + code);
            Airport entityToDelete = new Airport(code);
            crudService.delete(entityToDelete);
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

