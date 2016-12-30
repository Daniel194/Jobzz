package ro.jobzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.ReviewEmployer;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewEmployerRepository extends JpaRepository<ReviewEmployer, Integer> {

    @SuppressWarnings("unchecked")
    ReviewEmployer saveAndFlush(ReviewEmployer review);

    @Query("SELECT MAX(r.date) FROM ReviewEmployer r WHERE r.employeeId = ?1 AND r.employer.employerId = ?2 ")
    Date reviewMaxDate(Integer employeeId, Integer employerId);

    @Query("SELECT r FROM ReviewEmployer r WHERE r.employer.employerId = ?1 ")
    List<ReviewEmployer> findAllReviewsByEmployerId(Integer employerId);

}
