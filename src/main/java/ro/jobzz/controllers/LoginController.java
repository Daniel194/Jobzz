package ro.jobzz.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/employer")
    public Principal employer(Principal employer) {
        return employer;
    }

}
