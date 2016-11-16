package ro.jobzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.jobzz.entity.Job;
import ro.jobzz.service.JobService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class Login {

    private JobService service;

    @Autowired
    public Login(JobService service) {
        Assert.notNull(service, "Job Service must be not null !");

        this.service = service;
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/resource")
    public Map<String, Object> home() {

        List<Job> jobs = service.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        model.put("jobs", jobs);

        return model;
    }

}
