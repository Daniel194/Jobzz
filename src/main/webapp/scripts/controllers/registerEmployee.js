angular.module('jobzz')
    .controller('RegisterEmployeeCtrl', ['$scope', '$rootScope', '$http', '$location', 'intervalDateForCalendarsService',
        function ($scope, $rootScope, $http, $location, intervalDateForCalendarsService) {

            var dates = intervalDateForCalendarsService.getDates();

            $scope.error = false;
            $scope.errorMessage = "";
            $scope.minDate = dates.minDate;
            $scope.maxDate = dates.maxDate;
            $scope.minDateExp = dates.minDateExp;
            $scope.maxDateExp = dates.maxDateExp;

            (function () {
                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/register/get/jobs',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.jobs = response.data.jobs;
                });

            })();

            $scope.newAccount = function () {
                if ($scope.employee.password === $scope.employee.repeatPassword) {

                    var req = {
                        method: 'POST',
                        dataType: 'json',
                        url: '/register/employee',
                        headers: {
                            'Content-Type': 'application/json; charset=utf-8'
                        },
                        data: $scope.employee
                    };

                    $http(req).then(function (response) {

                        if (response.data.isCreated) {
                            $rootScope.isCreated = true;
                            $location.path('/login').replace();
                        } else {
                            $scope.error = true;
                            $scope.errorMessage = $scope.employee.email + " already exist !";
                        }

                    }, function () {
                        $scope.error = true;
                        $scope.errorMessage = "A problem has happened during recording. Please try again.";
                    });

                }
            };

        }]);