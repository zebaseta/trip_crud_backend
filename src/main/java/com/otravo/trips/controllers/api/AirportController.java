package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.AirportModel;
import com.otravo.trips.services.CrudServiceTemplate;
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

    @GetMapping()
    public List<AirportModel> findAll() {
        List<Airport> aiports = crudService.findAll();
        return aiports.stream().map(AirportModel::buildFromEntity).collect(Collectors.toList());
    }

    @PostMapping()
    public AirportModel create(@RequestBody AirportModel model) {
        try {
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

