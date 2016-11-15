package ro.jobzz.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entity.Employer;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    List<Employer> findAll();

}
