angular.module('jobzz')
    .controller('SettingsEmployeeCtrl', ['$scope', '$rootScope', '$http',
        function ($scope, $rootScope, $http) {

            $scope.employee = {};
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


            var getEmployeeFullDetails = function () {

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

                }, function () {
                    console.log('Fail to get employee full details !');
                });

            };

            getEmployeeFullDetails();


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

                $http(req).then(function (response) {


                }, function () {
                    console.log('Fail to update employee !');
                });

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

                $http(req).then(function (response) {


                }, function () {
                    console.log('Fail to update employee !');
                });

            };

        }]);