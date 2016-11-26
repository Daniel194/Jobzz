package ro.jobzz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ro.jobzz.entities.Employee;
import ro.jobzz.repositories.EmployeeRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EmployeeRestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRestAuthenticationSuccessHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Employee employee = employeeRepository.findByEmail(authentication.getName());

        employee.setPassword(null);
        employee.setPhoneNumber(null);
        employee.setCardNumber(null);
        employee.setCvv(null);
        employee.setExpirationDate(null);
        employee.setDateOfBirth(null);
        employee.setReputation(null);
        employee.setEmployeePostings(null);
        employee.setReviewEmployee(null);
        employee.setJob(null);

        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, employee);
    }

}
