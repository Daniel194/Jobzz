angular.module('jobzz', ['ngRoute'])
    .config(function ($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl: '/views/login.html',
            controller: 'LoginCtrl',
            controllerAs: 'LoginCtrl'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    });