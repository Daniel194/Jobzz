package ro.jobzz.entities;


import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class EmployerPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employerPostingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float longitude;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Integer jobId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Integer status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employerPosting")
    private Set<EmployeePosting> employeePostings;

    public EmployerPosting() {
    }

    public Integer getEmployerPostingId() {
        return employerPostingId;
    }

    public void setEmployerPostingId(Integer employerPostingId) {
        this.employerPostingId = employerPostingId;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<EmployeePosting> getEmployeePostings() {
        return employeePostings;
    }

    public void setEmployeePostings(Set<EmployeePosting> employeePostings) {
        this.employeePostings = employeePostings;
    }
}
