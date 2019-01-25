package jan.jakubowski.noteme.controllers;

import jan.jakubowski.noteme.database.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

    @GetMapping("/pinboard")
    @ResponseBody
    public String index(Authentication authentication) {

        Logger log = LoggerFactory.getLogger(this.getClass());

        String principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        log.info("Principal: " + principal);

        return principal;
    }
}
