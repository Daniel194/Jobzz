package ro.jobzz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.Employer;

@Repository
public interface EmployeeRepository extends JpaRepository<Employer, Integer> {
}
