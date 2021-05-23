package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.AirlineModel;
import com.otravo.trips.domain.Airline;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.ServiceException;
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
@RequestMapping(value = "/airlines")
public class AirlineController {
    @Autowired
    private CrudServiceTemplate<Airline, Long> crudService;

    @GetMapping()
    public List<AirlineModel> findAll() {
        List<Airline> countries = crudService.findAll();
        return countries.stream().map(AirlineModel::buildFromEntity).collect(Collectors.toList());
    }

    @PostMapping()
    public AirlineModel create(@RequestBody AirlineModel model) {
        try {
            Airline resultBD = crudService.create(model.toEntity());
            return AirlineModel.buildFromEntity(resultBD);
        } catch (DomainException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The entity is not ok: " + e.getMessage());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity");
        }
    }

}

