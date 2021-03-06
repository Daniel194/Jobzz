package ro.jobzz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.Employer;

import javax.transaction.Transactional;
import java.util.Date;
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

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Employer e SET e.email = ?2, e.firstName = ?3, e.lastName = ?4, e.phoneNumber = ?5, e.dateOfBirth = ?6" +
            " WHERE e.employerId = ?1")
    void updateGeneralInformation(Integer employerId, String email, String firstName, String lastName, String phoneNumber, Date dob);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Employer e SET e.cardNumber = ?2, e.expirationDate = ?3, e.cvv = ?4 " +
            " WHERE e.employerId = ?1")
    void updatePaymentInformation(Integer employerId, String cardNumber, Date expirationDate, String cvv);

}
