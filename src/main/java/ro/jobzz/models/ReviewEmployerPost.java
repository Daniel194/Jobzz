package ro.jobzz.models;


import ro.jobzz.entities.EmployeePosting;
import ro.jobzz.entities.ReviewEmployer;

public class ReviewEmployerPost {

    private ReviewEmployer review;

    private EmployeePosting employeePosting;

    public ReviewEmployer getReview() {
        return review;
    }

    public void setReview(ReviewEmployer review) {
        this.review = review;
    }

    public EmployeePosting getEmployeePosting() {
        return employeePosting;
    }

    public void setEmployeePosting(EmployeePosting employeePosting) {
        this.employeePosting = employeePosting;
    }
}
