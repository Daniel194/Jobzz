package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.ReviewEmployee;
import ro.jobzz.models.EmployeeReviews;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.repositories.ReviewEmployeeRepository;
import ro.jobzz.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewEmployeeService {

    private ReviewEmployeeRepository repository;
    private EmployerRepository employerRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public ReviewEmployeeService(ReviewEmployeeRepository repository, EmployerRepository employerRepository, EmployeeRepository employeeRepository) {
        Assert.notNull(repository, "Review Employee Repository must not be null !");

        this.repository = repository;
        this.employerRepository = employerRepository;
        this.employeeRepository = employeeRepository;
    }

    public boolean saveReview(ReviewEmployee review) {

        try {
            Employer employer = employerRepository.findByEmail(SecurityUtils.getCurrentLogin());
            Date currentDate = new Date();
            Integer employeeNewReputation = calculateEmployeeReputation(review.getEmployee(), review.getPoint());

            employeeRepository.updateReputation(review.getEmployee().getEmployeeId(), employeeNewReputation);

            review.setDate(currentDate);
            review.setEmployerId(employer.getEmployerId());

            repository.saveAndFlush(review);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public List<EmployeeReviews> getAllReviews() {
        Employee employee = employeeRepository.findByEmail(SecurityUtils.getCurrentLogin());
        List<ReviewEmployee> reviewEmployees = repository.findAllReviewsByEmployeeId(employee.getEmployeeId());

        List<EmployeeReviews> employeeReviews = new ArrayList<>();
        List<Integer> employerIds = new ArrayList<>();

        reviewEmployees.forEach(reviewEmployee -> employerIds.add(reviewEmployee.getEmployerId()));
        List<Employer> employers = employerRepository.findByIdIn(employerIds);

        reviewEmployees.forEach(reviewEmployee -> {
            reviewEmployee.setEmployee(null);
            Employer employer = employers.stream().filter(e -> e.getEmployerId().equals(reviewEmployee.getEmployerId()))
                    .findFirst().orElse(null);

            EmployeeReviews review = new EmployeeReviews();
            review.setReview(reviewEmployee);

            if (employer != null) {
                review.setEmployerFullName(employer.getFirstName() + " " + employer.getLastName());
            }

            employeeReviews.add(review);
        });

        return employeeReviews;
    }

    private Integer calculateEmployeeReputation(Employee employee, Integer points) {
        Integer reputation = employee.getReputation() + points - 5;

        return reputation > 0 ? reputation : 0;
    }

}
