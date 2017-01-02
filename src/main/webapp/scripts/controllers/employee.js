angular.module('jobzz')
    .controller('EmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {
        $scope.currentNavItem = 'home';

        $scope.logoutEmployee = function () {
            $http.post('/employee/logout', {}).finally(function () {
                EmployeeAuthSharedService.logout();
            });
        };

    }]);