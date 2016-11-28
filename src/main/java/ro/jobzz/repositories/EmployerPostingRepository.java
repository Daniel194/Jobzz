package ro.jobzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.EmployerPosting;

@Repository
public interface EmployerPostingRepository extends JpaRepository<EmployerPosting, Integer> {

    @SuppressWarnings("unchecked")
    EmployerPosting saveAndFlush(EmployerPosting posting);

}
