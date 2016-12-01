angular.module('jobzz')
    .controller('EmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {
        $scope.currentNavItem = 'home';

        $scope.logoutEmployee = function () {
            $http.post('/employee/logout', {}).finally(function () {
                EmployeeAuthSharedService.logout();
            });
        };

    }])
    .controller('HomeEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {

        $scope.jobsWaiting = [{
            status: 0,
            employerPosting: {
                name: 'Acesta este test 0',
                startDate: '12-15-2016',
                endDate: '12-20-2016'
            },
            price: 30,
            currency: '$'
        },
            {
                status: 1,
                employerPosting: {
                    name: 'Acesta este test 1',
                    startDate: '12-20-2016',
                    endDate: '12-25-2016'
                },
                price: 10,
                currency: '$'
            }, {
                status: 2,
                employerPosting: {
                    name: 'Acesta este tes 2',
                    startDate: '12-15-2016',
                    endDate: '12-17-2016'
                },
                price: 35,
                currency: '$'
            }];

        $scope.jobsProgress = [{
            status: 3,
            employerPosting: {
                name: 'Acesta este test 3',
                startDate: '12-01-2016',
                endDate: '12-05-2016'
            },
            price: 30,
            currency: '$'
        },
            {
                status: 4,
                employerPosting: {
                    name: 'Acesta este test 4',
                    startDate: '11-30-2016',
                    endDate: '12-10-2016'
                },
                price: 100,
                currency: '$'
            }];

        $scope.jobsDone = [{
            status: 5,
            employerPosting: {
                name: 'Acesta este test 5',
                startDate: '11-01-2016',
                endDate: '11-05-2016'
            },
            price: 30,
            currency: '$'
        },
            {
                status: 6,
                employerPosting: {
                    name: 'Acesta este test 6',
                    startDate: '10-30-2016',
                    endDate: '11-10-2016'
                },
                price: 100,
                currency: '$'
            }];

    }])
    .controller('SettingsEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {


    }])
    .controller('ProfileEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {


    }])
    .controller('FindJobEmployeeCtrl', ['$scope', '$rootScope', '$http', 'EmployeeAuthSharedService', function ($scope, $rootScope, $http, EmployeeAuthSharedService) {


    }]);