package ro.jobzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.jobzz.entity.Employer;
import ro.jobzz.service.EmployerService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class Login {

    private EmployerService service;

    @Autowired
    public Login(EmployerService service) {
        Assert.notNull(service, "Employer Service must be not null !");

        this.service = service;
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/resource")
    public Map<String, Object> home() {

        List<Employer> employers = service.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");

        return model;
    }

}
