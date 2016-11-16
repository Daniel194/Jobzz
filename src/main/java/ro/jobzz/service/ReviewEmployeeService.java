package ro.jobzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.repository.ReviewEmployerRepository;

@Service
public class ReviewEmployeeService {

    private ReviewEmployerRepository repository;

    @Autowired
    public ReviewEmployeeService(ReviewEmployerRepository repository) {
        Assert.notNull(repository, "Review Employee Repository must not be null !");

        this.repository = repository;
    }
}
