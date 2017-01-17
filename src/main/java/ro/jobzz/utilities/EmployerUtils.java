package ro.jobzz.utilities;


import ro.jobzz.entities.Employer;

public final class EmployerUtils {

    private EmployerUtils() {
        //Empty
    }

    public static void hiddenEmployerDetails(Employer employer) {

        employer.setPassword(null);
        employer.setPhoneNumber(null);
        employer.setCardNumber(null);
        employer.setCvv(null);
        employer.setExpirationDate(null);
        employer.setDateOfBirth(null);
        employer.setReputation(null);
        employer.setEmployerPostings(null);
        employer.setReviewEmployer(null);

    }

}
