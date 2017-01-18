package ro.jobzz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.jobzz.entities.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Override
    List<Job> findAll();

    Job findByJobId(Integer jobId);

}
