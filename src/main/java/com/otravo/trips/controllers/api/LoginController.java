package com.otravo.trips.controllers.api;

import com.otravo.trips.controllers.models.LoginModel;
import com.otravo.trips.controllers.models.TokenModel;
import com.otravo.trips.exceptions.BusinessLogicException;
import com.otravo.trips.services.JwtService;
import com.otravo.trips.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.otravo.trips.constants.LogConstants.TRANSACTION_ID_IDENTIFICATION;

@Slf4j
@RestController
@RequestMapping(value = "/login")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;



    @PostMapping()
    public ResponseEntity<Object> login(@RequestBody LoginModel model) {
        try {
            MDC.put("TRANSACTION-ID",TRANSACTION_ID_IDENTIFICATION+UUID.randomUUID().toString());
            boolean isUserOk =  userService.validateUserLogin(model.getEmail(),model.getPassword());
            if(!isUserOk) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User and pass are not ok");
            String token = jwtService.createToken("1","admin", model.getEmail());
            return ResponseEntity.ok().body(new TokenModel(token));
        } catch (BusinessLogicException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("There was a problem: " + e.getMessage());
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body("There was a problem");
        }
    }

}

