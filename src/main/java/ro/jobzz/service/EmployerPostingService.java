package ro.jobzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.repository.EmployerPostingRepository;

@Service
public class EmployerPostingService {

    private EmployerPostingRepository repository;

    @Autowired
    public EmployerPostingService(EmployerPostingRepository repository) {
        Assert.notNull(repository, "Employer Posting Repository must not be null !");

        this.repository = repository;
    }
}
