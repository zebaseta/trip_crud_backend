package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.AirportModel;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/airports")
public class AirportController {
    @Autowired
    private CrudServiceTemplate<Airport, Long> crudService;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public List<AirportModel> findAll(@RequestHeader("authorization") String token) {
        try {
            ThreadContext.push("TRANSACTION-ID",UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition find alla airports from user "+user);
            jwtService.verifyTokenAndGetUser(token);
            List<Airport> aiports = crudService.findAll();
            return aiports.stream().map(AirportModel::buildFromEntity).collect(Collectors.toList());
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem: " + e.getMessage());
        }

    }

    @PostMapping()
    public AirportModel create(@RequestHeader("authorization") String token, @RequestBody AirportModel model) {
        try {
            ThreadContext.push("TRANSACTION-ID",UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition create airport from user "+user+" with data "+model.toString());
            Airport resultBD = crudService.create(model.toEntity());
            return AirportModel.buildFromEntity(resultBD);
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

