angular.module('jobzz')
    .controller('SettingsEmployeeCtrl', ['$scope', '$rootScope', '$http', 'userProfilePictureService', 'intervalDateForCalendarsService',
        function ($scope, $rootScope, $http, userProfilePictureService, intervalDateForCalendarsService) {

            var dates = intervalDateForCalendarsService.getDates();

            $scope.employee = {};
            $scope.change = {};

            $scope.minDate = dates.minDate;
            $scope.maxDate = dates.maxDate;
            $scope.minDateExp = dates.minDateExp;
            $scope.maxDateExp = dates.maxDateExp;

            (function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employee/account/full',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {

                    $scope.employee = response.data;
                    $scope.employee.dateOfBirth = new Date($scope.employee.dateOfBirth);
                    $scope.employee.expirationDate = new Date($scope.employee.expirationDate);
                    $scope.employee.picture = userProfilePictureService.employeeProfilePicture($scope.employee.picture);

                });

            })();

            $scope.changeGeneralInformation = function () {

                var req = {
                    method: 'PUT',
                    dataType: 'json',
                    url: '/employee/update/employee/general/information',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.employee
                };

                $http(req);

            };

            $scope.changePaymentInformation = function () {

                var req = {
                    method: 'PUT',
                    dataType: 'json',
                    url: '/employee/update/employee/payment/information',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.employee
                };

                $http(req);

            };

            $scope.changePassword = function () {

                var req = {
                    method: 'PUT',
                    dataType: 'json',
                    url: '/employee/change/password',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.change
                };

                $http(req);

            };


            $scope.changePicture = function () {
                var file = $scope.myFile;
                var uploadUrl = "/employee/change/picture";
                var fd = new FormData();

                fd.append('file', file);

                $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-Type': undefined
                    }
                }).then(function (response) {

                    $scope.employee.picture = userProfilePictureService.employeeProfilePicture(response.profilePicture);

                });

            };

        }]);