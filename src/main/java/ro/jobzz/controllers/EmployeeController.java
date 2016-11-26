package ro.jobzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.jobzz.entities.Employee;
import ro.jobzz.security.SecurityUtils;
import ro.jobzz.services.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @RequestMapping(value = "/account", method = RequestMethod.GET)
    @ResponseBody
    public Employee getEmployerAccount() {

        Employee employee = employeeService.findByEmail(SecurityUtils.getCurrentLogin());

        employee.setPassword(null);
        employee.setPhoneNumber(null);
        employee.setCardNumber(null);
        employee.setCvv(null);
        employee.setExpirationDate(null);
        employee.setDateOfBirth(null);
        employee.setReputation(null);
        employee.setEmployeePostings(null);
        employee.setReviewEmployee(null);
        employee.setJob(null);

        return employee;
    }

    @RequestMapping(value = "/account/full", method = RequestMethod.GET)
    @ResponseBody
    public Employee getEmployerAccountFull() {
        return employeeService.findByEmail(SecurityUtils.getCurrentLogin());
    }


}
