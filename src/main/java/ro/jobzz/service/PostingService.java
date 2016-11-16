package ro.jobzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.repository.PostingRepository;

@Service
public class PostingService {

    private PostingRepository repository;

    @Autowired
    public PostingService(PostingRepository repository) {
        Assert.notNull(repository, "Posting Repository must not be null !");

        this.repository = repository;
    }
}
