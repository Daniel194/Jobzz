angular.module('jobzz')
    .controller('EmployerCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {

        $scope.logoutEmployer = function () {
            $http.post('/employer/logout', {}).finally(function () {
                $rootScope.authenticated.employer = false;
                $location.path("/");
            });
        };

    }]);