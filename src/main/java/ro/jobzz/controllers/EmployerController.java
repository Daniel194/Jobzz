package ro.jobzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.models.RemoveEmployeePost;
import ro.jobzz.security.SecurityUtils;
import ro.jobzz.services.EmployeePostingService;
import ro.jobzz.services.EmployerPostingService;
import ro.jobzz.services.EmployerService;
import ro.jobzz.services.ReviewEmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    private EmployerService employerService;
    private EmployerPostingService employerPostingService;
    private EmployeePostingService employeePostingService;
    private ReviewEmployeeService reviewEmployeeService;

    @Autowired
    public EmployerController(EmployerService employerService, EmployerPostingService employerPostingService, EmployeePostingService employeePostingService, ReviewEmployeeService reviewEmployeeService) {
        this.employerService = employerService;
        this.employerPostingService = employerPostingService;
        this.employeePostingService = employeePostingService;
        this.reviewEmployeeService = reviewEmployeeService;
    }


    @RequestMapping(value = "/account", method = RequestMethod.GET)
    @ResponseBody
    public Employer getEmployeeAccount() {
        Employer employer = employerService.findByEmail(SecurityUtils.getCurrentLogin());

        employer.setPassword(null);
        employer.setPhoneNumber(null);
        employer.setCardNumber(null);
        employer.setCvv(null);
        employer.setExpirationDate(null);
        employer.setDateOfBirth(null);
        employer.setReputation(null);
        employer.setEmployerPostings(null);
        employer.setReviewEmployer(null);

        return employer;
    }

    @RequestMapping(value = "/account/full", method = RequestMethod.GET)
    @ResponseBody
    public Employer getEmployerAccountFull() {
        return employerService.findByEmail(SecurityUtils.getCurrentLogin());
    }


    @RequestMapping(value = "/new/post", method = RequestMethod.POST)
    public Map<String, Object> newEmployerPost(@RequestBody EmployerPosting post) {
        boolean isCreated = employerPostingService.createNewPost(post);

        Map<String, Object> model = new HashMap<>();
        model.put("isCreated", isCreated);

        return model;
    }

    @RequestMapping(value = "/all/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployerPosting> getAllPosts() {
        return employerPostingService.findAllEmployerPosts();
    }

    @RequestMapping(value = "/update/post", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployerPost(@RequestBody EmployerPosting post) {
        boolean isUpdate = employerPostingService.updatePost(post);

        Map<String, Object> model = new HashMap<>();
        model.put("isUpdate", isUpdate);

        return model;
    }

    @RequestMapping(value = "/delete/post", method = RequestMethod.DELETE)
    public Map<String, Object> deleteEmployerPost(@RequestBody EmployerPosting post) {
        boolean isDeleted = employerPostingService.deletePost(post);

        Map<String, Object> model = new HashMap<>();
        model.put("isDeleted", isDeleted);

        return model;
    }

    @RequestMapping(value = "/update/employee/post", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployeePost(@RequestBody EmployeePosting post) {
        boolean isDeleted = employeePostingService.updateStatus(post);

        Map<String, Object> model = new HashMap<>();
        model.put("isUpdate", isDeleted);

        return model;
    }

    @RequestMapping(value = "/remove/employee/post", method = RequestMethod.POST)
    public Map<String, Object> removeEmployeePost(@RequestBody RemoveEmployeePost removeEmployeePost) {
        removeEmployeePost.getEmployeePost().setStatus(4);
        boolean isAdd = reviewEmployeeService.saveReview(removeEmployeePost.getReview());
        boolean isUpdate = employeePostingService.updateStatus(removeEmployeePost.getEmployeePost());

        Map<String, Object> model = new HashMap<>();
        model.put("isRemoved", isAdd && isUpdate);

        return model;
    }

}
