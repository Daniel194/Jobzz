package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.repositories.EmployeePostingRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeePostingService {

    private EmployeePostingRepository repository;


    @Autowired
    public EmployeePostingService(EmployeePostingRepository repository) {
        Assert.notNull(repository, "Employee Posting Repository must not be null !");

        this.repository = repository;
    }

    public List<EmployeePosting> getAllEmployeePost() {
        List<EmployeePosting> postings = repository.findAllEmployeePosts(SecurityUtils.getCurrentLogin());
        List<EmployeePosting> deletePostings = new ArrayList<>();
        Date currentDate = new Date();

        postings.forEach(post -> {

            if (post.getStatus() == 0 && post.getEmployerPosting().getStartDate().before(currentDate)) {
                deletePostings.add(post);
            } else if (post.getStatus() == 1 && post.getEmployerPosting().getStartDate().before(currentDate)) {
                post.setStatus(3);
                repository.saveAndFlush(post);
            } else if (post.getStatus() == 2 && post.getEmployerPosting().getStartDate().before(currentDate)) {
                deletePostings.add(post);
            }

            if (post.getStatus() == 3 && post.getEmployerPosting().getEndDate().before(currentDate)) {
                post.setStatus(5);
                repository.saveAndFlush(post);
            }

        });

        deletePostings.forEach(post -> {
            postings.remove(post);
            repository.delete(post);
        });

        return postings;
    }

}
