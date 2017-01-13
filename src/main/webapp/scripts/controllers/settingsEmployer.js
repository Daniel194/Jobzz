angular.module('jobzz')
    .controller('SettingsEmployerCtrl', ['$scope', '$rootScope', '$http', 'userProfilePictureService',
        function ($scope, $rootScope, $http, userProfilePictureService) {

            $scope.employer = {};
            $scope.change = {};
            $scope.currentDate = new Date();

            $scope.minDate = new Date(
                $scope.currentDate.getFullYear() - 100,
                $scope.currentDate.getMonth(),
                $scope.currentDate.getDate());

            $scope.maxDate = new Date(
                $scope.currentDate.getFullYear(),
                $scope.currentDate.getMonth(),
                $scope.currentDate.getDate());

            $scope.minDateExp = new Date(
                $scope.currentDate.getFullYear(),
                $scope.currentDate.getMonth(),
                $scope.currentDate.getDate());

            $scope.maxDateExp = new Date(
                $scope.currentDate.getFullYear() + 10,
                $scope.currentDate.getMonth(),
                $scope.currentDate.getDate());


            var getEmployerFullDetails = function () {

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

                }, function () {
                    //Empty
                });

            };

            getEmployerFullDetails();

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

                $http(req).then(function () {
                    //Empty
                }, function () {
                    //Empty
                });

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

                $http(req).then(function () {
                    //Empty
                }, function () {
                    //Empty
                });

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

                $http(req).then(function () {
                    //Empty
                }, function () {
                    //Empty
                });

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
                }).success(function (response) {

                    $scope.employer.profilePicture = userProfilePictureService.employerProfilePicture(response.profilePicture);

                }).error(function () {
                    //Empty
                });

            };

        }]);