package be.yianna.rest;

import be.yianna.domain.Message;
import be.yianna.domain.Role;
import be.yianna.domain.User;
import be.yianna.repository.RoleRepository;
import be.yianna.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin // possible to specify settings
public class UserRestController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserRestController(UserRepository userRepository,
                              RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository =  roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            User resultUser = userRepository.findByUsername(user.getUsername());

            // verify if username is already in database
            if (resultUser != null) {
                return new ResponseEntity<String>("Username already taken: ", HttpStatus.CONFLICT);

            } else {
                // - add user to Database
                Role role1 = roleRepository.findByRole("USER");
                //new Role("USER", new HashSet<>(Arrays.asList(user)));
                user.setPassword(user.getPassword());
                //user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setRoles(new HashSet<>(Arrays.asList(role1)));
                userRepository.save(user);
                return new ResponseEntity<String>("Success de l'enregistrement", HttpStatus.CREATED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'enregistrement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        try{
            User user = userRepository.findByUsername(username);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("user not found : " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/user/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        try {
            Optional<User> resultUser = userRepository.findById(id);

            if (resultUser.isPresent()) {
                // DELETE
                userRepository.delete(resultUser.get());
                return new ResponseEntity<String>("Success de la suppresion l'enregistrement", HttpStatus.ACCEPTED);
            } else {
                // user not found
                return new ResponseEntity<String>("Username already taken: ", HttpStatus.CONFLICT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'enregistrement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /*@GetMapping("/checklogin")
    public ResponseEntity<?> login(Principal user) {

        if (user != null)
            return new ResponseEntity<String>(user.getName() + ": Authenticated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<String>("Please add your basic token in the Authorization Header",
                    HttpStatus.UNAUTHORIZED);
    }*/

    @GetMapping("/checklogin")
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
