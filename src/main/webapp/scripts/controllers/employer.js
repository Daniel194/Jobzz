angular.module('jobzz')
    .controller('EmployerCtrl', ['$scope', '$rootScope', '$http', 'EmployerAuthSharedService', function ($scope, $rootScope, $http, EmployerAuthSharedService) {

        $scope.logoutEmployer = function () {
            $http.post('/employer/logout', {}).finally(function () {
                EmployeeAuthSharedService.logout();
            });
        };

    }]);