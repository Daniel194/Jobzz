package ro.jobzz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ro.jobzz.entities.Employee;
import ro.jobzz.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeDetailsService(EmployeeRepository repository) {
        Assert.notNull(repository, "Employee Repository must be not null !");

        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = repository.findByEmail(email);

        List<GrantedAuthority> authorities = buildUserAuthority();

        return buildUserForAuthentication(employee, authorities);
    }

    private User buildUserForAuthentication(Employee employer, List<GrantedAuthority> authorities) {
        return new User(employer.getEmail(), employer.getPassword(), true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority() {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority("EMPLOYEE"));

        return new ArrayList<>(setAuths);
    }

}
