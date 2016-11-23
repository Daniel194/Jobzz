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
import ro.jobzz.entities.Employer;
import ro.jobzz.repositories.EmployerRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployerDetailsService implements UserDetailsService {

    private EmployerRepository repository;

    @Autowired
    public EmployerDetailsService(EmployerRepository repository) {
        Assert.notNull(repository, "Employer Repository must be not null !");
        Assert.notNull(repository, "Password Encoder must be not null !");

        this.repository = repository;
    }

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employer employer = repository.findByEmail(email);

        List<GrantedAuthority> authorities = buildUserAuthority();

        return buildUserForAuthentication(employer, authorities);
    }

    private User buildUserForAuthentication(Employer employer, List<GrantedAuthority> authorities) {
        return new User(employer.getEmail(), employer.getPassword(), true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority() {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority("EMPLOYER"));

        return new ArrayList<>(setAuths);
    }

}
