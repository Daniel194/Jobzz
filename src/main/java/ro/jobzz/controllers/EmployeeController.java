package ro.jobzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.models.FindJobRequest;
import ro.jobzz.models.ReviewEmployerPost;
import ro.jobzz.security.SecurityUtils;
import ro.jobzz.services.EmployeePostingService;
import ro.jobzz.services.EmployeeService;
import ro.jobzz.services.EmployerPostingService;
import ro.jobzz.services.ReviewEmployerService;

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
    private EmployeePostingService employeePostingService;
    private ReviewEmployerService reviewEmployerService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeePostingService postingService, EmployerPostingService employerPostingService,
                              EmployeePostingService employeePostingService, ReviewEmployerService reviewEmployerService) {

        this.employeeService = employeeService;
        this.postingService = postingService;
        this.employerPostingService = employerPostingService;
        this.employeePostingService = employeePostingService;
        this.reviewEmployerService = reviewEmployerService;
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

        Map<String, Object> model = new HashMap<>();
        model.put("jobsWaiting", jobsWaiting);
        model.put("jobsProgress", jobsProgress);
        model.put("jobsDone", jobsDone);

        return model;
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

    @RequestMapping(value = "/create/post", method = RequestMethod.POST)
    public Map<String, Object> createEmployeePost(@RequestBody EmployeePosting post) {
        boolean isCreated = employeePostingService.createPost(post);

        Map<String, Object> model = new HashMap<>();
        model.put("isCreated", isCreated);

        return model;
    }

    @RequestMapping(value = "/update/post", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployeePost(@RequestBody EmployeePosting post) {
        boolean isUpdate = employeePostingService.createPost(post);

        Map<String, Object> model = new HashMap<>();
        model.put("isUpdate", isUpdate);

        return model;
    }

    @RequestMapping(value = "/review/employer/post", method = RequestMethod.POST)
    public Map<String, Object> reviewEmployerPost(@RequestBody ReviewEmployerPost review) {
        boolean isCreated = reviewEmployerService.reviewEmployerPost(review.getReview());
        boolean isChange = employeePostingService.updateStatusToDone(review.getEmployeePosting());

        Map<String, Object> model = new HashMap<>();
        model.put("isCreated", isCreated && isChange);

        return model;
    }

    @RequestMapping(value = "/allow/new/post", method = RequestMethod.GET)
    public Map<String, Object> allowNewPost() {
        boolean isAllow = employeePostingService.allowNewPost();

        Map<String, Object> model = new HashMap<>();
        model.put("isAllow", isAllow);

        return model;
    }

    @RequestMapping(value = "/allow/new/review/{employerId}", method = RequestMethod.GET)
    public Map<String, Object> allowNewReview(@PathVariable("employerId") int employerId) {
        boolean isAllow = reviewEmployerService.allowNewReview(employerId);

        Map<String, Object> model = new HashMap<>();
        model.put("isAllow", isAllow);

        return model;
    }

}
