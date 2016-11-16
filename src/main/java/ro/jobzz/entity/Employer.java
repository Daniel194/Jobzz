package ro.jobzz.entity;


import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;
import java.util.Set;

@Entity
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employerId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column(nullable = false)
    private Integer reputation;

    private Blob profilePicture;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employer")
    private Set<ReviewEmployer> reviewEmployer;

    public Employer() {
    }

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

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<ReviewEmployer> getReviewEmployer() {
        return reviewEmployer;
    }

    public void setReviewEmployer(Set<ReviewEmployer> reviewEmployer) {
        this.reviewEmployer = reviewEmployer;
    }
}
