package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.AirportModel;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.otravo.trips.domain.Airport;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;

import java.util.List;
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
            jwtService.verifyToken(token);
            List<Airport> aiports = crudService.findAll();
            return aiports.stream().map(AirportModel::buildFromEntity).collect(Collectors.toList());
        } catch (BusinessLogicException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem: " + e.getMessage());
        }

    }

    @PostMapping()
    public AirportModel create(@RequestHeader("authorization") String token, @RequestBody AirportModel model) {
        try {
            jwtService.verifyToken(token);
            Airport resultBD = crudService.create(model.toEntity());
            return AirportModel.buildFromEntity(resultBD);
        } catch (DomainException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The entity is not ok: " + e.getMessage());
        } catch (BusinessLogicException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity");
        }
    }


}

