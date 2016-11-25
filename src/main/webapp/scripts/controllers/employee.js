angular.module('jobzz')
    .controller('EmployeeCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {

        $scope.logoutEmployee = function () {
            $http.post('/employee/logout', {}).finally(function () {
                $rootScope.authenticated.employee = false;
                $location.path("/");
            });
        };

    }]);