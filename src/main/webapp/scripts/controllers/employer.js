angular.module('jobzz')
    .controller('EmployerCtrl', ['$scope', '$rootScope', '$http', '$location', 'EmployerAuthSharedService', function ($scope, $rootScope, $http, $location, EmployerAuthSharedService) {

        $scope.logoutEmployer = function () {
            $http.post('/employer/logout', {}).finally(function () {
                EmployerAuthSharedService.logout();
            });
        };

    }]);