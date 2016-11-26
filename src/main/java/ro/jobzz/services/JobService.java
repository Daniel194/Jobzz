package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Job;
import ro.jobzz.repositories.JobRepository;

import java.util.List;

@Service
public class JobService {

    private JobRepository repository;

    @Autowired
    public JobService(JobRepository repository) {
        Assert.notNull(repository, "Job Repository must not be null !");

        this.repository = repository;
    }

    public List<Job> findAll() {
        return repository.findAll();
    }

}
