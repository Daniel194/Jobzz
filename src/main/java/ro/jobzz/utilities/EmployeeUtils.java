package ro.jobzz.utilities;


import ro.jobzz.entities.Employee;

public final class EmployeeUtils {

    private EmployeeUtils() {
        //Empty
    }

    public static void hiddenEmployeeDetails(Employee employee) {

        employee.setPassword(null);
        employee.setPhoneNumber(null);
        employee.setCardNumber(null);
        employee.setCvv(null);
        employee.setExpirationDate(null);
        employee.setDateOfBirth(null);
        employee.setReputation(null);
        employee.setEmployeePostings(null);
        employee.setReviewEmployee(null);
        employee.setJob(null);

    }

}
