package ro.jobzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.repository.ReviewEmployerRepository;

@Service
public class ReviewEmployerService {

    private ReviewEmployerRepository repository;

    @Autowired
    public ReviewEmployerService(ReviewEmployerRepository repository) {
        Assert.notNull(repository, "Employer Repository must be not null !");

        this.repository = repository;
    }

}
