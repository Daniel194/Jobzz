package ro.jobzz.configurations;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/",
                        "/index.html",
                        "/views/login.html",
                        "/views/create-account-employee.html",
                        "/views/create-account-employer.html",
                        "/styles/main.css",
                        "/scripts/app.js",
                        "/scripts/controllers/login.js",
                        "/scripts/controllers/createAccountEmployee.js",
                        "/scripts/controllers/createAccountEmployer.js",
                        "/components/**",
                        "/fonts/**")
                .permitAll().anyRequest()
                .authenticated().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
