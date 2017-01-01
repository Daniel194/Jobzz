package ro.jobzz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.Employer;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    @SuppressWarnings("unchecked")
    Employer saveAndFlush(Employer employer);

    Employer findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Employer e SET e.reputation =:reputation WHERE e.employerId =:employerId")
    void updateReputation(@Param("employerId") Integer employerId, @Param("reputation") Integer reputation);

    @Query("SELECT e FROM Employer e WHERE e.employerId IN ?1")
    List<Employer> findByIdIn(List<Integer> employerIds);

    @Query("SELECT e FROM Employer e WHERE e.employerId = ?1")
    Employer findById(int employerId);

}
