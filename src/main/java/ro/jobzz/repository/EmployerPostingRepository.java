package ro.jobzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entity.EmployerPosting;

@Repository
public interface EmployerPostingRepository extends JpaRepository<EmployerPosting, Integer> {
}
