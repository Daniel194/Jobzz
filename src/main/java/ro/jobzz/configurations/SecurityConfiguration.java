package ro.jobzz.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import ro.jobzz.services.EmployeeDetailsService;
import ro.jobzz.services.EmployerDetailsService;

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Configuration
    @Order(1)
    public static class EmployerSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private EmployerDetailsService employerDetailsService;
        private PasswordEncoder passwordEncoder;

        @Autowired
        public EmployerSecurityConfiguration(EmployerDetailsService employerDetailsService, PasswordEncoder passwordEncoder) {
            this.employerDetailsService = employerDetailsService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/", "/index.html", "/views/login.html", "/views/register/**", "/components/**", "/fonts/**",
                            "/scripts/**", "/styles/**", "/register/**");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(employerDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/employer/**")
                    .authorizeRequests()
                    .antMatchers("/employer/**", "/view/employer/**").hasRole("EMPLOYER")
                    .and()
                    .formLogin()
                    .loginPage("/employer/login").permitAll().failureUrl("/employer/login?error")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                    .logout().logoutUrl("/employer/logout").permitAll().logoutSuccessUrl("/employer/login?logout")
                    .and()
                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                    .exceptionHandling().accessDeniedPage("/403")
                    .and()
                    .httpBasic();

        }
    }

    @Configuration
    @Order(2)
    public static class EmployeeSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private EmployeeDetailsService employeeDetailsService;
        private PasswordEncoder passwordEncoder;

        @Autowired
        public EmployeeSecurityConfiguration(EmployeeDetailsService employeeDetailsService, PasswordEncoder passwordEncoder) {
            this.employeeDetailsService = employeeDetailsService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(employeeDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/", "/index.html", "/views/login.html", "/views/register/**", "/components/**", "/fonts/**",
                            "/scripts/**", "/styles/**", "/register/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/employee/**")
                    .authorizeRequests()
                    .antMatchers("/employee/**", "/view/employee/**").hasRole("EMPLOYEE")
                    .and()
                    .formLogin()
                    .loginPage("/employee/login").permitAll().failureUrl("/employee/login?error")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                    .logout().logoutUrl("/employee/logout").permitAll().logoutSuccessUrl("/employee/login?logout")
                    .and()
                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                    .exceptionHandling().accessDeniedPage("/403")
                    .and()
                    .httpBasic();

        }
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
