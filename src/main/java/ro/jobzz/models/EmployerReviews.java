package ro.jobzz.models;

import ro.jobzz.entities.ReviewEmployer;

public class EmployerReviews {

    private String employeeFullName;

    private ReviewEmployer review;

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public ReviewEmployer getReview() {
        return review;
    }

    public void setReview(ReviewEmployer review) {
        this.review = review;
    }
}
