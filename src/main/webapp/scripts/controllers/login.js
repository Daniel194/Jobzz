angular.module('jobzz')
    .controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location', 'EmployerAuthSharedService', 'EmployeeAuthSharedService',
        function ($scope, $rootScope, $http, $location, EmployerAuthSharedService, EmployeeAuthSharedService) {

            $scope.employee = {};
            $scope.employer = {};

            $scope.employerLogin = function () {
                $rootScope.employerAuthenticationError = false;
                EmployerAuthSharedService.login($scope.employer.email, $scope.employer.password);
            };

            $scope.employeeLogin = function () {
                $rootScope.employeeAuthenticationError = false;
                EmployeeAuthSharedService.login($scope.employee.email, $scope.employee.password);
            };

        }]);