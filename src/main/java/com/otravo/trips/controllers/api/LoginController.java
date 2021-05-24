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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private JwtService jwtService;


    @PostMapping()
    public String login(@RequestBody LoginModel model) {
        try {
            ThreadContext.push("TRANSACTION-ID", UUID.randomUUID().toString());
            boolean isUserOk =  model.getUser()!=null && model.getUser().equals("admin") && model.getPass()!= null && model.getPass().equals("pass1234");
            if(!isUserOk) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User and pass are not ok");
            return jwtService.createToken(model.getUser(),model.getPass());
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

