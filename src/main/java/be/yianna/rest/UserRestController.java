package be.yianna.rest;

import be.yianna.domain.Role;
import be.yianna.domain.User;
import be.yianna.repository.RoleRepository;
import be.yianna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
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
        // LOGGER.info(">>>>> RECU The username " + client.getUsername() + " - "+client.getPassword());
        try {
            User resultUser = userRepository.findByUsername(user.getUsername());

            // Tester si le nom d'utilisatur est déjà réservé
            if (resultUser != null) {
                //LOGGER.info("The username " + client.getUsername() + " is already taken !");
                return new ResponseEntity<String>("Username already taken: ", HttpStatus.CONFLICT);

            } else {
                // - Ajouter l'utilisateur à la BDD
                Role role1 = roleRepository.findByRole("USER");
                //new Role("USER", new HashSet<>(Arrays.asList(user)));
                //LOGGER.info(">>>>>>>>   >>>> Role NULL ???: " + (role1 == null));
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setRoles(new HashSet<>(Arrays.asList(role1)));
                userRepository.save(user);
                //LOGGER.info("The username " + client.getUsername() + " has been added to the database !");

                return new ResponseEntity<String>("Success de l'enregistrement", HttpStatus.CREATED);

            }
        } catch (Exception ex) {
            //LOGGER.error("Exception lors de l'enregistrement de l'utlisateur:"+ ex);
            return new ResponseEntity<String>("Erreur lors de l'enregistrement : " + ex.getMessage(), HttpStatus.CONFLICT);
            // throw new Exception("Exception lors de l'enregistrement de l'utlisateur : "+ex.getMessage());
        }
    }
}
