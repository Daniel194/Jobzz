package ro.jobzz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        Assert.notNull(repository, "Employee Repository must not be null !");

        this.repository = repository;
    }
}
