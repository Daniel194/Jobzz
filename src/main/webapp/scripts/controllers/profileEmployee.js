angular.module('jobzz')
    .controller('ProfileEmployeeCtrl', ['$scope', '$rootScope', '$http', 'dateToStringService',
        function ($scope, $rootScope, $http, dateToStringService) {

            $scope.employee = {};
            $scope.responses = {};

            var getAllEmployeeDetails = function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/account/full',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.employee = response.data;

                }, function () {
                    console.log('Fail');
                });

            };

            var getAllEmployeeReviews = function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/all/reviews',
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

            getAllEmployeeDetails();

            getAllEmployeeReviews();

        }]);