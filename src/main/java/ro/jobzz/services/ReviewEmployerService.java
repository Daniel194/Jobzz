package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.ReviewEmployer;
import ro.jobzz.models.EmployerReviews;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.repositories.ReviewEmployerRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public List<EmployerReviews> getAllReviews() {
        Employer employer = employerRepository.findByEmail(SecurityUtils.getCurrentLogin());
        List<ReviewEmployer> reviewEmployers = repository.findAllReviewsByEmployerId(employer.getEmployerId());

        List<EmployerReviews> employerReviews = new ArrayList<>();
        List<Integer> employeeIds = new ArrayList<>();

        reviewEmployers.forEach(reviewEmployer -> employeeIds.add(reviewEmployer.getEmployeeId()));
        List<Employee> employees = employeeRepository.findByIdIn(employeeIds);

        reviewEmployers.forEach(reviewEmployer -> {
            reviewEmployer.setEmployer(null);
            Employee employee = employees.stream().filter(e -> e.getEmployeeId().equals(reviewEmployer.getEmployeeId()))
                    .findFirst().orElse(null);

            EmployerReviews review = new EmployerReviews();
            review.setReview(reviewEmployer);

            if (employee != null) {
                review.setEmployeeFullName(employee.getFirstName() + " " + employee.getLastName());
            }

            employerReviews.add(review);
        });

        return employerReviews;
    }

    private Integer calculateEmployeeReputation(Employer employer, Integer points) {
        Integer reputation = employer.getReputation() + points - 5;

        return reputation > 0 ? reputation : 0;
    }

}
