package ro.jobzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entity.Posting;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Integer> {
}
