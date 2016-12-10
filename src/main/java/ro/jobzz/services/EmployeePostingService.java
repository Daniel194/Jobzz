package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.repositories.EmployeePostingRepository;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeePostingService {

    private EmployeePostingRepository postingRepository;
    private EmployeeRepository employeeRepository;


    @Autowired
    public EmployeePostingService(EmployeePostingRepository postingRepository, EmployeeRepository employeeRepository) {
        Assert.notNull(postingRepository, "Employee Posting Repository must not be null !");

        this.postingRepository = postingRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeePosting> getAllEmployeePost() {
        List<EmployeePosting> postings = postingRepository.findAllEmployeePosts(SecurityUtils.getCurrentLogin());
        List<EmployeePosting> deletePostings = new ArrayList<>();
        Date currentDate = new Date();

        postings.forEach(post -> {

            if (post.getStatus() == 0 && post.getEmployerPosting().getStartDate().before(currentDate)) {
                deletePostings.add(post);
            } else if (post.getStatus() == 1 && post.getEmployerPosting().getStartDate().before(currentDate)) {
                post.setStatus(3);
                postingRepository.saveAndFlush(post);
            } else if (post.getStatus() == 2 && post.getEmployerPosting().getStartDate().before(currentDate)) {
                deletePostings.add(post);
            }

            if (post.getStatus() == 3 && post.getEmployerPosting().getEndDate().before(currentDate)) {
                post.setStatus(5);
                postingRepository.saveAndFlush(post);
            }

        });

        deletePostings.forEach(post -> {
            postings.remove(post);
            postingRepository.delete(post);
        });

        return postings;
    }

    public boolean createPost(EmployeePosting post) {

        try {
            Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());
            Date currentDate = new Date();

            post.setStatus(0);
            post.setEmployee(employee);
            post.setDate(currentDate);

            postingRepository.saveAndFlush(post);
        } catch (Exception e) {
            return false;
        }

        return true;

    }

}
