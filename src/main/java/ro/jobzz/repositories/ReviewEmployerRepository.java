package ro.jobzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.ReviewEmployer;

import java.util.Date;

@Repository
public interface ReviewEmployerRepository extends JpaRepository<ReviewEmployer, Integer> {

    @SuppressWarnings("unchecked")
    ReviewEmployer saveAndFlush(ReviewEmployer review);

    @Query("SELECT MAX(r.date) FROM ReviewEmployer r WHERE r.employeeId = ?1 ")
    Date reviewMaxDate(Integer employeeId);

}
