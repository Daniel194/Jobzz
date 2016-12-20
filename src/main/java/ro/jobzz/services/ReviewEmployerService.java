package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.ReviewEmployer;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.repositories.ReviewEmployerRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class ReviewEmployerService {

    private ReviewEmployerRepository repository;
    private EmployeeRepository employeeRepository;
    private EmployerRepository employerRepository;

    @Autowired
    public ReviewEmployerService(ReviewEmployerRepository repository, EmployeeRepository employeeRepository, EmployerRepository employerRepository) {
        Assert.notNull(repository, "Review Repository must be not null !");

        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.employerRepository = employerRepository;
    }

    public boolean reviewEmployerPost(ReviewEmployer review) {

        try {
            Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());
            Date currentDate = new Date();
            Integer employerNewReputation = calculateEmployeeReputation(review.getEmployer(), review.getPoint());

            employerRepository.updateReputation(review.getEmployer().getEmployerId(), employerNewReputation);

            review.setDate(currentDate);
            review.setEmployeeId(employee.getEmployeeId());

            repository.saveAndFlush(review);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean allowNewReview(int employerId) {
        Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());
        Date currentDate = new Date();
        Date maxDate = repository.reviewMaxDate(employee.getEmployeeId(), employerId);

        if (maxDate != null) {
            long diff = currentDate.getTime() - maxDate.getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            return days > 1;
        }

        return true;
    }

    private Integer calculateEmployeeReputation(Employer employer, Integer points) {
        Integer reputation = employer.getReputation() + points - 5;

        return reputation > 0 ? reputation : 0;
    }

}
