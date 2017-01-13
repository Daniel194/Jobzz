package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ro.jobzz.entities.Employer;
import ro.jobzz.models.ChangePassword;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.security.SecurityUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmployerService {

    private static final Logger LOGGER = Logger.getLogger(EmployeePostingService.class.getName());

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
            LOGGER.log(Level.WARNING, e.getMessage(), e);

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
            LOGGER.log(Level.WARNING, e.getMessage(), e);

            return false;
        }

        return true;
    }

    public boolean updatePaymentInformation(Employer employer) {

        try {

            repository.updatePaymentInformation(employer.getEmployerId(), employer.getCardNumber(), employer.getExpirationDate(),
                    employer.getCvv());


        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);

            return false;
        }


        return true;
    }

    public boolean changePassword(ChangePassword changePassword) {
        Employer employer = repository.findByEmail(SecurityUtils.getCurrentLogin());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(changePassword.getOldPassword(), employer.getPassword())) {

            if (!changePassword.getNewPassword().equals(changePassword.getRepeatNewPassword())) {
                return false;
            }

            String encodedPassword = encoder.encode(changePassword.getNewPassword());
            employer.setPassword(encodedPassword);
            repository.saveAndFlush(employer);

            return true;

        } else {
            return false;
        }

    }

    public String changeUserPicture(MultipartFile file) {
        String rootDirectory = System.getProperty("user.dir") + "/src/main/webapp/image/users/employer/";
        Employer employer = repository.findByEmail(SecurityUtils.getCurrentLogin());

        try {
            String fileName = employer.getEmployerId() + "_" + file.getOriginalFilename();

            File newFile = new File(rootDirectory + fileName);
            file.transferTo(newFile);

            employer.setProfilePicture(fileName);
            repository.saveAndFlush(employer);

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);

            return null;
        }

        return employer.getProfilePicture();
    }

}
