angular.module('jobzz', ['ngRoute', 'ngAnimate', 'ngMaterial', 'ngMessages'])
    .config(function ($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl: '/views/login.html',
            controller: 'LoginCtrl',
            controllerAs: 'LoginCtrl'
        })
            .when('/register/employee', {
                templateUrl: '/views/register/employee.html',
                controller: 'RegisterEmployeeCtrl',
                controllerAs: 'RegisterEmployeeCtrl'
            })
            .when('/register/employer', {
                templateUrl: '/views/register/employer.html',
                controller: 'RegisterEmployerCtrl',
                controllerAs: 'RegisterEmployerCtrl'
            })
            .when('/employer/home', {
                templateUrl: '/views/employer/home.html',
                controller: 'EmployerCtrl',
                controllerAs: 'EmployerCtrl'
            })
            .when('/employee/home', {
                templateUrl: '/views/employee/home.html',
                controller: 'EmployeeCtrl',
                controllerAs: 'EmployeeCtrl'
            })
            .otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    });