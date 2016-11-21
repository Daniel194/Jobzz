package ro.jobzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.Job;
import ro.jobzz.requests.entities.EmployeeRequest;
import ro.jobzz.services.EmployeeService;
import ro.jobzz.services.EmployerService;
import ro.jobzz.services.JobService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Login {

    private JobService jobServices;
    private EmployerService employerService;
    private EmployeeService employeeService;

    @Autowired
    public Login(JobService jobServices, EmployerService employerService, EmployeeService employeeService) {
        Assert.notNull(jobServices, "Job Service must be not null !");
        Assert.notNull(employerService, "Employer Service must be not null !");
        Assert.notNull(employeeService, "Employee Service must be not null !");

        this.jobServices = jobServices;
        this.employerService = employerService;
        this.employeeService = employeeService;
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register/new/employer/account")
    public Map<String, Object> registerNewEmployerAccount(@RequestBody Employer employer) {
        boolean isCreated = employerService.registerNewEmployerAccount(employer);

        Map<String, Object> model = new HashMap<>();
        model.put("isCreated", isCreated);

        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register/new/employee/account")
    public Map<String, Object> registerNewEmployeeAccount(@RequestBody EmployeeRequest employee) {
        boolean isCreated = employeeService.registerNewEmployeeAccount(employee);

        Map<String, Object> model = new HashMap<>();
        model.put("isCreated", isCreated);

        return model;
    }

    @RequestMapping("/jobs")
    public Map<String, Object> jobs() {

        List<Job> jobs = jobServices.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("jobs", jobs);

        return model;
    }

}
