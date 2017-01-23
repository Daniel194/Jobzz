angular.module('jobzz').service('profileService', function ($http, dateToStringService, userProfilePictureService) {

    function getAllReview(url) {

        var req = {
            method: 'GET',
            dataType: 'json',
            url: url,
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        return $http(req).then(function (response) {
            var reviews = response.data;

            reviews.forEach(function (review) {
                review.review.date = dateToStringService.dateToString(new Date(review.review.date));
            });

            return reviews;

        });

    }

    function getFullAccount(ulr) {

        var req = {
            method: 'GET',
            dataType: 'json',
            url: ulr,
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        };

        return $http(req).then(function (response) {
            var account = response.data;

            if (account.picture != undefined) {
                account.picture = userProfilePictureService.employeeProfilePicture(response.data.picture);
            } else {
                account.profilePicture = userProfilePictureService.employerProfilePicture(response.data.profilePicture);
            }

            return account;
        });

    }

    return {
        getFullAccount: getFullAccount,
        getAllReview: getAllReview
    };

});
