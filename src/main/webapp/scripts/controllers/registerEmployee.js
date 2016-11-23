angular.module('jobzz')
    .controller('RegisterEmployeeCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {
        $scope.currentDate = new Date();

        $scope.minDate = new Date(
            $scope.currentDate.getFullYear() - 100,
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.maxDate = new Date(
            $scope.currentDate.getFullYear(),
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.minDateExp = new Date(
            $scope.currentDate.getFullYear(),
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        $scope.maxDateExp = new Date(
            $scope.currentDate.getFullYear() + 10,
            $scope.currentDate.getMonth(),
            $scope.currentDate.getDate());

        var getJobs = function () {
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
            }, function () {
                console.log('fail');
            });

        };

        getJobs();

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

                $http(req).then(function () {
                    console.log('success');
                }, function () {
                    console.log('fail');
                });

            }
        };

    }]);

