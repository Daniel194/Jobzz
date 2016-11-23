package ro.jobzz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    @SuppressWarnings("unchecked")
    Employer saveAndFlush(Employer employer);

    Employer findByEmail(String email);

}
