angular.module('jobzz').service('employeeProfileService', function () {
    var employee = {};

    var setEmployee = function (newObj) {
        employee = newObj;
    };

    var getEmployee = function () {
        return employee;
    };

    return {
        setEmployee: setEmployee,
        getEmployee: getEmployee
    };

});
