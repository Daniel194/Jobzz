package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employer;
import ro.jobzz.repositories.EmployerRepository;

@Service
public class EmployerService {

    private EmployerRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployerService(EmployerRepository repository, PasswordEncoder passwordEncoder) {
        Assert.notNull(repository, "Employer Repository must be not null !");
        Assert.notNull(repository, "Password Encoder must be not null !");

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerNewEmployerAccount(Employer employer) {

        try {
            employer.setPassword(passwordEncoder.encode(employer.getPassword()));
            employer.setReputation(0);

            repository.saveAndFlush(employer);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Employer findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean updateGeneralInformation(Employer employer) {

        try {

            repository.updateGeneralInformation(employer.getEmployerId(), employer.getEmail(), employer.getFirstName(),
                    employer.getLastName(), employer.getPhoneNumber(), employer.getDateOfBirth());


        } catch (Exception e) {
            return false;
        }

        return true;
    }


}
