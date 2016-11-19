package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.repositories.EmployerRepository;

@Service
public class EmployerService {

    private EmployerRepository repository;

    @Autowired
    public EmployerService(EmployerRepository repository) {
        Assert.notNull(repository, "Employer Repository must be not null !");

        this.repository = repository;
    }

}
