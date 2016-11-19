angular.module('jobzz', ['ngRoute'])
    .config(function ($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl: '/views/login.html',
            controller: 'LoginCtrl',
            controllerAs: 'LoginCtrl'
        })
            .when('/create-account-employee', {
                templateUrl: '/views/create-account-employee.html',
                controller: 'CreateAccountEmployeeCtrl',
                controllerAs: 'CreateAccountEmployeeCtrl'
            })
            .when('/create-account-employer', {
                templateUrl: '/views/create-account-employer.html',
                controller: 'CreateAccountEmployerCtrl',
                controllerAs: 'CreateAccountEmployerCtrl'
            })
            .otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    });