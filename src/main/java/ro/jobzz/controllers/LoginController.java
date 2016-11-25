package ro.jobzz.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    @RequestMapping("/employer/login")
    public Principal employer(Principal employer) {
        return employer;
    }

    @RequestMapping("/employee/login")
    public Principal employee(Principal employee) {
        return employee;
    }

}
