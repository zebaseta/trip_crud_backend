package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.PassengerModel;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.ServiceException;
import com.otravo.trips.services.CrudServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/passengers")
public class PassengerController {
    @Autowired
    private CrudServiceTemplate<Passenger, Long> crudService;

    @Value("${passenger.birth.pattern}")
    private String pattern;

    @GetMapping()
    public List<PassengerModel> findAll() {
        List<Passenger> countries = crudService.findAll();
        return countries.stream().map(PassengerModel::buildFromEntity).collect(Collectors.toList());
    }

    @PostMapping()
    public PassengerModel create(@RequestBody PassengerModel model) {
        try {
            Passenger resultBD = crudService.create(model.toEntity(pattern));
            return PassengerModel.buildFromEntity(resultBD);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date format is not ok, it must be "+pattern);
        } catch (DomainException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The entity is not ok: " + e.getMessage());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem creating the entity");
        }
    }


}

