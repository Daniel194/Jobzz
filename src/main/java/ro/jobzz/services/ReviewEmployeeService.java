package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.ReviewEmployee;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.repositories.ReviewEmployeeRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.Date;

@Service
public class ReviewEmployeeService {

    private ReviewEmployeeRepository repository;
    private EmployerRepository employerRepository;

    @Autowired
    public ReviewEmployeeService(ReviewEmployeeRepository repository, EmployerRepository employerRepository) {
        Assert.notNull(repository, "Review Employee Repository must not be null !");

        this.repository = repository;
        this.employerRepository = employerRepository;
    }

    public boolean saveReview(ReviewEmployee review) {

        try {
            Employer employer = employerRepository.findByEmail(SecurityUtils.getCurrentLogin());
            Date currentDate = new Date();

            review.setDate(currentDate);
            review.setEmployerId(employer.getEmployerId());

            repository.saveAndFlush(review);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
