angular.module('jobzz')
    .controller('EmployerProfileEmployeeCtrl', ['$scope', '$rootScope', '$http', 'employerProfileService',
        function ($scope, $rootScope, $http, employerProfileService) {

            $scope.employer = employerProfileService.getEmployer();
            $scope.responses = {};

            var getAllEmployerReviews = function () {

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

                }, function () {
                    console.log('Fail');
                });

            };

            getAllEmployerReviews();

        }]);