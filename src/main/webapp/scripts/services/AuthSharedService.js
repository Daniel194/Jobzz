angular.module('jobzz').service('EmployerAuthSharedService', function ($rootScope, $http, authService, EmployerSession, localStorageService) {
    return {
        login: function (email, password) {

            var config = {
                ignoreAuthModule: 'ignoreAuthModule',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            };
            $http.post('/employer/login', $.param({
                email: email,
                password: password
            }), config)
                .success(function (data, status, headers, config) {
                    localStorageService.set('isEmployer', true);
                    localStorageService.set('isEmployee', false);

                    authService.loginConfirmed(data);
                }).error(function (data, status, headers, config) {

                $rootScope.employerAuthenticationError = true;
                EmployerSession.invalidate();
            });

        },

        getAccount: function () {
            $rootScope.loadingAccount = true;
            $http.get('/employer/account')
                .then(function (response) {
                    authService.loginConfirmed(response.data);
                });
        },

        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!EmployerSession.email &&
                EmployerSession.userRoles.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        },

        logout: function () {
            $rootScope.employerAuthenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            localStorageService.remove('isEmployer');
            localStorageService.remove('isEmployee');
            EmployerSession.invalidate();
            authService.loginCancelled();
        }
    };
});


angular.module('jobzz').service('EmployeeAuthSharedService', function ($rootScope, $http, authService, EmployeeSession, localStorageService) {
    return {
        login: function (email, password) {

            var config = {
                ignoreAuthModule: 'ignoreAuthModule',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            };
            $http.post('/employee/login', $.param({
                email: email,
                password: password
            }), config)
                .success(function (data, status, headers, config) {
                    localStorageService.set('isEmployer', false);
                    localStorageService.set('isEmployee', true);

                    authService.loginConfirmed(data);
                }).error(function (data, status, headers, config) {

                $rootScope.employeeAuthenticationError = true;
                EmployeeSession.invalidate();
            });
        },

        getAccount: function () {
            $rootScope.loadingAccount = true;
            $http.get('/employee/account')
                .then(function (response) {
                    authService.loginConfirmed(response.data);
                });
        },

        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!EmployeeSession.email &&
                EmployeeSession.userRoles.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        },
        logout: function () {
            $rootScope.employeeAuthenticationError = false;
            $rootScope.authenticated = false;
            localStorageService.remove('isEmployer');
            localStorageService.remove('isEmployee');
            $rootScope.account = null;
            EmployeeSession.invalidate();
            authService.loginCancelled();
        }

    };
});
