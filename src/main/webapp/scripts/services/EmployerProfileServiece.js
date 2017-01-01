angular.module('jobzz').service('employerProfileService', function () {
    var employer = {};

    var setEmployer = function (newObj) {
        employer = newObj;
    };

    var getEmployer = function () {
        return employer;
    };

    return {
        setEmployer: setEmployer,
        getEmployer: getEmployer
    };

});
