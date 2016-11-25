angular.module('jobzz')
    .controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {

        var authenticateEmployer = function (credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.email + ":" + credentials.password)
            } : {};

            $http.get('/employer/login', {headers: headers}).then(function (response) {

                $rootScope.authenticated.employer = !!response.data.name;

                callback && callback();
            }, function () {
                $rootScope.authenticated.employer = false;
                callback && callback();
            });

        };

        var authenticateEmployee = function (credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.email + ":" + credentials.password)
            } : {};

            $http.get('/employee/login', {headers: headers}).then(function (response) {

                $rootScope.authenticated.employee = !!response.data.name;

                callback && callback();
            }, function () {
                $rootScope.authenticated.employee = false;
                callback && callback();
            });

        };

        authenticateEmployer();
        authenticateEmployee();

        $scope.employee = {};
        $scope.employer = {};
        $scope.login = {};
        $rootScope.authenticated = {};

        $scope.employerLogin = function () {
            authenticateEmployer($scope.employer, function () {
                if ($rootScope.authenticated.employer) {
                    $location.path("/");
                    $scope.login.error = false;
                } else {
                    $location.path("/");
                    $scope.login.error = true;
                }
            });
        };

        $scope.employeeLogin = function () {
            authenticateEmployee($scope.employee, function () {
                if ($rootScope.authenticated.employee) {
                    $location.path("/");
                    $scope.login.error = false;
                } else {
                    $location.path("/");
                    $scope.login.error = true;
                }
            });
        };

        $scope.logoutEmployer = function () {
            $http.post('/employer/logout', {}).finally(function () {
                $rootScope.authenticated.employer = false;
                $location.path("/");
            });
        };

        $scope.logoutEmployee = function () {
            $http.post('/employee/logout', {}).finally(function () {
                $rootScope.authenticated.employee = false;
                $location.path("/");
            });
        };

    }]);