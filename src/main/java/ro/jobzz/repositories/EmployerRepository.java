package ro.jobzz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.Employer;

import javax.transaction.Transactional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    @SuppressWarnings("unchecked")
    Employer saveAndFlush(Employer employer);

    Employer findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Employer e SET e.reputation =:reputation WHERE e.employerId =:employerId")
    void updateReputation(@Param("employerId") Integer employerId, @Param("reputation") Integer reputation);

}
