package jan.jakubowski.noteme.controllers;

import jan.jakubowski.noteme.database.entities.User;
import jan.jakubowski.noteme.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Controller
@RequestMapping("/register")
@Validated
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping
    public String getRegisterForm() {
        return "forward:/index.html";
    }

    @PostMapping(produces = "application/json")
    @ResponseBody
    public ResponseEntity registerUser(@RequestParam(name = "login", required = true) @NotNull @Size(min = 1) String login,
                                       @RequestParam(name = "password", required = true) @NotNull @Size(min = 4) String password) {


        Optional<User> existingUser = userRepository.getOneByLogin(login);
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{'message':'Login already in use'}");
        }


        userRepository.save(new User(login, encoder.encode(password), true, true, true, true));
        Optional<User> createdUser = userRepository.getOneByLogin(login);
        if (createdUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleException() {
        return ResponseEntity.badRequest().build();
    }
}
