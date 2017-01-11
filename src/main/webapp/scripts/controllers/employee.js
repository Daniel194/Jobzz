angular.module('jobzz')
    .controller('EmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', '$location',
        function ($scope, $rootScope, $http, EmployeeAuthSharedService, $location) {
            $scope.currentNavItem = 'home';

            $scope.logoutEmployee = function () {
                $http.post('/employee/logout', {}).finally(function () {
                    EmployeeAuthSharedService.logout();
                });
            };

            $scope.navigateTo = function (newPath) {

                $location.path(newPath).replace();

            };

        }]);