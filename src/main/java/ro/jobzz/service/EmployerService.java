package ro.jobzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entity.Employer;
import ro.jobzz.repository.EmployerRepository;

import java.util.List;

@Service
public class EmployerService {

    private EmployerRepository repository;

    @Autowired
    public EmployerService(EmployerRepository repository) {
        Assert.notNull(repository, "Employer Repository must be not null !");

        this.repository = repository;
    }

    public List<Employer> findAll() {
        return repository.findAll();
    }

}
