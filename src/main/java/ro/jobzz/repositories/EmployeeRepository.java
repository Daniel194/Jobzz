package ro.jobzz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.jobzz.entities.Employee;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @SuppressWarnings("unchecked")
    Employee saveAndFlush(Employee employee);

    Employee findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Employee e SET e.reputation =:reputation WHERE e.employeeId =:employeeId")
    void updateReputation(@Param("employeeId") Integer employeeId, @Param("reputation") Integer reputation);

    @Query("SELECT e FROM Employee e WHERE e.employeeId IN ?1")
    List<Employee> findByIdIn(List<Integer> employeeIds);

    @Query("SELECT e FROM Employee e WHERE e.employeeId = ?1")
    Employee findById(Integer employeeId);

    @Modifying(clearAutomatically = true)
    @javax.transaction.Transactional
    @Query("UPDATE Employee e SET e.email = ?2, e.firstName = ?3, e.lastName = ?4, e.phoneNumber = ?5, e.dateOfBirth = ?6" +
            " WHERE e.employeeId = ?1")
    void updateGeneralInformation(Integer employeeId, String email, String firstName, String lastName, String phoneNumber, Date dob);

}
