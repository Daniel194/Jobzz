package ro.jobzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.ReviewEmployee;

import java.util.List;

@Repository
public interface ReviewEmployeeRepository extends JpaRepository<ReviewEmployee, Integer> {

    @SuppressWarnings("unchecked")
    ReviewEmployee saveAndFlush(ReviewEmployee review);

    @Query("SELECT r FROM ReviewEmployee r WHERE r.employee.employeeId = ?1 ")
    List<ReviewEmployee> findAllReviewsByEmployeeId(Integer employeeId);

}
