package ro.jobzz.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.Job;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.requests.entities.EmployeeRequest;

@Service
public class EmployeeService {

    private EmployeeRepository repository;
    private PasswordEncoder passwordEncoder;
    private JobService jobService;

    @Autowired
    public EmployeeService(EmployeeRepository repository, PasswordEncoder passwordEncoder, JobService jobService) {
        Assert.notNull(repository, "Employee Repository must not be null !");
        Assert.notNull(repository, "Password Encoder must not be null !");
        Assert.notNull(repository, "Job Repository must not be null !");

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jobService = jobService;
    }

    public boolean registerNewEmployeeAccount(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        Job job = jobService.findById(employeeRequest.getJobId());

        if (job == null) {
            return false;
        }

        try {
            employee.setCvv(employeeRequest.getCvv());
            employee.setExpirationDate(employeeRequest.getExpirationDate());
            employee.setCardNumber(employeeRequest.getCardNumber());
            employee.setDateOfBirth(employeeRequest.getDateOfBirth());
            employee.setEmail(employeeRequest.getEmail());
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setPhoneNumber(employeeRequest.getPhoneNumber());

            employee.setJob(job);
            employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
            employee.setReputation(0);

            repository.saveAndFlush(employee);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
