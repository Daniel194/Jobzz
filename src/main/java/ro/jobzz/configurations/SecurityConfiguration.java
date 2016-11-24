package ro.jobzz.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import ro.jobzz.services.EmployerDetailsService;

@Configuration
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

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(employerDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/employer/**", "/view/employer/**").access("hasRole('EMPLOYER')")
                    .and()
                    .formLogin()
                    .loginPage("/login/employer").failureUrl("/login/employer?error")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                    .logout().logoutSuccessUrl("/login/employer?logout")
                    .and()
                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                    .exceptionHandling().accessDeniedPage("/403");

        }
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
