angular.module('jobzz')
    .controller('EmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', '$location', 'userProfilePictureService',
        function ($scope, $rootScope, $http, EmployeeAuthSharedService, $location, userProfilePictureService) {
            $scope.currentNavItem = 'home';

            $scope.logoutEmployee = function () {
                $http.post('/employee/logout', {}).finally(function () {
                    EmployeeAuthSharedService.logout();
                });
            };

            $scope.navigateTo = function (newPath) {
                $location.path(newPath).replace();
            };

            $scope.openMenu = function ($mdOpenMenu, ev) {
                $mdOpenMenu(ev);
            };

            (function () {
                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/account',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {
                    $scope.employee = response.data;
                    $scope.employee.picture = userProfilePictureService.employeeProfilePicture($scope.employee.picture);
                });

            })();
        }]);