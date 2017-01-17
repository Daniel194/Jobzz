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
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import ro.jobzz.security.*;
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
        private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;
        private RestAccessDeniedHandler restAccessDeniedHandler;
        private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
        private EmployerRestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

        @Autowired
        public EmployerSecurityConfiguration(EmployerDetailsService employerDetailsService, PasswordEncoder passwordEncoder, RestUnauthorizedEntryPoint restAuthenticationEntryPoint, RestAccessDeniedHandler restAccessDeniedHandler, RestAuthenticationFailureHandler restAuthenticationFailureHandler, EmployerRestAuthenticationSuccessHandler restAuthenticationSuccessHandler) {
            this.employerDetailsService = employerDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
            this.restAccessDeniedHandler = restAccessDeniedHandler;
            this.restAuthenticationFailureHandler = restAuthenticationFailureHandler;
            this.restAuthenticationSuccessHandler = restAuthenticationSuccessHandler;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/", "/index.html", "/views/login.html", "/views/register/**", "/components/**", "/fonts/**",
                            "/scripts/**", "/styles/**", "/register/**", "/error/**", "/views/loading.html", "/views/error.html");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(employerDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/employer/**")
                    .headers().disable()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/employer/**", "/views/employer/**").hasAnyAuthority("EMPLOYER")
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .accessDeniedHandler(restAccessDeniedHandler)
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/employer/login")
                    .successHandler(restAuthenticationSuccessHandler)
                    .failureHandler(restAuthenticationFailureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/employer/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                    .deleteCookies("JSESSIONID")
                    .permitAll();

        }
    }

    @Configuration
    @Order(2)
    public static class EmployeeSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private EmployeeDetailsService employeeDetailsService;
        private PasswordEncoder passwordEncoder;
        private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;
        private RestAccessDeniedHandler restAccessDeniedHandler;
        private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
        private EmployeeRestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

        @Autowired
        public EmployeeSecurityConfiguration(EmployeeDetailsService employeeDetailsService, PasswordEncoder passwordEncoder, RestUnauthorizedEntryPoint restAuthenticationEntryPoint, RestAccessDeniedHandler restAccessDeniedHandler, RestAuthenticationFailureHandler restAuthenticationFailureHandler, EmployeeRestAuthenticationSuccessHandler restAuthenticationSuccessHandler) {
            this.employeeDetailsService = employeeDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
            this.restAccessDeniedHandler = restAccessDeniedHandler;
            this.restAuthenticationFailureHandler = restAuthenticationFailureHandler;
            this.restAuthenticationSuccessHandler = restAuthenticationSuccessHandler;
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
                            "/scripts/**", "/styles/**", "/register/**", "/error/**", "/views/loading.html", "/views/error.html");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/employee/**")
                    .headers().disable()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/employee/**", "/views/employee/**").hasAnyAuthority("EMPLOYEE")
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .accessDeniedHandler(restAccessDeniedHandler)
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/employee/login")
                    .successHandler(restAuthenticationSuccessHandler)
                    .failureHandler(restAuthenticationFailureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/employee/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                    .deleteCookies("JSESSIONID")
                    .permitAll();

        }
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
