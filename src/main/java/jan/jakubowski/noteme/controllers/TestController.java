package jan.jakubowski.noteme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/test")
public class TestController {
    @GetMapping
    @ResponseBody
    public String test() {
        return "Haha, test!";
    }
}
