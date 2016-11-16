package ro.jobzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entity.Employer;
import ro.jobzz.entity.ReviewEmployer;

@Repository
public interface ReviewEmployerRepository extends JpaRepository<ReviewEmployer, Integer> {
}
