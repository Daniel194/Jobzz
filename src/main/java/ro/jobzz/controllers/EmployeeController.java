package ro.jobzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.models.EmployeeReviews;
import ro.jobzz.models.EmployerReviews;
import ro.jobzz.models.FindJobRequest;
import ro.jobzz.models.ReviewEmployerPost;
import ro.jobzz.models.ChangePassword;
import ro.jobzz.security.SecurityUtils;
import ro.jobzz.services.EmployeePostingService;
import ro.jobzz.services.EmployeeService;
import ro.jobzz.services.EmployerPostingService;
import ro.jobzz.services.ReviewEmployerService;
import ro.jobzz.services.ReviewEmployeeService;
import ro.jobzz.utilities.EmployeeUtils;
import ro.jobzz.utilities.MessagesConstant;

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
    private ReviewEmployeeService reviewEmployeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeePostingService postingService,
                              EmployerPostingService employerPostingService, EmployeePostingService employeePostingService,
                              ReviewEmployerService reviewEmployerService, ReviewEmployeeService reviewEmployeeService) {

        this.employeeService = employeeService;
        this.postingService = postingService;
        this.employerPostingService = employerPostingService;
        this.employeePostingService = employeePostingService;
        this.reviewEmployerService = reviewEmployerService;
        this.reviewEmployeeService = reviewEmployeeService;
    }


    @RequestMapping(value = "/account", method = RequestMethod.GET)
    @ResponseBody
    public Employee getEmployerAccount() {
        Employee employee = employeeService.findByEmail(SecurityUtils.getCurrentLogin());
        EmployeeUtils.hiddenEmployeeDetails(employee);

        return employee;
    }

    @RequestMapping(value = "/account/full", method = RequestMethod.GET)
    @ResponseBody
    public Employee getEmployerAccountFull() {
        Employee employee = employeeService.findByEmail(SecurityUtils.getCurrentLogin());
        employee.setReviewEmployee(null);
        employee.setEmployeePostings(null);
        employee.setJob(null);
        employee.setPassword(null);

        return employee;
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
        model.put(MessagesConstant.IS_CREATED, isCreated);

        return model;
    }

    @RequestMapping(value = "/update/post", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployeePost(@RequestBody EmployeePosting post) {
        boolean isUpdate = employeePostingService.createPost(post);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_UPDATE, isUpdate);

        return model;
    }

    @RequestMapping(value = "/review/employer/post", method = RequestMethod.POST)
    public Map<String, Object> reviewEmployerPost(@RequestBody ReviewEmployerPost review) {
        boolean isCreated = reviewEmployerService.reviewEmployerPost(review.getReview());
        boolean isChange = employeePostingService.updateStatusToDone(review.getEmployeePosting());

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_CREATED, isCreated && isChange);

        return model;
    }

    @RequestMapping(value = "/allow/new/post", method = RequestMethod.GET)
    public Map<String, Object> allowNewPost() {
        boolean isAllow = employeePostingService.allowNewPost();

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_ALLOW, isAllow);

        return model;
    }

    @RequestMapping(value = "/allow/new/review/{employerId}", method = RequestMethod.GET)
    public Map<String, Object> allowNewReview(@PathVariable("employerId") int employerId) {
        boolean isAllow = reviewEmployerService.allowNewReview(employerId);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_ALLOW, isAllow);

        return model;
    }

    @RequestMapping(value = "/all/reviews", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployeeReviews> allReviews() {
        return reviewEmployeeService.getAllReviews();
    }

    @RequestMapping(value = "/employer/{employerId}", method = RequestMethod.GET)
    public List<EmployerReviews> getEmployee(@PathVariable("employerId") int employerId) {
        return reviewEmployerService.getAllReviews(employerId);
    }


    @RequestMapping(value = "/update/employee/general/information", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployeeGeneralInformation(@RequestBody Employee employee) {
        boolean isDeleted = employeeService.updateGeneralInformation(employee);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_UPDATE, isDeleted);

        return model;
    }

    @RequestMapping(value = "/update/employee/payment/information", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployerPaymentInformation(@RequestBody Employee employee) {
        boolean isDeleted = employeeService.updatePaymentInformation(employee);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_UPDATE, isDeleted);

        return model;
    }

    @RequestMapping(value = "/change/password", method = RequestMethod.PUT)
    public Map<String, Object> changeEmployerPassword(@RequestBody ChangePassword changePassword) {
        boolean isChanged = employeeService.changePassword(changePassword);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_CHANGED, isChanged);

        return model;
    }

    @RequestMapping(value = "/change/picture", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePicture(@RequestParam(value = "file") MultipartFile file) {
        String profilePicture = employeeService.changeUserPicture(file);

        Map<String, Object> model = new HashMap<>();
        model.put("profilePicture", profilePicture);

        return model;
    }

}
