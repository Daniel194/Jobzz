angular.module('jobzz')
    .controller('SettingsEmployerCtrl', ['$scope', '$rootScope', '$http', 'userProfilePictureService', 'intervalDateForCalendarsService',
        function ($scope, $rootScope, $http, userProfilePictureService, intervalDateForCalendarsService) {

            var dates = intervalDateForCalendarsService.getDates();

            $scope.employer = {};
            $scope.change = {};

            $scope.minDate = dates.minDate;
            $scope.maxDate = dates.maxDate;
            $scope.minDateExp = dates.minDateExp;
            $scope.maxDateExp = dates.maxDateExp;


            (function () {

                var req = {
                    method: 'GET',
                    dataType: 'json',
                    url: '/employer/account/full',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                };

                $http(req).then(function (response) {

                    $scope.employer = response.data;
                    $scope.employer.dateOfBirth = new Date($scope.employer.dateOfBirth);
                    $scope.employer.expirationDate = new Date($scope.employer.expirationDate);
                    $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture($scope.employer.profilePicture);

                });

            })();

            $scope.changeGeneralInformation = function () {

                var req = {
                    method: 'PUT',
                    dataType: 'json',
                    url: '/employer/update/employer/general/information',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.employer
                };

                $http(req);

            };


            $scope.changePaymentInformation = function () {

                var req = {
                    method: 'PUT',
                    dataType: 'json',
                    url: '/employer/update/employer/payment/information',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.employer
                };

                $http(req);

            };

            $scope.changePassword = function () {

                var req = {
                    method: 'PUT',
                    dataType: 'json',
                    url: '/employer/change/password',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    data: $scope.change
                };

                $http(req);

            };

            $scope.changePicture = function () {

                var file = $scope.myFile;

                var uploadUrl = "/employer/change/picture";
                var fd = new FormData();

                fd.append('file', file);

                $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-Type': undefined
                    }
                }).then(function (response) {

                    $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture(response.profilePicture);

                });

            };

        }]);