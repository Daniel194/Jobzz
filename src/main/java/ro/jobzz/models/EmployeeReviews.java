package ro.jobzz.models;


import ro.jobzz.entities.ReviewEmployee;

public class EmployeeReviews {

    private String employerFullName;
    private ReviewEmployee review;

    public String getEmployerFullName() {
        return employerFullName;
    }

    public void setEmployerFullName(String employerFullName) {
        this.employerFullName = employerFullName;
    }

    public ReviewEmployee getReview() {
        return review;
    }

    public void setReview(ReviewEmployee review) {
        this.review = review;
    }
}
