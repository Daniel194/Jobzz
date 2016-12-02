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
                console.log('fail');
            });

        };

        getJobs();


    }]);