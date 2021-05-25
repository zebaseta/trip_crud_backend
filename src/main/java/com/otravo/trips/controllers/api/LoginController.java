package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.LoginModel;
import com.otravo.trips.controllers.models.TripModel;
import com.otravo.trips.domain.Trip;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.services.CrudServiceTemplate;
import com.otravo.trips.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.otravo.trips.constans.LogConstants.TRANSACTION_ID_IDENTIFICATION;

@Slf4j
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @PostMapping()
    public ResponseEntity<Object> login(@RequestBody LoginModel model) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            boolean isUserOk =  model.getUser()!=null && model.getUser().equals("admin") && model.getPass()!= null && model.getPass().equals("pass1234");
            if(!isUserOk) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User and pass are not ok");
            return ResponseEntity.ok().body(jwtService.createToken(model.getUser(),model.getPass()));
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem: " + e.getMessage());
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body("There was a problem: " + e.getMessage());
        }
    }

}

