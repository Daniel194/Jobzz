package ro.jobzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.EmployeePosting;

import java.util.List;

@Repository
public interface EmployeePostingRepository extends JpaRepository<EmployeePosting, Integer> {

    @SuppressWarnings("unchecked")
    EmployeePosting saveAndFlush(EmployeePosting posting);

    @Query("SELECT p from EmployeePosting p WHERE p.employee.email = ?1")
    List<EmployeePosting> findAllEmployeePosts(String email);

    void delete(EmployeePosting posting);

}
