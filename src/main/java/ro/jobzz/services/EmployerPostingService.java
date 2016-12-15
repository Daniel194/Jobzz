package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.repositories.EmployerPostingRepository;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.*;

@Service
public class EmployerPostingService {

    private EmployerPostingRepository postingRepository;
    private EmployerRepository employerRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployerPostingService(EmployerPostingRepository postingRepository, EmployerRepository employerRepository, EmployeeRepository employeeRepository) {
        Assert.notNull(postingRepository, "Employer Posting Repository must not be null !");

        this.postingRepository = postingRepository;
        this.employerRepository = employerRepository;
        this.employeeRepository = employeeRepository;
    }

    public boolean createNewPost(EmployerPosting posting) {

        try {
            Employer employer = employerRepository.findByEmail(SecurityUtils.getCurrentLogin());

            posting.setStatus(0);
            posting.setEmployer(employer);

            postingRepository.saveAndFlush(posting);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updatePost(EmployerPosting posting) {
        try {
            Employer employer = employerRepository.findByEmail(SecurityUtils.getCurrentLogin());
            posting.setEmployer(employer);

            postingRepository.saveAndFlush(posting);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean deletePost(EmployerPosting posting) {
        try {
            postingRepository.delete(posting);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public List<EmployerPosting> findAllEmployerPosts() {
        List<EmployerPosting> postings = postingRepository.findAllEmployerPosts(SecurityUtils.getCurrentLogin());
        List<EmployerPosting> deletePostings = new ArrayList<>();
        Date currentDate = new Date();

        if (postings == null) {
            return new ArrayList<>();
        }

        postings.forEach(posting -> {

            if (posting.getEndDate().after(currentDate) && posting.getStartDate().before(currentDate) && posting.getEmployeePostings().size() > 0 && posting.getStatus() < 1) {

                posting.setStatus(1);
                postingRepository.saveAndFlush(posting);

            } else if (posting.getEndDate().after(currentDate) && posting.getStartDate().before(currentDate) && posting.getEmployeePostings().size() == 0 && posting.getStatus() < 1) {

                deletePostings.add(posting);

            } else if (posting.getEndDate().before(currentDate) && posting.getEmployeePostings().size() > 0 && posting.getStatus() < 2) {

                posting.setStatus(2);
                postingRepository.saveAndFlush(posting);

            } else if (posting.getEndDate().before(currentDate) && posting.getEmployeePostings().size() == 0 && posting.getStatus() < 2) {

                deletePostings.add(posting);

            }

        });

        deletePostings.forEach(posting -> {
            postings.remove(posting);
            postingRepository.delete(posting);
        });

        postings.forEach(posting -> {
            posting.setEmployer(null);

            if (posting.getEmployeePostings() != null && posting.getEmployeePostings().size() > 0) {
                hiddenEmployeePostingDetails(posting);
            }

        });

        return postings;
    }

    public List<EmployerPosting> findAllAvailablePostsForEmployee() {
        Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());

        if (employee == null) {
            return null;
        }

        List<EmployerPosting> postings = postingRepository.findAllAvailablePosts(employee.getJob().getJobId());
        postings.forEach(this::hiddenEmployerDetails);

        return postings;
    }

    public List<EmployerPosting> findAllAvailablePostsForEmployee(String name, Date startDate, Date endDate) {
        Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());

        if (employee == null) {
            return null;
        }

        List<EmployerPosting> postings = postingRepository.findAllAvailablePosts(employee.getJob().getJobId(), name, startDate, endDate);
        postings.forEach(this::hiddenEmployerDetails);

        return postings;
    }

    private void hiddenEmployerDetails(EmployerPosting posting) {
        Employer hiddenEmployer = new Employer();
        Employer employer = posting.getEmployer();

        hiddenEmployer.setEmail(employer.getEmail());
        hiddenEmployer.setPhoneNumber(employer.getPhoneNumber());
        hiddenEmployer.setDateOfBirth(employer.getDateOfBirth());
        hiddenEmployer.setFirstName(employer.getFirstName());
        hiddenEmployer.setLastName(employer.getLastName());
        hiddenEmployer.setReputation(employer.getReputation());
        hiddenEmployer.setProfilePicture(employer.getProfilePicture());
        hiddenEmployer.setReviewEmployer(employer.getReviewEmployer());

        posting.setEmployer(hiddenEmployer);
    }

    private void hiddenEmployeePostingDetails(EmployerPosting posting) {
        Set<EmployeePosting> employeePostings = posting.getEmployeePostings();
        Set<EmployeePosting> hiddenEmployeePostings = new HashSet<>();

        employeePostings.forEach(employeePosting -> {
            EmployeePosting hiddenEmployeePosting = new EmployeePosting();

            // Hidden Employee Details
            Employee hiddenEmployee = new Employee();
            Employee employee = employeePosting.getEmployee();

            hiddenEmployee.setEmployeeId(employee.getEmployeeId());
            hiddenEmployee.setEmail(employee.getEmail());
            hiddenEmployee.setPhoneNumber(employee.getPhoneNumber());
            hiddenEmployee.setDateOfBirth(employee.getDateOfBirth());
            hiddenEmployee.setFirstName(employee.getFirstName());
            hiddenEmployee.setLastName(employee.getLastName());
            hiddenEmployee.setReputation(employee.getReputation());
            hiddenEmployee.setPicture(employee.getPicture());

            hiddenEmployeePosting.setEmployee(hiddenEmployee);

            // Hidden Employee Posting Details
            hiddenEmployeePosting.setEmployeePostingId(employeePosting.getEmployeePostingId());
            hiddenEmployeePosting.setPrice(employeePosting.getPrice());
            hiddenEmployeePosting.setCurrency(employeePosting.getCurrency());
            hiddenEmployeePosting.setDate(employeePosting.getDate());
            hiddenEmployeePosting.setComment(employeePosting.getComment());
            hiddenEmployeePosting.setStatus(employeePosting.getStatus());

            hiddenEmployeePostings.add(hiddenEmployeePosting);
        });

        posting.setEmployeePostings(hiddenEmployeePostings);
    }

}
