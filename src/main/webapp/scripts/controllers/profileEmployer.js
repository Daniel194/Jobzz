angular.module('jobzz')
    .controller('ProfileEmployerCtrl', ['$scope', '$rootScope', '$http', 'dateToStringService', 'userProfilePictureService',
        function ($scope, $rootScope, $http, dateToStringService, userProfilePictureService) {

            $scope.employer = {};
            $scope.responses = {};

            (function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/account/full',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.employer = response.data;
                    $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture(response.data.profilePicture);

                });

            })();

            (function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/all/reviews',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.responses = response.data;

                    $scope.responses.forEach(function (response) {
                        response.review.date = dateToStringService.dateToString(new Date(response.review.date));
                    });

                });

            })();

        }]);