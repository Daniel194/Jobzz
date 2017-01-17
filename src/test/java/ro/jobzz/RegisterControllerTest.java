package ro.jobzz;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ro.jobzz.controllers.RegisterController;
import ro.jobzz.services.EmployeePostingService;
import ro.jobzz.services.EmployeeService;
import ro.jobzz.services.EmployerService;
import ro.jobzz.services.JobService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterControllerTest {

    private static final Logger LOGGER = Logger.getLogger(EmployeePostingService.class.getName());

    private MockMvc mockMvc;

    @Before
    public void setup() {
        JobService jobServices = Mockito.mock(JobService.class);
        EmployerService employerService = Mockito.mock(EmployerService.class);
        EmployeeService employeeService = Mockito.mock(EmployeeService.class);

        this.mockMvc = standaloneSetup(new RegisterController(jobServices, employerService, employeeService)).build();
    }

    @Test
    public void testGetJobs() {
        try {
            mockMvc.perform(get("/register/get/jobs").accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        } catch (Exception e) {

            LOGGER.log(Level.WARNING, e.getMessage(), e);
            
        }
    }

}
