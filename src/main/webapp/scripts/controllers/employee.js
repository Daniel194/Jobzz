angular.module('jobzz')
    .controller('EmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {
        $scope.currentNavItem = 'home';

        $scope.logoutEmployee = function () {
            $http.post('/employee/logout', {}).finally(function () {
                EmployeeAuthSharedService.logout();
            });
        };

    }])
    .controller('HomeEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {

        var getJobs = function () {
            var req = {
                method: 'GET',
                dataType: 'json',
                url: '/employee/all/post',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            };

            $http(req).then(function (response) {
                $scope.jobsWaiting = response.data.jobsWaiting;
                $scope.jobsProgress = response.data.jobsProgress;
                $scope.jobsDone = response.data.jobsDone;
            }, function () {
                console.log('fail');
            });

        };

        getJobs();

    }])
    .controller('SettingsEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {


    }])
    .controller('ProfileEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {


    }])
    .controller('FindJobEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {
        var currentDate = new Date();
        $scope.job = {};

        $scope.minDate = new Date(
            currentDate.getFullYear(),
            currentDate.getMonth(),
            currentDate.getDate());

        $scope.maxDate = new Date(
            currentDate.getFullYear() + 10,
            currentDate.getMonth(),
            currentDate.getDate());

        $scope.job.startDate = $scope.minDate;
        $scope.job.endDate = $scope.maxDate;

        var getJobs = function () {
            var req = {
                method: 'GET',
                dataType: 'json',
                url: '/employee/all/available/jobs',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            };

            $http(req).then(function (response) {

                $scope.jobs = response.data;

            }, function () {
                console.log('Fail to load the Jobs');
            });

        };

        getJobs();

        $scope.findJob = function () {

            var req = {
                method: 'POST',
                dataType: 'json',
                url: '/employee/find/available/jobs',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                },
                data: $scope.job
            };

            $http(req).then(function (response) {

                $scope.jobs = response.data;

            }, function (response) {
                console.log('Fail to find Jobs');
            });

        };

    }]);