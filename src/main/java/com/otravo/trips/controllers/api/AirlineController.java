package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.AirlineModel;
import com.otravo.trips.domain.Airline;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.otravo.trips.constants.LogConstants.TRANSACTION_ID_IDENTIFICATION;

@Slf4j
@RestController
@RequestMapping(value = "/airlines")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE,RequestMethod.PUT})
public class AirlineController {
    @Autowired
    private CrudServiceTemplate<Airline, Long> crudService;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public ResponseEntity<Object> findAll(@RequestHeader("authorization") String token) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition find all airlines from user "+user);
            List<Airline> airlines = crudService.findAll();
            return ResponseEntity.ok().body(airlines.stream().map(AirlineModel::buildFromEntity).collect(Collectors.toList()));
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
    public ResponseEntity<Object> create(@RequestHeader("authorization") String token, @RequestBody AirlineModel model) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition create airline from user "+user+" with data "+model.toString());
            Airline resultBD = crudService.create(model.toEntity());
            return ResponseEntity.ok().body(AirlineModel.buildFromEntity(resultBD));
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
    public ResponseEntity<Object> update( @PathVariable String code, @RequestHeader("authorization") String token, @RequestBody AirlineModel model) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition update airline from user "+user+" with data "+model.toString());
            Airline airlineToUpdate = model.toEntity();
            airlineToUpdate.setCode(code);
            Airline resultBD = crudService.update(airlineToUpdate);
            return ResponseEntity.ok().body(AirlineModel.buildFromEntity(resultBD));
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
            log.info("Arrive petition delete airline from user " + user + " with code " + code);
            Airline entityToDelete = new Airline(code);
            crudService.delete(entityToDelete);
            return ResponseEntity.ok().body("Deleting sucessfull");
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem with deleting the entity: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("There was a problem with deleting the entity");
        }
    }



}

