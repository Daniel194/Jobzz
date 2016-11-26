angular.module('jobzz')
    .controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location', 'EmployerAuthSharedService', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, $location, EmployerAuthSharedService, EmployeeAuthSharedService) {
        $scope.employee = {};
        $scope.employer = {};

        $scope.employerLogin = function () {
            $rootScope.authenticationError = false;
            EmployerAuthSharedService.login($scope.employer.email, $scope.employer.password);
        };

        $scope.employeeLogin = function () {
            $rootScope.authenticationError = false;
            EmployeeAuthSharedService.login($scope.employee.email, $scope.employee.password);
        };

    }]);