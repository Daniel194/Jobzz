package ro.jobzz.entities;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Entity
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employerId;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^.+@.+\\..+$")
    @Size(min = 10, max = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^07[0-9]{8}$")
    @Size(max = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Size(min = 3, max = 30)
    private String firstName;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z]+$")
    @Size(min = 3, max = 30)
    private String lastName;

    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{16}$")
    @Size(max = 16)
    private String cardNumber;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(nullable = false)
    @Size(max = 3)
    @Pattern(regexp = "^[0-9]{3}$")
    private String cvv;

    @Column(nullable = false)
    private Integer reputation;

    private String profilePicture;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employer")
    private Set<ReviewEmployer> reviewEmployer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employer")
    private Set<EmployerPosting> employerPostings;

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<ReviewEmployer> getReviewEmployer() {
        return reviewEmployer;
    }

    public void setReviewEmployer(Set<ReviewEmployer> reviewEmployer) {
        this.reviewEmployer = reviewEmployer;
    }

    public Set<EmployerPosting> getEmployerPostings() {
        return employerPostings;
    }

    public void setEmployerPostings(Set<EmployerPosting> employerPostings) {
        this.employerPostings = employerPostings;
    }
}
