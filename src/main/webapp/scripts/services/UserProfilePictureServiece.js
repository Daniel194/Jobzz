angular.module('jobzz').service('userProfilePictureService', function () {
    var defaultImage = "/image/user.png";

    var employerProfilePicture = function (imageName) {
        if (imageName === null) {
            return defaultImage;
        } else {
            var path = "/image/users/employer/";

            if (imageName.includes(path)) {
                return imageName;
            } else {
                return path + imageName;
            }
        }
    };

    var employeeProfilePicture = function (imageName) {
        if (imageName === null) {
            return defaultImage;
        } else {
            var path = "/image/users/employee/";

            if (imageName.includes(path)) {
                return imageName;
            } else {
                return path + imageName;
            }
        }
    };

    return {
        employerProfilePicture: employerProfilePicture,
        employeeProfilePicture: employeeProfilePicture
    };

});
