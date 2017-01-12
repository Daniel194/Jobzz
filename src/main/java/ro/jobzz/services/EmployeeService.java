package ro.jobzz.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ro.jobzz.entities.Employee;
import ro.jobzz.entities.Job;
import ro.jobzz.models.ChangePassword;
import ro.jobzz.repositories.EmployeeRepository;
import ro.jobzz.models.EmployeeRequest;
import ro.jobzz.repositories.JobRepository;
import ro.jobzz.security.SecurityUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeePostingService.class.getName());

    private EmployeeRepository repository;
    private PasswordEncoder passwordEncoder;
    private JobRepository jobRepository;

    @Autowired
    public EmployeeService(EmployeeRepository repository, PasswordEncoder passwordEncoder, JobRepository jobRepository) {
        Assert.notNull(repository, "Employee Repository must not be null !");
        Assert.notNull(repository, "Password Encoder must not be null !");
        Assert.notNull(repository, "Job Repository must not be null !");

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jobRepository = jobRepository;
    }

    public boolean registerNewEmployeeAccount(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        Job job = jobRepository.findByJobId(employeeRequest.getJobId());

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
            LOGGER.log(Level.WARNING, e.getMessage(), e);

            return false;
        }

        return true;
    }

    public Employee findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean updateGeneralInformation(Employee employee) {

        try {

            repository.updateGeneralInformation(employee.getEmployeeId(), employee.getEmail(), employee.getFirstName(),
                    employee.getLastName(), employee.getPhoneNumber(), employee.getDateOfBirth());


        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);

            return false;
        }

        return true;
    }

    public boolean updatePaymentInformation(Employee employee) {

        try {

            repository.updatePaymentInformation(employee.getEmployeeId(), employee.getCardNumber(), employee.getExpirationDate(),
                    employee.getCvv());


        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);

            return false;
        }


        return true;
    }

    public boolean changePassword(ChangePassword changePassword) {
        Employee employee = repository.findByEmail(SecurityUtils.getCurrentLogin());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!changePassword.getNewPassword().equals(changePassword.getRepeatNewPassword())) {
            return false;
        }

        if (encoder.matches(changePassword.getOldPassword(), employee.getPassword())) {
            String encodedPassword = encoder.encode(changePassword.getNewPassword());
            employee.setPassword(encodedPassword);
            repository.saveAndFlush(employee);

            return true;

        } else {
            return false;
        }

    }

    public String changeUserPicture(MultipartFile file) {
        String rootDirectory = System.getProperty("user.dir") + "/src/main/webapp/image/users/employee/";
        Employee employee = repository.findByEmail(SecurityUtils.getCurrentLogin());

        try {
            String fileName = employee.getEmployeeId() + "_" + file.getOriginalFilename();

            File newFile = new File(rootDirectory + fileName);
            file.transferTo(newFile);

            employee.setPicture(fileName);
            repository.saveAndFlush(employee);

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);

            return null;
        }

        return employee.getPicture();
    }

}
