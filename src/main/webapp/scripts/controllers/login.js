angular.module('jobzz')
    .controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {

        var authenticate = function (credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.email + ":" + credentials.password)
            } : {};

            $http.get('/login/employer', {headers: headers}).then(function (response) {

                $rootScope.authenticated = response.data.name ? true : false;

                callback && callback();
            }, function (response) {
                $rootScope.authenticated = false;
                callback && callback();
            });

        };

        authenticate();

        $scope.employee = {};
        $scope.employer = {};
        $scope.login = {};

        $scope.employerLogin = function () {
            authenticate($scope.employer, function () {
                if ($rootScope.authenticated) {
                    $location.path("/");
                    $scope.login.error = false;
                } else {
                    $location.path("/");
                    $scope.login.error = true;
                }
            });
        };

        // $scope.employeeLogin = function () {
        //     authenticate($scope.employee, function () {
        //         if ($rootScope.authenticated) {
        //             $location.path("/");
        //             $scope.error = false;
        //         } else {
        //             $location.path("/");
        //             $scope.error = true;
        //         }
        //     });
        // };

        $scope.logout = function () {
            $http.post('logout', {}).finally(function () {
                $rootScope.authenticated = false;
                $location.path("/");
            });
        }
    }]);