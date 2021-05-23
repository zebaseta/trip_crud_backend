package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.TripModel;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.services.CrudServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/trips")
public class TripController {
    @Autowired
    private CrudServiceTemplate<Trip, Long> crudService;

    @GetMapping()
    public List<TripModel> findAll() {
        List<Trip> trips = crudService.findAll();
        return trips.stream().map(TripModel::buildFromEntity).collect(Collectors.toList());
    }

    @PostMapping()
    public TripModel create(@RequestBody TripModel model) {
        try {
            Trip resultBD = crudService.create(model.toEntity());
            return TripModel.buildFromEntity(resultBD);
        } catch (DomainException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The entity is not ok: " + e.getMessage());
        } catch (BusinessLogicException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity");
        }
    }

}

