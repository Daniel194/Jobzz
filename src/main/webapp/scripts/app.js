angular.module('jobzz', ['ngRoute', 'ngAnimate', 'ngMaterial', 'ngMessages', 'http-auth-interceptor', 'LocalStorageModule'])
    .config(function ($routeProvider, $httpProvider, USER_ROLES) {

        $routeProvider
            .when('/', {
                redirectTo: '/login'
            })
            .when('/login', {
                templateUrl: '/views/login.html',
                controller: 'LoginCtrl',
                controllerAs: 'LoginCtrl',
                access: {
                    loginRequired: false,
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/register/employee', {
                templateUrl: '/views/register/employee.html',
                controller: 'RegisterEmployeeCtrl',
                controllerAs: 'RegisterEmployeeCtrl',
                access: {
                    loginRequired: false,
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/register/employer', {
                templateUrl: '/views/register/employer.html',
                controller: 'RegisterEmployerCtrl',
                controllerAs: 'RegisterEmployerCtrl',
                access: {
                    loginRequired: false,
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/employer/home', {
                templateUrl: '/views/employer/home.html',
                controller: 'EmployerCtrl',
                controllerAs: 'EmployerCtrl',
                access: {
                    loginRequired: true,
                    authorizedRoles: [USER_ROLES.employer]
                }
            })
            .when('/employee/home', {
                templateUrl: '/views/employee/home.html',
                controller: 'EmployeeCtrl',
                controllerAs: 'EmployeeCtrl',
                access: {
                    loginRequired: true,
                    authorizedRoles: [USER_ROLES.employee]
                }
            })
            .when('/loading', {
                templateUrl: 'views/loading.html',
                access: {
                    loginRequired: false,
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when("/error/:code", {
                templateUrl: "/views/error.html",
                controller: "ErrorCtrl",
                access: {
                    loginRequired: false,
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .otherwise({
                redirectTo: '/error/404',
                access: {
                    loginRequired: false,
                    authorizedRoles: [USER_ROLES.all]
                }
            });

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    })
    .run(function ($rootScope, $location, $http, EmployerSession, EmployeeSession, EmployerAuthSharedService, EmployeeAuthSharedService, $q, $timeout, localStorageService) {


        $rootScope.$on('event:auth-forbidden', function (rejection) {
            $rootScope.$evalAsync(function () {
                $location.path('/error/403').replace();
            });
        });

        // Call when the the client is confirmed
        $rootScope.$on('event:auth-loginConfirmed', function (event, data) {
            $rootScope.isEmployee = localStorageService.get("isEmployee");
            $rootScope.isEmployer = localStorageService.get("isEmployer");
            $rootScope.loadingAccount = false;
            var home = $rootScope.isEmployee ? "/employee/home" : "/employer/home";
            var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestedUrl : home);
            var delay = ($location.path() === "/loading" ? 1500 : 0);

            $timeout(function () {

                if ($rootScope.isEmployee) {
                    EmployeeSession.create(data);
                    $rootScope.account = EmployeeAuthSharedService;
                } else if ($rootScope.isEmployer) {
                    EmployerSession.create(data);
                    $rootScope.account = EmployerAuthSharedService;
                }

                $rootScope.authenticated = true;
                $location.path(nextLocation).replace();
            }, delay);

        });

        // Call when the 401 response is returned by the server
        $rootScope.$on('event:auth-loginRequired', function (event, data) {
            if ($rootScope.loadingAccount && data.status !== 401) {
                $rootScope.requestedUrl = $location.path();
                $location.path('/loading');
            } else {

                EmployeeSession.invalidate();
                EmployerSession.invalidate();

                $rootScope.authenticated = false;
                $rootScope.loadingAccount = false;
                $location.path('/login');
            }
        });

        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (next.originalPath === "/login" && $rootScope.authenticated) {
                event.preventDefault();
            } else if (next.access && next.access.loginRequired && !$rootScope.authenticated) {
                event.preventDefault();
                $rootScope.$broadcast("event:auth-loginRequired", {});
            } else if (next.access && (!EmployerAuthSharedService.isAuthorized(next.access.authorizedRoles) && !EmployeeAuthSharedService.isAuthorized(next.access.authorizedRoles))) {
                event.preventDefault();
                $rootScope.$broadcast("event:auth-forbidden", {});
            }
        });

        // Call when the user logs out
        $rootScope.$on('event:auth-loginCancelled', function () {
            $location.path('/login').replace();
        });

        $rootScope.isEmployee = localStorageService.get("isEmployee");
        $rootScope.isEmployer = localStorageService.get("isEmployer");

        // Get already authenticated user account
        if ($rootScope.isEmployee) {
            EmployeeAuthSharedService.getAccount();
        } else if ($rootScope.isEmployer) {
            EmployerAuthSharedService.getAccount();
        }
    });