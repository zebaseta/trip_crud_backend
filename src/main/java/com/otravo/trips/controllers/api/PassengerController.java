package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.PassengerModel;
import com.otravo.trips.domain.Passenger;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.otravo.trips.constans.LogConstants.TRANSACTION_ID_IDENTIFICATION;

@Slf4j
@RestController
@RequestMapping(value = "/passengers")
public class PassengerController {
    @Autowired
    private CrudServiceTemplate<Passenger, Long> crudService;

    @Value("${passenger.birth.pattern}")
    private String pattern;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public List<PassengerModel> findAll(@RequestHeader("authorization") String token) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition find alla passengers from user "+user);
            List<Passenger> passengers = crudService.findAll();
            return passengers.stream().map(PassengerModel::buildFromEntity).collect(Collectors.toList());
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem: " + e.getMessage());
        }

    }

    @PostMapping()
    public PassengerModel create(@RequestHeader("authorization") String token, @RequestBody PassengerModel model) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            String user = jwtService.verifyTokenAndGetUser(token);
            log.info("Arrive petition create passagenger from user "+user+" with data "+model.toString());
            Passenger resultBD = crudService.create(model.toEntity(pattern));
            return PassengerModel.buildFromEntity(resultBD);
        } catch (DateTimeParseException e) {
            log.error(e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The date format is not ok, it must be "+pattern);
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

