package ro.jobzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.models.FindJobRequest;
import ro.jobzz.security.SecurityUtils;
import ro.jobzz.services.EmployeePostingService;
import ro.jobzz.services.EmployeeService;
import ro.jobzz.services.EmployerPostingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeePostingService postingService;
    private EmployerPostingService employerPostingService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeePostingService postingService, EmployerPostingService employerPostingService) {
        this.employeeService = employeeService;
        this.postingService = postingService;
        this.employerPostingService = employerPostingService;
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


    @RequestMapping(value = "/all/post", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllEmployeePost() {
        List<EmployeePosting> postings = postingService.getAllEmployeePost();
        List<EmployeePosting> jobsWaiting = new ArrayList<>();
        List<EmployeePosting> jobsProgress = new ArrayList<>();
        List<EmployeePosting> jobsDone = new ArrayList<>();

        postings.forEach(post -> {

            if (post.getStatus() >= 0 && post.getStatus() <= 2) {
                jobsWaiting.add(post);
            } else if (post.getStatus() >= 3 && post.getStatus() <= 4) {
                jobsProgress.add(post);
            } else if (post.getStatus() >= 5 && post.getStatus() <= 6) {
                jobsDone.add(post);
            }

        });

        Map<String, Object> response = new HashMap<>();
        response.put("jobsWaiting", jobsWaiting);
        response.put("jobsProgress", jobsProgress);
        response.put("jobsDone", jobsDone);

        return response;
    }

    @RequestMapping(value = "/all/available/jobs", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployerPosting> getAllAvailableJobs() {
        return employerPostingService.findAllAvailablePostsForEmployee();
    }

    @RequestMapping(value = "/find/available/jobs", method = RequestMethod.POST)
    @ResponseBody
    public List<EmployerPosting> getAllAvailableJobs(@RequestBody FindJobRequest job) {
        if (job.getName() == null) {
            job.setName("");
        }

        return employerPostingService.findAllAvailablePostsForEmployee(job.getName(), job.getStartDate(), job.getEndDate());
    }

}
