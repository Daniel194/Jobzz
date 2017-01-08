angular.module('jobzz').service('userProfilePictureService', function () {
    var defaultImage = "/image/user.png";

    var employerProfilePicture = function (imageName) {
        if (imageName === null) {
            return defaultImage;
        } else {
            return "/image/users/employer/" + imageName;
        }
    };

    var employeeProfilePicture = function (imageName) {
        if (imageName === null) {
            return defaultImage;
        } else {
            return "/image/users/employee/" + imageName;
        }
    };

    return {
        employerProfilePicture: employerProfilePicture,
        employeeProfilePicture: employeeProfilePicture
    };

});
