package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.repositories.EmployeePostingRepository;

@Service
public class EmployeePostingService {

    private EmployeePostingRepository repository;


    @Autowired
    public EmployeePostingService(EmployeePostingRepository repository) {
        Assert.notNull(repository, "Employee Posting Repository must not be null !");

        this.repository = repository;
    }
}
