package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.AirlineModel;
import com.otravo.trips.domain.Airline;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/airlines")
public class AirlineController {
    @Autowired
    private CrudServiceTemplate<Airline, Long> crudService;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public List<AirlineModel> findAll(@RequestHeader("authorization") String token) {
        try {
            ThreadContext.push("TRANSACTION-ID",UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition find all airlines from user "+user);
            List<Airline> airlines = crudService.findAll();
            return airlines.stream().map(AirlineModel::buildFromEntity).collect(Collectors.toList());
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem: " + e.getMessage());
        }

    }

    @PostMapping()
    public AirlineModel create(@RequestHeader("authorization") String token, @RequestBody AirlineModel model) {
        try {
            ThreadContext.push("TRANSACTION-ID",UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition create airline from user "+user+" with data "+model.toString());
            Airline resultBD = crudService.create(model.toEntity());
            return AirlineModel.buildFromEntity(resultBD);
        } catch (DomainException e) {
            log.error(e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The entity is not ok: " + e.getMessage());
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity");
        }
    }

}

