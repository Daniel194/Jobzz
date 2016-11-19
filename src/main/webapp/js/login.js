angular.module('jobzz', ['ngRoute'])
    .config(function ($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl: '/view/login.html',
            controller: 'navigation',
            controllerAs: 'controller'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    })
    .controller('navigation', function ($rootScope, $http, $location) {

        var self = this;

        var authenticate = function (credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('user', {headers: headers}).then(function (response) {

                $rootScope.authenticated = response.data.name ? true : false;

                callback && callback();
            }, function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

        };

        authenticate();

        self.credentials = {};

        self.employerLogin = function () {
            authenticate(self.credentials.employer, function () {
                if ($rootScope.authenticated) {
                    $location.path("/");
                    self.error = false;
                } else {
                    $location.path("/");
                    self.error = true;
                }
            });
        };

        self.employeeLogin = function () {
            authenticate(self.credentials.employee, function () {
                if ($rootScope.authenticated) {
                    $location.path("/");
                    self.error = false;
                } else {
                    $location.path("/");
                    self.error = true;
                }
            });
        };

        self.logout = function () {
            $http.post('logout', {}).finally(function () {
                $rootScope.authenticated = false;
                $location.path("/");
            });
        }
    });