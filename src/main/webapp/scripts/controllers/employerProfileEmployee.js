angular.module('jobzz')
    .controller('EmployerProfileEmployeeCtrl', ['$scope', '$http', 'employerProfileService', 'userProfilePictureService',
        'dateToStringService',
        function ($scope, $http, employerProfileService, userProfilePictureService, dateToStringService) {
            $scope.responses = {};

            (function () {

                $scope.employer = employerProfileService.getEmployer();
                $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture($scope.employer.profilePicture);

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/employer/' + $scope.employer.employerId,
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