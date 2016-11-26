angular.module('jobzz').service('EmployerSession', function () {

    this.create = function (data) {
        this.employerId = data.employerId;
        this.email = data.email;
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.profilePicture = data.profilePicture;
        this.userRoles = ['EMPLOYER'];
    };

    this.invalidate = function () {
        this.employerId = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.profilePicture = null;
        this.userRoles = null;
    };

    return this;
});


angular.module('jobzz').service('EmployeeSession', function () {

    this.create = function (data) {
        this.employeeId = data.employeeId;
        this.email = data.email;
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.profilePicture = data.profilePicture;
        this.userRoles = ['EMPLOYEE'];
    };

    this.invalidate = function () {
        this.employeeId = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.profilePicture = null;
        this.userRoles = null;
    };

    return this;
});