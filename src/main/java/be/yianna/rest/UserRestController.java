package be.yianna.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin // possible to specify settings
public class UserRestController {

    @GetMapping("/checklogin")
    public ResponseEntity<?> login(Principal user) {

        if (user != null)
            return new ResponseEntity<String>(user.getName() + ": Authenticated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<String>("Please add your basic token in the Authorization Header",
                    HttpStatus.UNAUTHORIZED);
    }
}
