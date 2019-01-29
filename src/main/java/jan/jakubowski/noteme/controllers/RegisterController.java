package jan.jakubowski.noteme.controllers;

import jan.jakubowski.noteme.exceptions.UserAlreadyExistException;
import jan.jakubowski.noteme.services.UserService;
import jan.jakubowski.noteme.services.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users/register")
@Validated
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Validation failed")
    public void validationFailed() {
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already exist")
    public void userAlreadyExist() {
    }

}
