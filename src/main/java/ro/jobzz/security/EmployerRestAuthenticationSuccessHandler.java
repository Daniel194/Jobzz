package ro.jobzz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ro.jobzz.entities.Employer;
import ro.jobzz.repositories.EmployerRepository;
import ro.jobzz.utilities.EmployerUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EmployerRestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private EmployerRepository employerRepository;

    @Autowired
    public EmployerRestAuthenticationSuccessHandler(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Employer employer = employerRepository.findByEmail(authentication.getName());
        EmployerUtils.hiddenEmployerDetails(employer);

        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, employer);
    }

}
