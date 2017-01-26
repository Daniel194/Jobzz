package ro.jobzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.jobzz.constants.EmployeePostStatus;
import ro.jobzz.constants.EmployerPostStatus;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.models.ChangePassword;
import ro.jobzz.models.EmployeeReviews;
import ro.jobzz.models.EmployerReviews;
import ro.jobzz.models.ReviewEmployeePost;
import ro.jobzz.security.SecurityUtils;
import ro.jobzz.services.*;
import ro.jobzz.utilities.EmployerUtils;
import ro.jobzz.utilities.MessagesConstant;

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
    private ReviewEmployerService reviewEmployerService;

    @Autowired
    public EmployerController(EmployerService employerService, EmployerPostingService employerPostingService,
                              EmployeePostingService employeePostingService, ReviewEmployeeService reviewEmployeeService,
                              ReviewEmployerService reviewEmployerService) {

        this.employerService = employerService;
        this.employerPostingService = employerPostingService;
        this.employeePostingService = employeePostingService;
        this.reviewEmployeeService = reviewEmployeeService;
        this.reviewEmployerService = reviewEmployerService;
    }


    @RequestMapping(value = "/account", method = RequestMethod.GET)
    @ResponseBody
    public Employer getEmployeeAccount() {
        Employer employer = employerService.findByEmail(SecurityUtils.getCurrentLogin());
        EmployerUtils.hiddenEmployerDetails(employer);

        return employer;
    }

    @RequestMapping(value = "/account/full", method = RequestMethod.GET)
    @ResponseBody
    public Employer getEmployerAccountFull() {
        Employer employer = employerService.findByEmail(SecurityUtils.getCurrentLogin());
        employer.setEmployerPostings(null);
        employer.setReviewEmployer(null);
        employer.setPassword(null);

        return employer;
    }


    @RequestMapping(value = "/new/post", method = RequestMethod.POST)
    public Map<String, Object> newEmployerPost(@RequestBody EmployerPosting post) {
        boolean isCreated = employerPostingService.createNewPost(post);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_CREATED, isCreated);

        return model;
    }

    @RequestMapping(value = "/all/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployerPosting> getAllPosts() {
        return employerPostingService.findAllEmployerPosts();
    }

    @RequestMapping(value = "/history/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployerPosting> getHistoryPosts() {
        return employerPostingService.findHistoryEmployerPosts();
    }

    @RequestMapping(value = "/update/post", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployerPost(@RequestBody EmployerPosting post) {
        boolean isUpdate = employerPostingService.updatePost(post);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_UPDATE, isUpdate);

        return model;
    }

    @RequestMapping(value = "/delete/post", method = RequestMethod.DELETE)
    public Map<String, Object> deleteEmployerPost(@RequestBody EmployerPosting post) {
        boolean isDeleted = employerPostingService.deletePost(post);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_DELETED, isDeleted);

        return model;
    }

    @RequestMapping(value = "/update/employee/post", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployeePost(@RequestBody EmployeePosting post) {
        boolean isUpdate = employeePostingService.updateStatus(post);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_UPDATE, isUpdate);

        return model;
    }

    @RequestMapping(value = "/remove/employee/post", method = RequestMethod.POST)
    public Map<String, Object> removeEmployeePost(@RequestBody ReviewEmployeePost reviewEmployeePost) {
        reviewEmployeePost.getEmployeePost().setStatus(EmployeePostStatus.IN_PROGRESS_REMOVED.getStatus());
        boolean isAdd = reviewEmployeeService.saveReview(reviewEmployeePost.getReview());
        boolean isUpdate = employeePostingService.updateStatus(reviewEmployeePost.getEmployeePost());

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_REMOVED, isAdd && isUpdate);

        return model;
    }

    @RequestMapping(value = "/pay/employee/post", method = RequestMethod.POST)
    public Map<String, Object> payEmployeePost(@RequestBody ReviewEmployeePost reviewEmployeePost) {
        reviewEmployeePost.getEmployeePost().setStatus(EmployeePostStatus.DONE_PAID.getStatus());
        boolean isAdd = reviewEmployeeService.saveReview(reviewEmployeePost.getReview());
        boolean isUpdate = employeePostingService.updateStatus(reviewEmployeePost.getEmployeePost());

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_PAID, isAdd && isUpdate);

        return model;
    }

    @RequestMapping(value = "/close/post", method = RequestMethod.POST)
    public Map<String, Object> closePost(@RequestBody EmployerPosting post) {
        post.setStatus(EmployerPostStatus.CLOSED.getStatus());
        boolean isClose = employerPostingService.updatePost(post);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_CLOSE, isClose);

        return model;
    }

    @RequestMapping(value = "/allow/new/post", method = RequestMethod.GET)
    public Map<String, Object> allowNewPost() {
        boolean isAllow = employerPostingService.allowNewPost();

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_ALLOW, isAllow);

        return model;
    }

    @RequestMapping(value = "/all/reviews", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployerReviews> allReviews() {
        return reviewEmployerService.getAllReviews();
    }

    @RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
    public List<EmployeeReviews> getEmployee(@PathVariable("employeeId") int employeeId) {
        return reviewEmployeeService.getAllReviews(employeeId);
    }

    @RequestMapping(value = "/update/employer/general/information", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployerGeneralInformation(@RequestBody Employer employer) {
        boolean isUpdate = employerService.updateGeneralInformation(employer);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_UPDATE, isUpdate);

        return model;
    }

    @RequestMapping(value = "/update/employer/payment/information", method = RequestMethod.PUT)
    public Map<String, Object> updateEmployerPaymentInformation(@RequestBody Employer employer) {
        boolean isUpdate = employerService.updatePaymentInformation(employer);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_UPDATE, isUpdate);

        return model;
    }

    @RequestMapping(value = "/change/password", method = RequestMethod.PUT)
    public Map<String, Object> changeEmployerPassword(@RequestBody ChangePassword changePassword) {
        boolean isChanged = employerService.changePassword(changePassword);

        Map<String, Object> model = new HashMap<>();
        model.put(MessagesConstant.IS_CHANGED, isChanged);

        return model;
    }

    @RequestMapping(value = "/change/picture", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePicture(@RequestParam(value = "file") MultipartFile file) {
        String profilePicture = employerService.changeUserPicture(file);

        Map<String, Object> model = new HashMap<>();
        model.put("profilePicture", profilePicture);

        return model;
    }
}
