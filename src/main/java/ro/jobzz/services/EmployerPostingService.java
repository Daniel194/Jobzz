package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.repositories.EmployerPostingRepository;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        return postings;
    }

    public List<EmployerPosting> findAllAvailablePostsForEmployee() {
        Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());

        if (employee == null) {
            return null;
        }

        return postingRepository.findAllAvailablePosts(employee.getJob().getJobId());
    }

    public List<EmployerPosting> findAllAvailablePostsForEmployee(String name, Date startDate, Date endDate) {
        Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());

        if (employee == null) {
            return null;
        }

        return postingRepository.findAllAvailablePosts(employee.getJob().getJobId(), name, startDate, endDate);
    }

}
