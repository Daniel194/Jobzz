package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employer;
import ro.jobzz.entities.EmployerPosting;
import ro.jobzz.repositories.EmployerPostingRepository;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.security.SecurityUtils;

@Service
public class EmployerPostingService {

    private EmployerPostingRepository postingRepository;
    private EmployerRepository employerRepository;

    @Autowired
    public EmployerPostingService(EmployerPostingRepository postingRepository, EmployerRepository employerRepository) {
        Assert.notNull(postingRepository, "Employer Posting Repository must not be null !");

        this.postingRepository = postingRepository;
        this.employerRepository = employerRepository;
    }

    public boolean createNewPost(EmployerPosting posting) {

        try {
            Employer employer = employerRepository.findByEmail(SecurityUtils.getCurrentLogin());

            posting.setStatus(0);
            posting.setEmployer(employer);

            postingRepository.saveAndFlush(posting);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
