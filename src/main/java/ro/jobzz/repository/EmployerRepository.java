package ro.jobzz.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ro.jobzz.model.Employer;

import java.util.List;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    List<Employer> findAll();

}
