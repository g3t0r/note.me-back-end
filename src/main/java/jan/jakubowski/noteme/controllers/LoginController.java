package jan.jakubowski.noteme.controllers;

import jan.jakubowski.noteme.database.entities.User;
import jan.jakubowski.noteme.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

//    @GetMapping("/login")
//    public String openLoginPage() {
//        return "forward:/index.html";
//    }

//    @PostMapping("/login")
//    public Authentication authenticate(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
//        Optional<User> user = userRepository.getOneByLogin(username);
//        if(user.isPresent()) {
//            if(encoder.matches(password, user.get().getPassword())) {
//                return new UsernamePasswordAuthenticationToken(username, password);
//            }
//        }
//        return null;
//    }

}
